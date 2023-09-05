package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.NewEventDto;
import ru.practicum.ewm.event.dto.UpdateEventRequest;
import ru.practicum.ewm.event.service.privateEvent.PrivateEventService;
import ru.practicum.ewm.requests.dto.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.requests.dto.EventRequestStatusUpdateResult;
import ru.practicum.ewm.requests.dto.ParticipationRequestDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static ru.practicum.dto.Common.CommonForPaths.BY_ID_PATH;
import static ru.practicum.dto.Common.Messages.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/events")
@Validated
public class PrivateEventController {

    private final PrivateEventService privateEventService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto createEvent(@PathVariable long userId,
                                    @Valid @RequestBody NewEventDto newEventDto) {
        log.info(RECEIVED_POST, "/users/{userId}/events", userId);
        return privateEventService.createEvent(userId, newEventDto);
    }

    @GetMapping()
    public List<EventFullDto> getAllUserEvents(@PathVariable long userId,
                                               @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                               @RequestParam(defaultValue = "10") @Positive int size) {
        log.info(RECEIVED_GET, "/users/{userId}/events", userId);
        return privateEventService.getUserEvents(userId, from, size);
    }

    @GetMapping(BY_ID_PATH)
    public EventFullDto getUserEvent(@PathVariable long userId,
                                     @PathVariable long id) {
        log.info(RECEIVED_GET, "/users/{userId}/events", userId);
        return privateEventService.getUserEvent(userId, id);
    }

    @PatchMapping(BY_ID_PATH)
    public EventFullDto updateUserEvent(@PathVariable long userId,
                                        @PathVariable long id,
                                        @Valid @RequestBody UpdateEventRequest newEventDto) {
        log.info(RECEIVED_PATCH, "/users/{userId}/events", userId);
        return privateEventService.updateEvent(userId, id, newEventDto);
    }

    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> getAllRequestsByForEvent(@PathVariable Long eventId,
                                                                  @PathVariable Long userId) {
        log.info(RECEIVED_GET, "/{eventId}/requests", "/users/{userId}/events");
        return privateEventService.getAllRequestsByEvent(eventId, userId);
    }

    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResult updateRequestsForEvent(@PathVariable Long eventId,
                                                                 @PathVariable Long userId,
                                                                 @Valid @RequestBody EventRequestStatusUpdateRequest request) {
        log.info(RECEIVED_PATCH, "/{eventId}/requests", "/users/{userId}/events");
        return privateEventService.updateEventRequestsStatus(eventId, userId, request);
    }
}
