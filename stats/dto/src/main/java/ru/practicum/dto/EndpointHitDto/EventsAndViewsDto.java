package ru.practicum.dto.EndpointHitDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EventsAndViewsDto {

    @JsonProperty("uri")
    private String uri;
    @JsonProperty("views")
    private long views;
}
