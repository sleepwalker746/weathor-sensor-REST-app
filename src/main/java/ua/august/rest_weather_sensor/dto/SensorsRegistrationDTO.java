package ua.august.rest_weather_sensor.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorsRegistrationDTO {

    @NotBlank(message = "Sensor name should not be blank!")
    @Size(min = 3, max = 30, message = "Sensor name must be between 3 and 30 characters long!")
    private String name;

}
