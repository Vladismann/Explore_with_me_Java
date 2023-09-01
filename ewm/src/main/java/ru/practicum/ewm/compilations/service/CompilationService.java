package ru.practicum.ewm.compilations.service;

import ru.practicum.ewm.compilations.dto.CompilationDto;
import ru.practicum.ewm.compilations.dto.NewCompilationDto;
import ru.practicum.ewm.compilations.dto.UpdateCompilationRequest;

import java.util.List;

public interface CompilationService {

    CompilationDto create(NewCompilationDto newCompilationDto);

    CompilationDto update(Long compilationId, UpdateCompilationRequest request);

    void delete(long compilationId);

    CompilationDto get(long compilationId);

    List<CompilationDto> getAll(boolean pinned, int from, int size);
}
