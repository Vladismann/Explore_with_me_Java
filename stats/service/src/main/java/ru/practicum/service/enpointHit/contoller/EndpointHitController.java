package ru.practicum.service.enpointHit.contoller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.EndpointHitDto.AddEndpointHitDto;
import ru.practicum.dto.EndpointHitDto.GetEndpointHitDto;
import ru.practicum.dto.EndpointHitDto.GetStatsDto;
import ru.practicum.service.enpointHit.service.EndpointHitService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.dto.Common.Constants.DEFAULT_DATE_FORMAT;
import static ru.practicum.dto.Common.Messages.RECEIVED_GET;
import static ru.practicum.dto.Common.Messages.RECEIVED_POST;

@RestController
@RequiredArgsConstructor
@Slf4j
public class EndpointHitController {

    private final EndpointHitService endpointHitService;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public GetEndpointHitDto add(@RequestBody @Valid AddEndpointHitDto addEndpointHitDto) {
        log.info(RECEIVED_POST, "hit");
        return endpointHitService.addEndpointHit(addEndpointHitDto);
    }

    @GetMapping("/stats")
    public List<GetStatsDto> getStats(@DateTimeFormat(pattern = DEFAULT_DATE_FORMAT) LocalDateTime start,
                                      @DateTimeFormat(pattern = DEFAULT_DATE_FORMAT) LocalDateTime end,
                                      @RequestParam(name = "uris", defaultValue = "") List<String> uris,
                                      @RequestParam(name = "unique", defaultValue = "false") Boolean unique) {
        log.info(RECEIVED_GET, "stats");
        return endpointHitService.getEndpointHitStats(start, end, uris, unique);
    }
}
