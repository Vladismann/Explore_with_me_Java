package ru.practicum.dto.EndpointHitDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static ru.practicum.dto.Common.Constants.DEFAULT_DATE_FORMAT;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GetEndpointHitDto {

    private Long id;

    private String app;

    private String uri;

    private String ip;

    @JsonFormat(pattern = DEFAULT_DATE_FORMAT, shape = JsonFormat.Shape.STRING)
    private LocalDateTime timestamp;
}
