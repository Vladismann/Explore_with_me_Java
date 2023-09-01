package ru.practicum.ewm.requests.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.requests.dto.ParticipationRequestDto;
import ru.practicum.ewm.requests.service.RequestService;

import java.util.List;

import static ru.practicum.dto.Common.Messages.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/requests")
public class RequestController {

    private final RequestService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ParticipationRequestDto create(@PathVariable Long userId,
                                   @RequestParam Long eventId) {
        log.info(RECEIVED_POST, "/users/{userId}/requests", eventId);
        return service.createRequest(userId, eventId);
    }

    @GetMapping
    List<ParticipationRequestDto> getAll(@PathVariable Long userId) {
        log.info(RECEIVED_GET, "/users/{userId}/requests", userId);
        return service.getAll(userId);
    }

    @PatchMapping("/{requestId}/cancel")
    ParticipationRequestDto update(@PathVariable Long userId,
                                   @PathVariable Long requestId) {
        log.info(RECEIVED_PATCH, userId, requestId);
        return service.updateRequest(userId, requestId);
    }
}
