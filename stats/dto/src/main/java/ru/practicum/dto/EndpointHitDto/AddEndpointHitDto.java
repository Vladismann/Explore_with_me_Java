package ru.practicum.dto.EndpointHitDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static ru.practicum.dto.Common.Constants.DEFAULT_DATE_FORMAT;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AddEndpointHitDto {

    private Long id;
    @NotNull
    private String app;
    @NotNull
    private String uri;
    @NotNull
    private String ip;
    @NotNull
    @JsonFormat(pattern = DEFAULT_DATE_FORMAT, shape = JsonFormat.Shape.STRING)
    private LocalDateTime timestamp;
}
