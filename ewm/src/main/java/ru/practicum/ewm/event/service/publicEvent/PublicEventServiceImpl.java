package ru.practicum.ewm.event.service.publicEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.client.StatsClient;
import ru.practicum.dto.EndpointHitDto.AddEndpointHitDto;
import ru.practicum.ewm.common.CommonMethods;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventMapper;
import ru.practicum.ewm.event.dto.search.PublicSearchParameters;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.repo.EventRepo;
import ru.practicum.ewm.exceptions.NotFoundException;
import ru.practicum.ewm.requests.repo.RequestRepo;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.ewm.event.model.EventState.PUBLISHED;
import static ru.practicum.ewm.requests.model.StateParticipation.CONFIRMED;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class PublicEventServiceImpl implements PublicEventService {

    private final EventRepo eventRepo;
    private final StatsClient stats;
    private final RequestRepo requestRepo;

    private void sendHit(HttpServletRequest servletRequest) {
        AddEndpointHitDto addEndpointHitDto = AddEndpointHitDto.builder()
                .app("ewm-main-service")
                .uri(servletRequest.getRequestURI())
                .ip(servletRequest.getRemoteAddr())
                .timestamp(LocalDateTime.now())
                .build();
        log.info("Отправлена статистика {}", addEndpointHitDto);
        stats.create(addEndpointHitDto);
    }

    private Long getViews(List<Long> eventsIds) {
        return null;
    }

    @Override
    public EventFullDto getEvent(Long eventId, HttpServletRequest servletRequest) {
        CommonMethods.checkObjectIsExists(eventId, eventRepo);
        Event event = eventRepo.getReferenceById(eventId);
        if (event.getState().equals(PUBLISHED)) {
            throw new NotFoundException(String.format("Object with id=%s was not found", eventId));
        }
        sendHit(servletRequest);
        EventFullDto eventFullDto = EventMapper.eventToEventFullDto(event);
        eventFullDto.setConfirmedRequests(requestRepo.countByEventIdAndStatus(eventId, CONFIRMED));
        return null;
    }

    @Override
    public List<EventFullDto> getAll(PublicSearchParameters request, HttpServletRequest servletRequest) {
        return null;
    }
}
