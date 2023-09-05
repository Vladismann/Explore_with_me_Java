package ru.practicum.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.dto.EndpointHitDto.AddEndpointHitDto;
import ru.practicum.dto.EndpointHitDto.EventsAndViewsDto;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class StatsClient extends BaseClient {

    @Autowired
    public StatsClient(@Value("${stats-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build(),
                serverUrl);
    }

    public void create(AddEndpointHitDto addEndpointHitDto) {
        post("/hit", addEndpointHitDto);
    }

    public ResponseEntity<Object> get(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        String urisString = String.join(",", uris);
        Map<String, Object> parameters = Map.of(
                "start", URLEncoder.encode(start.toString(), StandardCharsets.UTF_8),
                "end", URLEncoder.encode(end.toString(), StandardCharsets.UTF_8),
                "uris", urisString,
                "unique", unique
        );
        return get("/stats?start={start}&end={end}&uris={uris}&unique={unique}", parameters);
    }

    public List<EventsAndViewsDto> getViews(List<String> uris) {
        String urisString = String.join(",", uris);
        Map<String, Object> parameters = Map.of("uris", urisString);
        ResponseEntity<String> response = rest.getForEntity(serverUrl + "/views?uris={uris}", String.class, parameters);
        if (response.getBody() == null || response.getBody().equals("[]")) {
            return new ArrayList<>();
        }
        try {
            return List.of(new ObjectMapper().readValue(response.getBody(), EventsAndViewsDto[].class));
        } catch (JsonProcessingException exception) {
            throw new RuntimeException(String.format("Ошибка обработки json. %s", exception.getMessage()));
        }
    }
}