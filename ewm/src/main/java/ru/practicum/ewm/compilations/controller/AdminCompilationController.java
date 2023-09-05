package ru.practicum.ewm.compilations.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilations.dto.CompilationDto;
import ru.practicum.ewm.compilations.dto.NewCompilationDto;
import ru.practicum.ewm.compilations.dto.UpdateCompilationRequest;
import ru.practicum.ewm.compilations.service.CompilationService;

import javax.validation.Valid;

import static ru.practicum.dto.Common.CommonForPaths.BY_ID_PATH;
import static ru.practicum.dto.Common.Messages.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/compilations")
public class AdminCompilationController {

    private final CompilationService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto create(@Valid @RequestBody NewCompilationDto newCompilationDto) {
        log.info(RECEIVED_POST, "/admin/compilations");
        return service.create(newCompilationDto);
    }

    @PatchMapping(BY_ID_PATH)
    public CompilationDto update(@PathVariable Long id,
                          @Valid @RequestBody UpdateCompilationRequest request) {
        log.info(RECEIVED_PATCH, "/admin/compilations", id);
        return service.update(id, request);
    }

    @DeleteMapping(BY_ID_PATH)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        log.info(RECEIVED_DELETE, "/admin/compilations", id);
        service.delete(id);
    }
}