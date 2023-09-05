package ru.practicum.ewm.requests.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.requests.model.StateParticipation;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EventRequestStatusUpdateRequest {

    @NotEmpty
    private List<Long> requestIds;
    @NotNull
    private StateParticipation status;
}
