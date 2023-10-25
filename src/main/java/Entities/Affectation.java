package Entities;
import lombok.*;
import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Affectation {
    private Mission mission;
    private Employe employe;
    private LocalDate debutAffectation;
    private LocalDate finAffectation;
}
