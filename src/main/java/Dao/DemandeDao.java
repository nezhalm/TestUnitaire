package Dao;
import Enum.*;
import Entities.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DemandeDao extends GlobaleDao<Demande> {
    public Optional<Demande> UpdateStatus(StatusDemande status, String number) ;
    public List<Demande> searchDemandesByLabel(String label);
    public List<Demande> searchDemandesByDate(LocalDate label) ;
}
