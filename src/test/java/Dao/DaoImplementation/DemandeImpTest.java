package Dao.DaoImplementation;
import Entities.Agence;
import Entities.Client;
import Entities.Demande;
import Entities.Employe;
import Utils.ExtraMethods;
import Enum.StatusDemande;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.Optional;

public class DemandeImpTest {
    private static DemandeImp demandeImp;

    @BeforeAll
    public static void setUp() {
        demandeImp = new DemandeImp();
    }

    @Test
    public void createTest() {
        String number = ExtraMethods.generateUniqueCode(6);
        Employe employe = new Employe();
        Agence agence = new Agence();
        Client client = new Client();
        employe.setMatricule("EMP001");
        agence.setCode("7297");
        client.setCode("001");
        Demande demande = new Demande(
                number,
                4435.34,
                150000,
                20,
                "Remarque testing",
                LocalDateTime.now(),
                StatusDemande.Pending,
                employe,
                agence,
                client,
                null
        );
        Optional<Demande> newDemande = demandeImp.ajouter(demande);
        assertTrue(newDemande.isPresent());
    }
}
