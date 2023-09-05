package ru.practicum.ewm.event.service.publicEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.common.CommonMethods;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventMapper;
import ru.practicum.ewm.event.dto.search.PublicSearchRequest;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.repo.EventRepo;
import ru.practicum.ewm.event.repo.EventRepoSearch;
import ru.practicum.ewm.event.service.EventServiceCommon;
import ru.practicum.ewm.exceptions.NotFoundException;
import ru.practicum.ewm.requests.repo.RequestRepo;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.practicum.ewm.event.model.EventSort.VIEWS;
import static ru.practicum.ewm.event.model.EventState.PUBLISHED;
import static ru.practicum.ewm.requests.model.StateParticipation.CONFIRMED;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class PublicEventServiceImpl implements PublicEventService {

    private final EventRepo eventRepo;
    private final RequestRepo requestRepo;
    private final EventServiceCommon eventServiceCommon;
    private final EventRepoSearch eventRepoSearch;

    @Override
    public EventFullDto getEvent(Long eventId, HttpServletRequest servletRequest) {
        CommonMethods.checkObjectIsExists(eventId, eventRepo);
        Event event = eventRepo.getReferenceById(eventId);
        if (!event.getState().equals(PUBLISHED)) {
            throw new NotFoundException(String.format("Object with id=%s was not found", eventId));
        }
        eventServiceCommon.sendHit(servletRequest);
        Map<Long, Long> views = eventServiceCommon.getViews(Collections.singletonList(event));
        EventFullDto eventFullDto = EventMapper.eventToEventFullDto(event);
        eventFullDto.setConfirmedRequests(requestRepo.countByEventIdAndStatus(eventId, CONFIRMED));
        long actualViews = 0;
        if (views != null && !views.isEmpty()) {
            actualViews = views.get(eventId);
        }
        eventFullDto.setViews(actualViews);
        log.info("Запрошено событие: {}", eventFullDto);
        return eventFullDto;
    }

    @Override
    public List<EventFullDto> getAll(PublicSearchRequest request, HttpServletRequest servletRequest) {
        if (request.getRangeEnd() != null && request.getRangeEnd().isBefore(request.getRangeStart())) {
            throw new IllegalArgumentException("The range end most be greater or equals the range start");
        }
        eventServiceCommon.sendHit(servletRequest);
        List<Event> events = eventRepoSearch.findAllForPublic(request);
        if (events.isEmpty()) {
            return List.of();
        }
        Map<Long, Integer> confirmedRequests = eventServiceCommon.getConfirmedRequests(events);

        if (request.getOnlyAvailable() != null && request.getOnlyAvailable() && !confirmedRequests.isEmpty()) {
            events = events.stream()
                    .filter(event -> confirmedRequests.get(event.getId()) == null || event.getParticipantLimit() != confirmedRequests.get(event.getId()))
                    .collect(Collectors.toList());
        }
        Map<Long, Long> views = eventServiceCommon.getViews(events);
        List<EventFullDto> eventFullDto = EventMapper.eventToEventFullDto(events);
        eventFullDto = EventMapper.setViewsAndRequestForListEventFullDto(eventFullDto, views, confirmedRequests);

        if (request.getSort() != null && request.getSort().equals(VIEWS)) {
            eventFullDto = eventFullDto.stream().sorted(Comparator.comparingLong(EventFullDto::getViews).reversed()).collect(Collectors.toList());
        } else {
            eventFullDto = eventFullDto.stream().sorted(Comparator.comparing(EventFullDto::getEventDate).reversed()).collect(Collectors.toList());
        }
        log.info("Запрошен список событий в размере: {}", eventFullDto.size());
        return eventFullDto;
    }
}
