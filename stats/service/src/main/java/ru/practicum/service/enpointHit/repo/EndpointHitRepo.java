package ru.practicum.service.enpointHit.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.dto.EndpointHitDto.GetStatsDto;
import ru.practicum.service.enpointHit.model.EndpointHit;

import java.time.LocalDateTime;
import java.util.List;

public interface EndpointHitRepo extends JpaRepository<EndpointHit, Long> {


    @Query("SELECT new ru.practicum.dto.EndpointHitDto.GetStatsDto(h.app, h.uri, count(Distinct(h.ip)) as hits) " +
            "from EndpointHit h " +
            "WHERE h.uri IN :uris " +
            "AND h.timestamp BETWEEN :start AND :end " +
            "GROUP BY h.uri, h.app " +
            "ORDER BY hits desc")
    List<GetStatsDto> findAllByTimestampBetweenAndUrisInDistinct(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, @Param("uris") List<String> uris);

    @Query("SELECT new ru.practicum.dto.EndpointHitDto.GetStatsDto(h.app, h.uri, count(h.ip) as hits) " +
            "from EndpointHit h " +
            "WHERE h.uri IN :uris " +
            "AND h.timestamp BETWEEN :start AND :end " +
            "GROUP BY h.uri, h.app " +
            "ORDER BY hits desc")
    List<GetStatsDto> findAllByTimestampBetweenAndUrisIn(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, @Param("uris") List<String> uris);

    @Query("SELECT new ru.practicum.dto.EndpointHitDto.GetStatsDto(h.app, h.uri, count(Distinct(h.ip)) as hits) " +
            "from EndpointHit h " +
            "WHERE h.timestamp BETWEEN :start AND :end " +
            "GROUP BY h.uri, h.app " +
            "ORDER BY hits desc")
    List<GetStatsDto> findAllByTimestampBetweenDistinct(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT new ru.practicum.dto.EndpointHitDto.GetStatsDto(h.app, h.uri, count(h.ip) as hits) " +
            "from EndpointHit h " +
            "WHERE h.timestamp BETWEEN :start AND :end " +
            "GROUP BY h.uri, h.app " +
            "ORDER BY hits desc")
    List<GetStatsDto> findAllByTimestampBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
