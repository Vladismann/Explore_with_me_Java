package ru.practicum.ewm.event.dto;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.EventStateAction;
import ru.practicum.ewm.event.model.Location;
import ru.practicum.ewm.user.dto.UserShortDto;
import ru.practicum.ewm.user.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.practicum.ewm.event.model.EventState.*;
import static ru.practicum.ewm.event.model.EventStateAction.PUBLISH_EVENT;
import static ru.practicum.ewm.event.model.EventStateAction.SEND_TO_REVIEW;

@UtilityClass
public class EventMapper {

    public Location locationDtoToLocation(LocationDto locationDto) {
        return Location.builder().lat(locationDto.getLat()).lon(locationDto.getLon()).build();
    }

    public Event newEventDtoToEvent(NewEventDto newEventDto, Category category, User user, Location location) {
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
                .requestModeration(newEventDto.getRequestModeration())
                .state(PENDING)
                .build();
    }

    public EventFullDto eventToEventFullDto(Event event) {
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

    public List<EventFullDto> eventToEventFullDto(List<Event> event) {
        return event.stream().map(EventMapper::eventToEventFullDto).collect(Collectors.toList());
    }

    public List<EventFullDto> setViewsAndRequestForListEventFullDto(List<EventFullDto> eventFullDto, Map<Long, Long> views, Map<Long, Integer> confirmedRequests) {
        if (!views.isEmpty() || !confirmedRequests.isEmpty()) {
            for (EventFullDto event : eventFullDto) {
                if (views.get(event.getId()) != null) {
                    event.setViews(views.get(event.getId()));
                }
                if (confirmedRequests.get(event.getId()) != null) {
                    event.setConfirmedRequests(confirmedRequests.get(event.getId()));
                }
            }
        }
        return eventFullDto;
    }

    public Event updateEventUser(UpdateEventRequest updateEvent, Event event) {
        String newAnnotation = updateEvent.getAnnotation();
        String newDescription = updateEvent.getDescription();
        LocalDateTime newEventDate = updateEvent.getEventDate();
        Boolean newPaid = updateEvent.getPaid();
        int newParticipantLimit = updateEvent.getParticipantLimit();
        Boolean newRequestModeration = updateEvent.getRequestModeration();
        String newTitle = updateEvent.getTitle();
        EventStateAction newState = updateEvent.getStateAction();

        if (newAnnotation != null && !newAnnotation.isBlank()) {
            event.setAnnotation(newAnnotation);
        }
        if (newDescription != null && !newDescription.isBlank()) {
            event.setDescription(newDescription);
        }
        if (newEventDate != null) {
            event.setEventDate(newEventDate);
        }
        if (newPaid != null) {
            event.setPaid(newPaid);
        }
        if (newParticipantLimit != 0 && newParticipantLimit != event.getParticipantLimit()) {
            event.setParticipantLimit(newParticipantLimit);
        }
        if (newRequestModeration != null) {
            event.setRequestModeration(updateEvent.getRequestModeration());
        }
        if (newTitle != null && !newTitle.isBlank()) {
            event.setTitle(updateEvent.getTitle());
        }

        if (newState != null) {
            if (newState.equals(PUBLISH_EVENT)) {
                event.setState(PUBLISHED);
            } else if (newState.equals(SEND_TO_REVIEW)) {
                event.setState(PENDING);
            } else {
                event.setState(CANCELED);
            }
        }
        return event;
    }

    public EventShortDto eventToEventShortDto(Event event, Map<Long, Integer> confirmedRequests, Map<Long, Long> views) {
        long eventId = event.getId();
        int eventConfirmedRequests = 0;
        long eventViews = 0;
        if (confirmedRequests.get(eventId) != null) {
            eventConfirmedRequests = confirmedRequests.get(eventId);
        }
        if (views.get(eventId) != null) {
            eventViews = views.get(eventId);
        }
        return EventShortDto.builder()
                .annotation(event.getAnnotation())
                .category(new CategoryDto(event.getCategory().getId(), event.getCategory().getName()))
                .confirmedRequests(eventConfirmedRequests)
                .eventDate(event.getEventDate())
                .id(eventId)
                .initiator(new UserShortDto(event.getInitiator().getId(), event.getInitiator().getName()))
                .paid(event.getPaid())
                .title(event.getTitle())
                .views(eventViews)
                .build();
    }

    public List<EventShortDto> eventToEventShortDto(List<Event> events, Map<Long, Integer> confirmedRequests, Map<Long, Long> views) {
        return events.stream().map(event -> eventToEventShortDto(event, confirmedRequests, views)).collect(Collectors.toList());
    }
}
