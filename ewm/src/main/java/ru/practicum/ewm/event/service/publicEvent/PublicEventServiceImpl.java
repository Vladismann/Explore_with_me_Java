package ru.practicum.ewm.event.service.publicEvent;

import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.search.PublicSearchParameters;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class PublicEventServiceImpl implements PublicEventService {
    @Override
    public EventFullDto get(Long eventId, HttpServletRequest servletRequest) {
        return null;
    }

    @Override
    public List<EventFullDto> getAll(PublicSearchParameters request, HttpServletRequest servletRequest) {
        return null;
    }
}
