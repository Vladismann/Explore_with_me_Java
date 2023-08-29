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
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.Location;
import ru.practicum.ewm.event.repo.EventRepo;
import ru.practicum.ewm.event.repo.LocationRepo;
import ru.practicum.ewm.exceptions.WrongConditionsException;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repo.UserRepo;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PrivateEventServiceImpl implements PrivateEventService {

    private final EventRepo eventRepo;
    private final UserRepo userRepo;
    private final CategoryRepo categoryRepo;
    private final LocationRepo locationRepo;

    private void checkEventDateIsCorrect(LocalDateTime eventDate) {
        if (eventDate.isBefore(LocalDateTime.now().plusHours(2))) {
            throw new WrongConditionsException(
                    String.format("Field: eventDate. Error: Дата должна быть больше текущей на 2ч. Value: %S", eventDate));
        }
    }

    @Override
    public EventFullDto createEvent(Long userId, NewEventDto newEventDto) {
        CommonMethods.checkObjectIsExists(userId, userRepo);
        long categoryId = newEventDto.getCategory();
        CommonMethods.checkObjectIsExists(categoryId, categoryRepo);
        checkEventDateIsCorrect(newEventDto.getEventDate());

        Category category = categoryRepo.getReferenceById(categoryId);
        User user = userRepo.getReferenceById(userId);
        Location location = EventMapper.LocationDtoToLocation(newEventDto.getLocation());
        locationRepo.save(location);
        Event event = EventMapper.NewEventDtoToEvent(newEventDto, category, user, location);
        event = eventRepo.save(event);
        log.info("Создано событие: {}", event);
        return EventMapper.EventToEventFullDto(event);
    }

    @Override
    public List<EventFullDto> getUserEvents(long userId, int from, int size) {
        CommonMethods.checkObjectIsExists(userId, userRepo);
        Pageable pageable = new CustomPageRequest(from, size);
        List<Event> events = eventRepo.findAllByInitiatorId(userId, pageable);
        if (events.isEmpty()) {
            return List.of();
        }
        log.info("Запрошен список событий пользователя id={} в размере: {}", userId, events.size());
        return EventMapper.EventToEventFullDto(events);
    }

    public EventFullDto getUserEvent(long userId, long eventId) {
        CommonMethods.checkObjectIsExists(userId, userRepo);
        CommonMethods.checkObjectIsExists(eventId, eventRepo);
        Event event = eventRepo.getReferenceById(eventId);
        log.info("Запрошено событие: {}", event);
        return EventMapper.EventToEventFullDto(event);
    }


}
