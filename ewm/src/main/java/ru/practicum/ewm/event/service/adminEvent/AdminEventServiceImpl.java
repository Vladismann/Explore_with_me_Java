package ru.practicum.ewm.event.service.adminEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.repo.CategoryRepo;
import ru.practicum.ewm.common.CommonMethods;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventMapper;
import ru.practicum.ewm.event.dto.UpdateEventRequest;
import ru.practicum.ewm.event.dto.search.AdminSearchRequest;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.EventState;
import ru.practicum.ewm.event.model.Location;
import ru.practicum.ewm.event.repo.EventRepo;
import ru.practicum.ewm.event.repo.EventRepoSearch;
import ru.practicum.ewm.event.repo.LocationRepo;
import ru.practicum.ewm.event.service.EventServiceCommon;
import ru.practicum.ewm.exceptions.WrongConditionsException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AdminEventServiceImpl implements AdminEventService {

    private final EventServiceCommon eventServiceCommon;
    private final EventRepoSearch eventRepoSearch;
    private final EventRepo eventRepo;
    private final CategoryRepo categoryRepo;
    private final LocationRepo locationRepo;

    @Override
    public EventFullDto updateEvent(Long eventId, UpdateEventRequest newEvent) {
        if (newEvent.getEventDate().isBefore(LocalDateTime.now().plusHours(1))) {
            throw new WrongConditionsException(
                    String.format("Field: eventDate. Error: Дата начала изменяемого события должна быть не ранее чем за час от даты публикации. Value: %S", newEvent.getEventDate()));
        }
        CommonMethods.checkObjectIsExists(eventId, eventRepo);
        Event event = eventRepo.getReferenceById(eventId);
        if (!event.getState().equals(EventState.PENDING)) {
            throw new WrongConditionsException("Cannot publish the event because it's not in the right state: PUBLISHED");
        }
        long newCategoryId = newEvent.getCategory();
        if (newCategoryId != event.getCategory().getId() && newCategoryId != 0) {
            CommonMethods.checkObjectIsExists(newCategoryId, categoryRepo);
            Category category = categoryRepo.getReferenceById(newCategoryId);
            event.setCategory(category);
        }

        Location location = event.getLocation();
        float newLat = newEvent.getLocation().getLat();
        float newLon = newEvent.getLocation().getLon();
        if (location.getLat() != newLat || location.getLon() != newLon) {
            location.setLat(newLat);
            location.setLon(newLon);
            locationRepo.save(location);
            event.setLocation(location);
        }
        event = EventMapper.UpdateEventUser(newEvent, event);
        log.info("Обновлено событие: {}", event);
        return EventMapper.eventToEventFullDto(event);
    }

    @Transactional(readOnly = true)
    @Override
    public List<EventFullDto> getAll(AdminSearchRequest request) {
        List<Event> events = eventRepoSearch.findAllForAdmin(request);
        Map<Long, Integer> confirmedRequests = eventServiceCommon.getConfirmedRequests(events);
        Map<Long, Long> views = eventServiceCommon.getViews(events);
        List<EventFullDto> eventFullDto = EventMapper.eventToEventFullDto(events);
        eventFullDto = eventServiceCommon.setViewsAndRequestForListEventFullDto(eventFullDto, views, confirmedRequests);
        log.info("Запрошен список событий в размере: {}", eventFullDto.size());
        return eventFullDto;
    }
}
