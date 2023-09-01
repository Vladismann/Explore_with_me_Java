package ru.practicum.ewm.compilations.dto;

import java.util.List;

public class UpdateCompilationRequest {

    private List<Long> events;
    private Boolean pinned;
    private String title;
}
