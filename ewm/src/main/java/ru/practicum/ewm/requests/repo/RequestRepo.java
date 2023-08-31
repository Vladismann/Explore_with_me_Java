package ru.practicum.ewm.requests.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.event.dto.EventsAndRequests;
import ru.practicum.ewm.requests.model.Request;
import ru.practicum.ewm.requests.model.StateParticipation;

import java.util.List;

public interface RequestRepo extends JpaRepository<Request, Long> {

    Request findByRequesterIdAndEventId(long requesterId, long eventId);

    long countByEventIdAndStatus(long eventId, StateParticipation status);

    @Query(value = "SELECT event_id as eventId, count(requester_id) as count " +
            "FROM requests " +
            "WHERE event_id IN :eventIds AND status = 'CONFIRMED' " +
            "GROUP BY eventId",
            nativeQuery = true)
    List<EventsAndRequests> findAllRequestsByEventIds(List<Long> eventIds);
}
