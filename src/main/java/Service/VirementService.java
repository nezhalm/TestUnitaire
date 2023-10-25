package Service;
import Dao.DaoImplementation.VirementImp;
import Entities.CompteCourant;
import Entities.Virement;

import java.util.Optional;
public class VirementService {
    static final VirementImp virementImp = new VirementImp();
    public static Optional<Virement> ajouterVirement(Entities.Virement virement) {
        Optional<Entities.Virement> result = virementImp.ajouter(virement);
                if (result.isPresent()) {
                    virementImp.retrait(virement.getComptes(),virement.getMontant());
                    virementImp.versement((CompteCourant) virement.getDestinataire(),virement.getMontant());
                    return  result;
                } else {
                    return Optional.empty();
                }
    }
}
