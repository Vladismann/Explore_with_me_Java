package ru.practicum.dto.EndpointHitDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetStatsDto {

    private String app;
    private String uri;
    private Long hits;
}
