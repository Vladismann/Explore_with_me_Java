package ru.practicum.ewm.event.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.event.model.Location;

public interface LocationRepo extends JpaRepository<Location, Long> {
}
