package ru.practicum.ewm.requests.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.requests.model.Request;
import ru.practicum.ewm.requests.model.StateParticipation;

public interface RequestRepo extends JpaRepository<Request, Long> {

    Request findByRequestorIdAndEventId(long requesterId, long eventId);

    long countByEventIdAndStatus(long eventId, StateParticipation status);
}
