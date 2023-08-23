package ru.practicum.service.enpointHit.model;

import lombok.experimental.UtilityClass;
import ru.practicum.dto.EndpointHitDto.AddEndpointHitDto;

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

    public AddEndpointHitDto toAddEndpointHitDto(EndpointHit endpointHit) {
        return AddEndpointHitDto.builder()
                .id(endpointHit.getId())
                .app(endpointHit.getApp())
                .uri(endpointHit.getUri())
                .ip(endpointHit.getIp())
                .timestamp(endpointHit.getTimestamp())
                .build();
    }
}
