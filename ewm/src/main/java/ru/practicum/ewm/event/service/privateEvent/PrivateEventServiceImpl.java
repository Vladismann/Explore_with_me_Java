package ru.practicum.ewm.event.service.privateEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.repo.CategoryRepo;
import ru.practicum.ewm.common.CommonMethods;
import ru.practicum.ewm.common.CustomPageRequest;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventMapper;
import ru.practicum.ewm.event.dto.NewEventDto;
import ru.practicum.ewm.event.dto.UpdateEventRequest;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.Location;
import ru.practicum.ewm.event.repo.EventRepo;
import ru.practicum.ewm.event.repo.LocationRepo;
import ru.practicum.ewm.exceptions.NotFoundException;
import ru.practicum.ewm.exceptions.WrongConditionsException;
import ru.practicum.ewm.requests.dto.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.requests.dto.EventRequestStatusUpdateResult;
import ru.practicum.ewm.requests.dto.ParticipationRequestDto;
import ru.practicum.ewm.requests.dto.RequestMapper;
import ru.practicum.ewm.requests.model.Request;
import ru.practicum.ewm.requests.model.StateParticipation;
import ru.practicum.ewm.requests.repo.RequestRepo;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repo.UserRepo;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.ewm.event.model.EventState.PUBLISHED;
import static ru.practicum.ewm.requests.model.StateParticipation.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PrivateEventServiceImpl implements PrivateEventService {

    private final EventRepo eventRepo;
    private final UserRepo userRepo;
    private final CategoryRepo categoryRepo;
    private final LocationRepo locationRepo;
    private final RequestRepo requestRepo;

    private void checkEventDateIsCorrect(LocalDateTime eventDate) {
        if (eventDate != null && eventDate.isBefore(LocalDateTime.now().plusHours(2))) {
            throw new IllegalArgumentException(
                    String.format("Field: eventDate. Error: Дата должна быть больше текущей на 2ч. Value: %S", eventDate));
        }
    }

    @Override
    public EventFullDto createEvent(Long userId, NewEventDto newEventDto) {
        CommonMethods.checkObjectIsExists(userId, userRepo);
        long categoryId = newEventDto.getCategory();
        CommonMethods.checkObjectIsExists(categoryId, categoryRepo);
        checkEventDateIsCorrect(newEventDto.getEventDate());
        if (newEventDto.getRequestModeration() == null) {
            newEventDto.setRequestModeration(true);
        }

        Category category = categoryRepo.getReferenceById(categoryId);
        User user = userRepo.getReferenceById(userId);
        Location location = EventMapper.locationDtoToLocation(newEventDto.getLocation());
        locationRepo.save(location);
        Event event = EventMapper.newEventDtoToEvent(newEventDto, category, user, location);
        event = eventRepo.save(event);
        log.info("Создано событие: {}", event);
        return EventMapper.eventToEventFullDto(event);
    }

    @Transactional(readOnly = true)
    @Override
    public List<EventFullDto> getUserEvents(long userId, int from, int size) {
        CommonMethods.checkObjectIsExists(userId, userRepo);
        Pageable pageable = new CustomPageRequest(from, size);
        List<Event> events = eventRepo.findAllByInitiatorId(userId, pageable);
        if (events.isEmpty()) {
            return List.of();
        }
        log.info("Запрошен список событий пользователя id={}", userId);
        return EventMapper.eventToEventFullDto(events);
    }

    @Transactional(readOnly = true)
    @Override
    public EventFullDto getUserEvent(long userId, long eventId) {
        CommonMethods.checkObjectIsExists(userId, userRepo);
        CommonMethods.checkObjectIsExists(eventId, eventRepo);
        Event event = eventRepo.getReferenceById(eventId);
        log.info("Запрошено событие: {}", event);
        return EventMapper.eventToEventFullDto(event);
    }

    @Override
    public EventFullDto updateEvent(long userId, long eventId, UpdateEventRequest newEvent) {
        CommonMethods.checkObjectIsExists(userId, userRepo);
        CommonMethods.checkObjectIsExists(eventId, eventRepo);
        Event event = eventRepo.getReferenceById(eventId);
        if (userId != event.getInitiator().getId()) {
            throw new NotFoundException(String.format("Object with id=%s was not found", eventId));
        }
        if (event.getState().equals(PUBLISHED)) {
            throw new WrongConditionsException("Event must not be published");
        }
        checkEventDateIsCorrect(newEvent.getEventDate());

        if (newEvent.getCategory() != null) {
            long newCategoryId = newEvent.getCategory();
            if (newCategoryId != event.getCategory().getId() && newCategoryId != 0) {
                CommonMethods.checkObjectIsExists(newCategoryId, categoryRepo);
                Category category = categoryRepo.getReferenceById(newCategoryId);
                event.setCategory(category);
            }
        }

        if (newEvent.getLocation() != null) {
            Location location = event.getLocation();
            float newLat = newEvent.getLocation().getLat();
            float newLon = newEvent.getLocation().getLon();
            if (location.getLat() != newLat || location.getLon() != newLon) {
                location.setLat(newLat);
                location.setLon(newLon);
                locationRepo.save(location);
                event.setLocation(location);
            }
        }
        event = EventMapper.updateEventUser(newEvent, event);
        log.info("Обновлено событие: {}", event);
        return EventMapper.eventToEventFullDto(event);
    }

    @Override
    public List<ParticipationRequestDto> getAllRequestsByEvent(Long eventId, Long userId) {
        CommonMethods.checkObjectIsExists(userId, userRepo);
        CommonMethods.checkObjectIsExists(eventId, eventRepo);
        Event event = eventRepo.getReferenceById(eventId);
        if (event.getInitiator().getId() != userId) {
            throw new NotFoundException(String.format("Object with id=%s was not found", eventId));
        }
        List<Request> requests = requestRepo.findAllByEventId(eventId);
        log.info("Получен список запросов на участие для события с id={}", eventId);
        return RequestMapper.requestToParticipationRequestDto(requests);
    }

    @Override
    public EventRequestStatusUpdateResult updateEventRequestsStatus(Long eventId, Long userId, EventRequestStatusUpdateRequest requestsForUpdate) {
        CommonMethods.checkObjectIsExists(userId, userRepo);
        CommonMethods.checkObjectIsExists(eventId, eventRepo);
        Event event = eventRepo.getReferenceById(eventId);
        List<Long> requestIds = requestsForUpdate.getRequestIds();
        if (event.getInitiator().getId() != userId) {
            throw new NotFoundException(String.format("Object with id=%s was not found", eventId));
        }
        long actualLimit = event.getParticipantLimit();
        if (actualLimit == 0 || !event.getRequestModeration()) {
            throw new WrongConditionsException("Approval is not required");
        }
        long actualConfirmedRequestsNum = requestRepo.countByEventIdAndStatus(eventId, CONFIRMED);
        if (actualLimit == actualConfirmedRequestsNum) {
            throw new WrongConditionsException("The participant limit has been reached=" + event.getParticipantLimit());
        }

        List<Request> requests = requestRepo.findAllByIdIn(requestIds);
        Request notPendingrequest = requests.stream().filter(request -> !request.getStatus().equals(PENDING)).findFirst().orElse(null);
        if (notPendingrequest != null) {
            throw new WrongConditionsException("Incorrect status for approving in request: " + notPendingrequest.getId());
        }
        // проверяем, что если заявки одобряются, то лимит не будет превышен, по одобряемым отталкиваемся от requests.size(), а не requestsForUpdate
        StateParticipation newState = requestsForUpdate.getStatus();
        long newRequestsWithNewConfirmedRequests = actualConfirmedRequestsNum + requests.size();
        if (newState.equals(CONFIRMED) && newRequestsWithNewConfirmedRequests > event.getParticipantLimit()) {
            throw new WrongConditionsException("Free places less than " + requestsForUpdate.getRequestIds().size());
        }

        EventRequestStatusUpdateResult updatedRequests = new EventRequestStatusUpdateResult();
        if (newState.equals(CONFIRMED)) {
            requests.forEach(request -> request.setStatus(CONFIRMED));
            requestRepo.saveAll(requests);
            updatedRequests.setConfirmedRequests(RequestMapper.requestToParticipationRequestDto(requests));
            // отклоняем оставшиеся запросы, которые ожидаю рассмотрения, если лимит достигнут
            if (actualLimit == newRequestsWithNewConfirmedRequests) {
                List<Request> leftRequests = requestRepo.findAllByEventIdAndStatusAndIdNotIn(eventId, PENDING, requestIds);
                leftRequests.forEach(request -> request.setStatus(REJECTED));
                requestRepo.saveAll(leftRequests);
                updatedRequests.setRejectedRequests(RequestMapper.requestToParticipationRequestDto(leftRequests));
            } else {
                updatedRequests.setRejectedRequests(List.of());
            }
        } else {
            requests.forEach(request -> request.setStatus(REJECTED));
            requestRepo.saveAll(requests);
            updatedRequests.setRejectedRequests(RequestMapper.requestToParticipationRequestDto(requests));
            updatedRequests.setConfirmedRequests(List.of());
        }
        return updatedRequests;
    }

}