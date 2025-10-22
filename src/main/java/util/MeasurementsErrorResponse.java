package util;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MeasurementsErrorResponse {
    private String message;
    private long timestamp;
}
