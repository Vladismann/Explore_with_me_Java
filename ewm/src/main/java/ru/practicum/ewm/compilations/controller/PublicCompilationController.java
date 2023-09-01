package ru.practicum.ewm.compilations.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilations.dto.CompilationDto;
import ru.practicum.ewm.compilations.service.CompilationService;

import java.util.List;

import static ru.practicum.dto.Common.CommonForPaths.BY_ID_PATH;
import static ru.practicum.dto.Common.Messages.RECEIVED_GET;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/compilations")
public class PublicCompilationController {

    private final CompilationService service;

    @GetMapping(BY_ID_PATH)
    CompilationDto get(@PathVariable Long id) {
        log.info(RECEIVED_GET, "/compilations", id);
        return service.get(id);
    }

    @GetMapping
    List<CompilationDto> getAll(@RequestParam(defaultValue = "false") boolean pinned,
                                @RequestParam(defaultValue = "0") int from,
                                @RequestParam(defaultValue = "10") int size) {
        log.info(RECEIVED_GET, "/compilations");
        return service.getAll(pinned, from, size);
    }
}