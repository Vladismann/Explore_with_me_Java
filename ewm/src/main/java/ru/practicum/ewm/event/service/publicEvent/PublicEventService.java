package ru.practicum.ewm.event.service.publicEvent;

import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.search.PublicSearchRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PublicEventService {

    EventFullDto getEvent(Long eventId, HttpServletRequest servletRequest);

    List<EventFullDto> getAll(PublicSearchRequest request, HttpServletRequest servletRequest);
}
