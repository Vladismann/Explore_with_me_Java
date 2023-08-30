package ru.practicum.ewm.event.service.adminEvent;

import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.UpdateEventUserRequest;
import ru.practicum.ewm.event.dto.search.AdminSearchParameters;

import java.util.List;

public class AdminEventServiceImpl implements AdminEventService {


    @Override
    public EventFullDto update(Long eventId, UpdateEventUserRequest request) {
        return null;
    }

    @Override
    public List<EventFullDto> getAll(AdminSearchParameters request) {
        return null;
    }
}
