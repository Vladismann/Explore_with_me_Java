package ru.practicum.ewm.event.repo;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.EventState;

import java.util.List;

public interface EventRepo extends JpaRepository<Event, Long> {

    List<Event> findAllByCategoryId(long categoryId);

    List<Event> findAllByInitiatorId(long initiatorId, Pageable pageable);

    List<Event> findAllByInitiatorIdInAndState(List<Long> initiatorIds, EventState state, Pageable pageable);
}
