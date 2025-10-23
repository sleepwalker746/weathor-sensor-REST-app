package ua.august.rest_weather_sensor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeasurementsDTO {

    private double value;

    private boolean raining;

    private String sensorsName;
}
