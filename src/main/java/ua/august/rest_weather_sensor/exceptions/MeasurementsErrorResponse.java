package ua.august.rest_weather_sensor.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MeasurementsErrorResponse {
    private String message;
    private long timestamp;
}
