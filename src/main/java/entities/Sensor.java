package entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table (name = "sensors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    @NotEmpty(message = "Имя не может быть пустым!")
    @Size(min = 3, max = 30, message = "Название сенсора должно быть только от 3 до 30 символов!")
    private String name;
    @Column(name = "value")
    @NotEmpty
    @Size(min = -100, max = 100, message = "Значение должно быть в пределах от -100 до 100")
    private int values;
    @Column(name = "raining")
    private boolean raining;
}
