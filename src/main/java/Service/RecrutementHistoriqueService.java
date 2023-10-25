package Service;
import Dao.DaoImplementation.AgenceImp;
import Dao.DaoImplementation.EmployeImp;
import Dao.RecrutementHistoriqueDao;
import Entities.Agence;
import Entities.Employe;
import Entities.RecrutementHistorique;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class RecrutementHistoriqueService {
    static final EmployeImp employeImp = new EmployeImp();
    static final AgenceImp agenceImp = new AgenceImp();
    private final RecrutementHistoriqueDao recrutementhistoriqueDao;

    public RecrutementHistoriqueService(RecrutementHistoriqueDao recrutementhistoriqueDao) {
        this.recrutementhistoriqueDao = recrutementhistoriqueDao;
    }

    public Optional<RecrutementHistorique> AffecterEmploye(String codeEmploye, String codeAgence, LocalDateTime datedebut) {
        Optional<RecrutementHistorique> result = null;
        if ( employeImp.chercher(codeEmploye).isPresent() && agenceImp.chercher(codeAgence).isPresent()) {
            RecrutementHistorique recrutementHistorique = new RecrutementHistorique(employeImp.chercher(codeEmploye).get(), agenceImp.chercher(codeAgence).get(), datedebut, datedebut);
            result = recrutementhistoriqueDao.AffecterEmploye(recrutementHistorique);
        }
        return result;
    }


    public Optional<RecrutementHistorique> MuterEmploye(String codeEmploye, String codeAgenceactuelle,String codeNouvelAgence, LocalDateTime datedebut) {
        Optional<Employe> employe = employeImp.chercher(codeEmploye);
        Optional<Agence> agence1 = agenceImp.chercher(codeAgenceactuelle);
        Optional<Agence> agence2 = agenceImp.chercher(codeNouvelAgence);
        Optional<RecrutementHistorique> result = null;
        if (employe.isPresent() && agence1.isPresent() && agence2.isPresent()) {
            result = recrutementhistoriqueDao.MuterEmploye(employe.get(), agence1.get(), agence2.get(), datedebut);
        }
        return result;
    }

    public List<RecrutementHistorique> HistoriquesAffectations() {
      return   recrutementhistoriqueDao.HistoriquesAffectations();
    }
}
