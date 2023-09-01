package ru.practicum.ewm.compilations.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.common.CommonMethods;
import ru.practicum.ewm.compilations.dto.CompilationDto;
import ru.practicum.ewm.compilations.dto.CompilationMapper;
import ru.practicum.ewm.compilations.dto.NewCompilationDto;
import ru.practicum.ewm.compilations.dto.UpdateCompilationRequest;
import ru.practicum.ewm.compilations.model.Compilation;
import ru.practicum.ewm.compilations.repo.CompilationRepo;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.repo.EventRepo;
import ru.practicum.ewm.event.service.EventServiceCommon;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepo compilationRepository;
    private final EventRepo eventRepository;
    private final EventServiceCommon eventServiceCommon;

    @Override
    public CompilationDto create(NewCompilationDto newCompilationDto) {
        List<Event> events = eventRepository.findAllById(newCompilationDto.getEvents());
        Compilation compilation = CompilationMapper.newCompilationDtoToCompilation(newCompilationDto, events);
        compilation = compilationRepository.save(compilation);
        log.info("Создана подборка: {}", compilation);
        Map<Long, Integer> confirmedRequests = eventServiceCommon.getConfirmedRequests(events);
        Map<Long, Long> views = eventServiceCommon.getViews(events);
        return CompilationMapper.compilationToCompilationDto(compilation, confirmedRequests, views);
    }

    @Override
    public CompilationDto update(Long compilationId, UpdateCompilationRequest updateCompilationRequest) {
        CommonMethods.checkObjectIsExists(compilationId, compilationRepository);
        Compilation compilation = compilationRepository.getReferenceById(compilationId);
        List<Long> newEventsIds = updateCompilationRequest.getEvents();
        if (newEventsIds != null) {
            List<Event> newEvents = eventRepository.findAllById(newEventsIds);
            if (!newEvents.isEmpty() && !compilation.getEvents().equals(newEvents)) {
                compilation.setEvents(newEvents);
            }
        }
        compilation = CompilationMapper.updateCompilation(updateCompilationRequest, compilation);
        compilation = compilationRepository.save(compilation);
        log.info("Обновлена подборка: {}", compilation);
        Map<Long, Integer> confirmedRequests = eventServiceCommon.getConfirmedRequests(compilation.getEvents());
        Map<Long, Long> views = eventServiceCommon.getViews(compilation.getEvents());
        return CompilationMapper.compilationToCompilationDto(compilation, confirmedRequests, views);
    }

    @Override
    public void delete(long compilationId) {
        CommonMethods.checkObjectIsExists(compilationId, compilationRepository);
        compilationRepository.deleteById(compilationId);
        log.info("Создана удалена: {}", compilationId);
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
