package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.search.PublicSearchRequest;
import ru.practicum.ewm.event.service.publicEvent.PublicEventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

import static ru.practicum.dto.Common.CommonForPaths.BY_ID_PATH;
import static ru.practicum.dto.Common.Messages.RECEIVED_GET;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/events")
public class PublicEventController {

    private final PublicEventService service;

    @GetMapping(BY_ID_PATH)
    public EventFullDto get(@PathVariable Long id, HttpServletRequest request) {
        log.info(RECEIVED_GET, "/events", id);
        return service.getEvent(id, request);
    }

    @GetMapping
    public List<EventFullDto> getAll(@Valid PublicSearchRequest request,
                                     HttpServletRequest servletRequest) {
        log.info(RECEIVED_GET, "/events");
        return service.getAll(request, servletRequest);
    }
}
