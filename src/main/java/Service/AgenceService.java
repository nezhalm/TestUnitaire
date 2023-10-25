package Service;
import Dao.AgenceDao;
import Dao.DaoImplementation.AgenceImp;
import Entities.Agence;

import java.util.List;
import java.util.Optional;

public class AgenceService  {
    private final AgenceDao agenceDao;
    public AgenceService(AgenceDao agenceDao) {
        this.agenceDao = agenceDao;
    }

    public Optional<Agence> ajouterAgence(Agence agence) {
        if (agence.getCode().isEmpty()|| agence.getNom().isEmpty()|| agence.getTele().isEmpty()||agence.getAdresse().isEmpty())
            throw new IllegalArgumentException("aucun champ doit etre vide.");
        return agenceDao.ajouter(agence);
    }
    public Optional<Agence> chercherAgence(String var) {
        if (var.isEmpty())
            throw new IllegalArgumentException("vuillez remplire le champ du code ");
        else
            return agenceDao.chercher(var);
    }

    public boolean supprimerAgence(String var) {
        Optional<Entities.Agence> result = agenceDao.chercher(var);
        if (result.isPresent()) {
            agenceDao.supprimer(result.get());
           return true;
        } else {
            return false;
        }
    }

    public List<Agence> getAllAgencies() {
        return agenceDao.afficheList();
    }
}
