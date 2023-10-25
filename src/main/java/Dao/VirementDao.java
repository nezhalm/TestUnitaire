package Dao;
import Entities.Compte;
import Entities.CompteCourant;
import Entities.Virement;
import java.util.Optional;
import Enum.*;
public interface VirementDao extends GlobaleDao<Virement>{
    public Optional<Compte> versement(CompteCourant compteCourant, Double montant);
    public Optional<Compte> retrait(CompteCourant compteCourant, Double montant);
}
