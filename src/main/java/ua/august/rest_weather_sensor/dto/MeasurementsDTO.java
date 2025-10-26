package ua.august.rest_weather_sensor.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeasurementsDTO {

    @Min(value = -100, message = "Value must be between -100 and 100")
    @Max(value = 100, message = "Value must be between -100 and 100")
    private double value;


    private boolean raining;

    @NotBlank(message = "Sensor name should not be blank!")
    @Size(min = 3, max = 30, message = "Sensor name must be between 3 and 30 characters long!")
    private String sensorsName;
}
