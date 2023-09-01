package ru.practicum.ewm.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.client.StatsClient;
import ru.practicum.dto.EndpointHitDto.AddEndpointHitDto;
import ru.practicum.dto.EndpointHitDto.EventsAndViewsDto;
import ru.practicum.ewm.event.dto.EventsAndRequests;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.requests.repo.RequestRepo;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventServiceCommon {

    private final StatsClient stats;
    private final RequestRepo requestRepo;

    public void sendHit(HttpServletRequest servletRequest) {
        AddEndpointHitDto addEndpointHitDto = AddEndpointHitDto.builder()
                .app("ewm-main-service")
                .uri(servletRequest.getRequestURI())
                .ip(servletRequest.getRemoteAddr())
                .timestamp(LocalDateTime.now())
                .build();
        log.info("Отправлена статистика {}", addEndpointHitDto);
        stats.create(addEndpointHitDto);
    }

    public Map<Long, Long> getViews(List<Event> events) {
        List<String> eventUris = events.stream().map(event -> "/events/" + event.getId()).collect(Collectors.toList());
        List<EventsAndViewsDto> eventsAndViewsDto = stats.getViews(eventUris);
        log.info("Получены просмотры событий: {}", eventsAndViewsDto);
        if (eventsAndViewsDto.isEmpty()) {
            return Map.of();
        }
        return eventsAndViewsDto.stream()
                .collect(Collectors.toMap(o -> Long.parseLong(o.getUri().replace("/events/", "")), EventsAndViewsDto::getViews));
    }

    public Map<Long, Integer> getConfirmedRequests(List<Event> events) {
        List<Long> eventsIds = events.stream().map(Event::getId).collect(Collectors.toList());
        List<EventsAndRequests> eventsAndRequests = requestRepo.findAllRequestsByEventIds(eventsIds);
        return eventsAndRequests.stream()
                .collect(Collectors.toMap(EventsAndRequests::getEventId, EventsAndRequests::getCount));
    }

}