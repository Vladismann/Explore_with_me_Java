package ru.practicum.ewm.event.service.publicEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.client.StatsClient;
import ru.practicum.dto.EndpointHitDto.AddEndpointHitDto;
import ru.practicum.ewm.common.CommonMethods;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.search.PublicSearchParameters;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.repo.EventRepo;
import ru.practicum.ewm.exceptions.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.ewm.event.model.EventState.PUBLISHED;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class PublicEventServiceImpl implements PublicEventService {

    private final EventRepo eventRepo;
    private final StatsClient stats;

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

    @Override
    public EventFullDto getEvent(Long eventId, HttpServletRequest servletRequest) {
        CommonMethods.checkObjectIsExists(eventId, eventRepo);
        Event event = eventRepo.getReferenceById(eventId);
        if (event.getState().equals(PUBLISHED)) {
            throw new NotFoundException(String.format("Object with id=%s was not found", eventId));
        }
        sendHit(servletRequest);
        return null;
    }

    @Override
    public List<EventFullDto> getAll(PublicSearchParameters request, HttpServletRequest servletRequest) {
        return null;
    }
}
