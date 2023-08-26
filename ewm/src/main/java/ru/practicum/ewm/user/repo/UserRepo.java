package ru.practicum.ewm.user.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.user.model.User;

public interface UserRepo extends JpaRepository<User, Long> {
}
