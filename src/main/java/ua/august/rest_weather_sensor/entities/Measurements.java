package ua.august.rest_weather_sensor.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "measurements")
public class Measurements {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "value")
    @Min(value = -100, message = "Value must be between -100 and 100")
    @Max(value = 100, message = "Value must be between -100 and 100")
    @NotNull
    private double value;
    @Column(name = "raining")
    private boolean raining;
    @ManyToOne
    @JoinColumn(name = "sensors_id", referencedColumnName = "id")
    private Sensors sensor;

}
