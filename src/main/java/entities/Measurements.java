package entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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
    @NotEmpty
    @Size(min = -100, max = 100, message = "Значение должно быть в пределах от -100 до 100")
    private double value;
    @Column(name = "raining")
    @NotEmpty
    private boolean raining;
    @ManyToOne
    @JoinColumn(name = "sensors_id", referencedColumnName = "id")
    private Sensors sensor;

}
