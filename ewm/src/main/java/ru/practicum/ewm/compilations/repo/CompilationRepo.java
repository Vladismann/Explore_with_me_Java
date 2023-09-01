package ru.practicum.ewm.compilations.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.compilations.model.Compilation;

public interface CompilationRepo extends JpaRepository<Compilation, Long> {
}
