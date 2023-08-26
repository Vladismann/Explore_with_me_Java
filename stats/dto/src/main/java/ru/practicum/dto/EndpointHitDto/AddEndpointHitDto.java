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

    @NotNull(message = "Specify the app")
    private String app;

    @NotNull(message = "Specify the uri")
    private String uri;

    @NotNull(message = "Specify the ip")
    private String ip;

    @NotNull(message = "Specify the date")
    @JsonFormat(pattern = DEFAULT_DATE_FORMAT, shape = JsonFormat.Shape.STRING)
    private LocalDateTime timestamp;
}
