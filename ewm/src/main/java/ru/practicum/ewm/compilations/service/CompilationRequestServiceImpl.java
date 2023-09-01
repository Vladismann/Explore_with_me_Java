package ru.practicum.ewm.compilations.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.compilations.dto.CompilationDto;
import ru.practicum.ewm.compilations.dto.NewCompilationDto;
import ru.practicum.ewm.compilations.dto.UpdateCompilationRequest;
import ru.practicum.ewm.compilations.repo.CompilationRepo;
import ru.practicum.ewm.event.repo.EventRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CompilationRequestServiceImpl implements CompilationService {

    private final CompilationRepo compilationRepository;
    private final EventRepo eventRepository;

    @Override
    public CompilationDto create(NewCompilationDto newCompilationDto) {
        return null;
    }

    @Override
    public CompilationDto update(Long compilationId, UpdateCompilationRequest request) {
        return null;
    }

    @Override
    public void delete(long compilationId) {

    }

    @Override
    public CompilationDto get(long compilationId) {
        return null;
    }

    @Override
    public List<CompilationDto> getAll(boolean pinned, int from, int size) {
        return null;
    }
}
