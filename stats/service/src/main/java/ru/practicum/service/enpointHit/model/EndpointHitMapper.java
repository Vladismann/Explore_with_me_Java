package ru.practicum.service.enpointHit.model;

import lombok.experimental.UtilityClass;
import ru.practicum.dto.EndpointHitDto.AddEndpointHitDto;
import ru.practicum.dto.EndpointHitDto.GetEndpointHitDto;

@UtilityClass
public class EndpointHitMapper {

    public EndpointHit toEndpointHit(AddEndpointHitDto addEndpointHitDto) {
        return EndpointHit.builder()
                .app(addEndpointHitDto.getApp())
                .uri(addEndpointHitDto.getUri())
                .ip(addEndpointHitDto.getIp())
                .timestamp(addEndpointHitDto.getTimestamp())
                .build();
    }

    public GetEndpointHitDto toGetEndpointHitDto(EndpointHit endpointHit) {
        return GetEndpointHitDto.builder()
                .id(endpointHit.getId())
                .app(endpointHit.getApp())
                .uri(endpointHit.getUri())
                .ip(endpointHit.getIp())
                .timestamp(endpointHit.getTimestamp())
                .build();
    }
}
