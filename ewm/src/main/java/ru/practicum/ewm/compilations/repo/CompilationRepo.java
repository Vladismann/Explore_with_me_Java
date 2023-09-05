package ru.practicum.ewm.compilations.repo;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.compilations.model.Compilation;

import java.util.List;

public interface CompilationRepo extends JpaRepository<Compilation, Long> {

    List<Compilation> findByPinned(boolean pinned, Pageable pageable);
}
