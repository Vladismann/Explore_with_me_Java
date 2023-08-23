package EndpointHitDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetEndpointHitDto {

    private String app;
    private String uri;
    private Long hits;
}
