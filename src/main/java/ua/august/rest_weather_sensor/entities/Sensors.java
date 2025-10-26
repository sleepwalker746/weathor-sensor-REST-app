package ua.august.rest_weather_sensor.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table (name = "sensors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sensors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    @NotBlank(message = "Sensor name should not be blank!")
    @Size(min = 3, max = 30, message = "Sensor name must be between 3 and 30 characters long!")
    private String name;
    @OneToMany(mappedBy = "sensor", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Measurements> measurements;

}
