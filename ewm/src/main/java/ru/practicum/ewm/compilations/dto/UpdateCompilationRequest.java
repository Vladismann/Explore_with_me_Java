package ru.practicum.ewm.compilations.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCompilationRequest {

    private List<Long> events;
    private Boolean pinned;
    private String title;
}
