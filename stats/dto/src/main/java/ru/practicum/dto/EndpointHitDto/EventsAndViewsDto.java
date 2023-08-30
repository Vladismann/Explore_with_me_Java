package ru.practicum.dto.EndpointHitDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EventsAndViewsDto {

    private String uri;
    private long views;
}
