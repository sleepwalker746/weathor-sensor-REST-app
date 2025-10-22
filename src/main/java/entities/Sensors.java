package entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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
    @NotEmpty(message = "Имя не может быть пустым!")
    @Size(min = 3, max = 30, message = "Название сенсора должно быть только от 3 до 30 символов!")
    private String name;
    @OneToMany(mappedBy = "sensor", fetch = FetchType.LAZY)
    private List<Measurements> measurements;

}
