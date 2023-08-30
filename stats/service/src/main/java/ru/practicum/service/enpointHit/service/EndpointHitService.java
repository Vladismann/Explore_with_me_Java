package ru.practicum.service.enpointHit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.EndpointHitDto.AddEndpointHitDto;
import ru.practicum.dto.EndpointHitDto.EventsAndViewsDto;
import ru.practicum.dto.EndpointHitDto.GetEndpointHitDto;
import ru.practicum.dto.EndpointHitDto.GetStatsDto;
import ru.practicum.service.enpointHit.model.EndpointHitMapper;
import ru.practicum.service.enpointHit.repo.EndpointHitRepo;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EndpointHitService {

    private final EndpointHitRepo endpointHitRepo;

    public GetEndpointHitDto addEndpointHit(AddEndpointHitDto addEndpointHitDto) {
        return EndpointHitMapper.toGetEndpointHitDto(endpointHitRepo.save(EndpointHitMapper.toEndpointHit(addEndpointHitDto)));
    }

    @Transactional(readOnly = true)
    public List<GetStatsDto> getEndpointHitStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        if (unique) {
            return endpointHitRepo.findAllByTimestampBetweenAndUrisInDistinct(start, end, uris);
        } else {
            return endpointHitRepo.findAllByTimestampBetweenAndUrisIn(start, end, uris);
        }
    }

    @Transactional(readOnly = true)
    public List<EventsAndViewsDto> getEventViews(List<String> uris) {
        return endpointHitRepo.findEventsViews(uris);
    }
}
