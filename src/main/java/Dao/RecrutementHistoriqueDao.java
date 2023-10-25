package Dao;

import Entities.Agence;
import Entities.Employe;
import Entities.RecrutementHistorique;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RecrutementHistoriqueDao {
    public Optional<RecrutementHistorique> AffecterEmploye(RecrutementHistorique recrutementHistorique) ;
    public Optional<RecrutementHistorique> MuterEmploye(Employe employe, Agence agence1, Agence agence2, LocalDateTime datedebut) ;
    public List<RecrutementHistorique> HistoriquesAffectations() ;


}
