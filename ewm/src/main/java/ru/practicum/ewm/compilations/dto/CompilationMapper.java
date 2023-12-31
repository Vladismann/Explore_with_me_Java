package ru.practicum.ewm.compilations.dto;


import lombok.experimental.UtilityClass;
import ru.practicum.ewm.compilations.model.Compilation;
import ru.practicum.ewm.event.dto.EventMapper;
import ru.practicum.ewm.event.model.Event;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@UtilityClass
public class CompilationMapper {

    public Compilation newCompilationDtoToCompilation(NewCompilationDto newCompilationDto, List<Event> events) {
        return Compilation.builder()
                .title(newCompilationDto.getTitle())
                .pinned(newCompilationDto.isPinned())
                .events(events)
                .build();
    }

    public CompilationDto compilationToCompilationDto(Compilation compilation, Map<Long, Integer> confirmedRequests, Map<Long, Long> views) {
        return CompilationDto.builder()
                .events(EventMapper.eventToEventShortDto(compilation.getEvents(), confirmedRequests, views))
                .id(compilation.getId())
                .pinned(compilation.isPinned())
                .title(compilation.getTitle())
                .build();
    }

    public List<CompilationDto> compilationToCompilationDto(List<Compilation> compilations, Map<Long, Integer> confirmedRequests, Map<Long, Long> views) {
        return compilations.stream().map(compilation -> CompilationMapper.compilationToCompilationDto(compilation, confirmedRequests, views)).collect(Collectors.toList());
    }

    public Compilation updateCompilation(UpdateCompilationRequest newCompilationDto, Compilation compilation) {
        if (newCompilationDto.getPinned() != null) {
            compilation.setPinned(newCompilationDto.getPinned());
        }
        if (newCompilationDto.getTitle() != null && !newCompilationDto.getTitle().isBlank()) {
            compilation.setTitle(newCompilationDto.getTitle());
        }
        return compilation;
    }
}
