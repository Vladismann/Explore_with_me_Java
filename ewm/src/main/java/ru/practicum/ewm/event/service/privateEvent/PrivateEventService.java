package ru.practicum.ewm.event.service.privateEvent;

import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.NewEventDto;

public interface PrivateEventService {

    EventFullDto createEvent(Long userId, NewEventDto newEventDto);
}
