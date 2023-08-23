package statsDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;

import static Common.Constants.DEFAULT_DATE_FORMAT;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HitDto {

    @NotNull
    private String app;
    @NotNull
    private String uri;
    @NotNull
    private String ip;
    @JsonFormat(pattern = DEFAULT_DATE_FORMAT, shape = JsonFormat.Shape.STRING)
    private LocalDateTime timestamp;
}
