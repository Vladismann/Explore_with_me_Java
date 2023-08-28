package ru.practicum.ewm.event.dto;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.EventState;
import ru.practicum.ewm.event.model.Location;
import ru.practicum.ewm.user.dto.UserShortDto;
import ru.practicum.ewm.user.model.User;

import java.time.LocalDateTime;

@UtilityClass
public class EventMapper {

    public Location LocationDtoToLocation(LocationDto locationDto) {
        return Location.builder().lat(locationDto.getLat()).lon(locationDto.getLon()).build();
    }

    public Event NewEventDtoToEvent(NewEventDto newEventDto, Category category, User user, Location location) {
        return Event.builder()
                .title(newEventDto.getTitle())
                .annotation(newEventDto.getAnnotation())
                .description(newEventDto.getDescription())
                .participantLimit(newEventDto.getParticipantLimit())
                .category(category)
                .initiator(user)
                .location(location)
                .createdOn(LocalDateTime.now())
                .eventDate(newEventDto.getEventDate())
                .paid(newEventDto.isPaid())
                .requestModeration(newEventDto.isRequestModeration())
                .state(EventState.PENDING)
                .build();
    }

    public EventFullDto EventToEventFullDto(Event event) {
        return EventFullDto.builder()
                .annotation(event.getAnnotation())
                .category(new CategoryDto(event.getCategory().getId(), event.getCategory().getName()))
                .createdOn(event.getCreatedOn())
                .description(event.getDescription())
                .eventDate(event.getEventDate())
                .id(event.getId())
                .initiator(new UserShortDto(event.getInitiator().getId(), event.getInitiator().getName()))
                .location(new LocationDto(event.getLocation().getLat(), event.getLocation().getLon()))
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .requestModeration(event.getRequestModeration())
                .state(event.getState())
                .title(event.getTitle())
                .build();
    }
}
