package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.UpdateEventRequest;
import ru.practicum.ewm.event.dto.search.AdminSearchRequest;
import ru.practicum.ewm.event.service.adminEvent.AdminEventService;

import javax.validation.Valid;
import java.util.List;

import static ru.practicum.dto.Common.CommonForPaths.BY_ID_PATH;
import static ru.practicum.dto.Common.Messages.RECEIVED_GET;
import static ru.practicum.dto.Common.Messages.RECEIVED_PATCH;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/events")
public class AdminEventController {

    private final AdminEventService adminEventService;

    @GetMapping
    public List<EventFullDto> getAll(@Valid AdminSearchRequest request) {
        log.info(RECEIVED_GET, "/admin/events");
        return adminEventService.getAll(request);
    }

    @PatchMapping(BY_ID_PATH)
    public EventFullDto update(@PathVariable Long id,
                               @Valid @RequestBody UpdateEventRequest updateEventRequest) {
        log.info(RECEIVED_PATCH, "/admin/events", id);
        return adminEventService.updateEvent(id, updateEventRequest);
    }
}
