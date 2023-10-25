package Service;

import Dao.AgenceDao;
import Dao.DaoImplementation.AgenceImp;
import Dao.DaoImplementation.ClientImp;
import Dao.DaoImplementation.DemandeImp;
import Dao.DaoImplementation.EmployeImp;
import Dao.DemandeDao;
import Entities.*;
import Enum.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public class DemandeService {
    static final EmployeImp employeImp = new EmployeImp();
    static final AgenceImp agenceImp = new AgenceImp();
    static final ClientImp clientImp = new ClientImp();

    private final DemandeImp demandeImp;

    public DemandeService(DemandeImp demandeImp) {

        this.demandeImp = demandeImp;
    }

    public Optional<Demande> ajouterDemande(Demande d) {
        Optional<Demande> result = Optional.empty();
        if (
            employeImp.chercher(d.getEmploye().getMatricule()).isPresent() &&
            agenceImp.chercher(d.getAgence().getCode()).isPresent() &&
            clientImp.chercher(d.getClient().getCode()).isPresent() &&
            d.getPrice() > 0 &&
            d.getDuration() > 0
        ) {
            double tauxInteretMensuel = 0.12 / 12.0;
            double denominateur = 1 - Math.pow(1 + tauxInteretMensuel, -d.getDuration());
            double mensualite = d.getPrice() * tauxInteretMensuel / denominateur;
            d.setMonsualite(mensualite);
            result = demandeImp.ajouter(d);
        }
        return result;
    }

    public List<Demande> AllDemandes() {
        List<Demande> demandeList = demandeImp.afficheList();
        demandeList.forEach(demande -> {
            BigDecimal bigDecimal = new BigDecimal(Double.toString(demande.getMonsualite()));
            bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
            demande.setMonsualite(bigDecimal.doubleValue());
        });

        return demandeList;
    }

    public List<Demande> searchDemandesByLabel(StatusDemande label) {
        String statusString = label.toString();
        List<Demande> demandeList = demandeImp.searchDemandesByLabel(statusString);
        demandeList.forEach(demande -> {
            BigDecimal bigDecimal = new BigDecimal(Double.toString(demande.getMonsualite()));
            bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
            demande.setMonsualite(bigDecimal.doubleValue());
        });
        return demandeList;
    }

    public List<Demande> searchDemandesByDate(LocalDate label) {
        List<Demande> demandeList = demandeImp.searchDemandesByDate(label);
        demandeList.forEach(demande -> {
            BigDecimal bigDecimal = new BigDecimal(Double.toString(demande.getMonsualite()));
            bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
            demande.setMonsualite(bigDecimal.doubleValue());
        });
        return demandeList;
    }

    public Optional<Demande> UpdateStatus(StatusDemande status,String number) {
        Optional<Demande> DemandeUpdated = demandeImp.UpdateStatus(status,number);
        return DemandeUpdated;
    }
}
