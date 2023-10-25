package Entities;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class RecrutementHistorique {

    private Employe employe;
    private Agence agence;
    private LocalDateTime recrute;
    private LocalDateTime fini;

    @Override
    public String toString() {
        return "IRecruitmentHistoryDAO{" +
                "employee=" + employe +
                ", agency=" + agence +
                ", recruitedAt=" + recrute +
                ", finishedAt=" + fini +
                '}';
    }
}


