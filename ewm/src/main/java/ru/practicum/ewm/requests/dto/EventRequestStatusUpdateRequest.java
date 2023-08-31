package ru.practicum.ewm.requests.dto;

import ru.practicum.ewm.requests.model.StateParticipation;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class EventRequestStatusUpdateRequest {

    @NotBlank
    private List<Long> requestIds;
    @NotNull
    private StateParticipation status;
}
