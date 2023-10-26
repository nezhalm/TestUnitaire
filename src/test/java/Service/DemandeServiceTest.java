package Service;
import Dao.DaoImplementation.DemandeImp;
import Entities.Agence;
import Entities.Client;
import Entities.Demande;
import Entities.Employe;
import Service.DemandeService;
import Utils.ExtraMethods;
import org.junit.*;
import Enum.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static junit.framework.TestCase.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
public class DemandeServiceTest {
    DemandeService demandeService;
    DemandeImp demandeImp;
    Demande demande;
    @Before
    public void setUp(){
        demandeImp =mock(DemandeImp.class);
        demandeService =new DemandeService(demandeImp);
        String number = ExtraMethods.generateUniqueCode(6);
        Employe employe = new Employe();
        Agence agence = new Agence();
        Client client = new Client();
        employe.setMatricule("EMP001");
        agence.setCode("7297");
        client.setCode("001");
        demande = new Demande(number, 3564.34, 150000, 20, "Remarque testing", LocalDateTime.now(), StatusDemande.Pending, employe, agence, client, null);
    }



    @Test
   public void ajouterDemande() {
        when(demandeImp.ajouter(demande)).thenReturn(Optional.of(demande));
        Optional<Demande> optionalDemande = demandeService.ajouterDemande(demande);
        assertSame(demande,optionalDemande.get());
        verify(demandeImp).ajouter(demande);
    }

    @Test
    public void calculeMensualite() {
        Double result =demandeService.calculeMensualite(11,560000);
        assertEquals(54014.28,result);
    }

    @Test
    public void allDemandes() {
        ArrayList<Demande> list = new ArrayList<>();
        list.add(demande);
        when(demandeImp.afficheList()).thenReturn(list);
        List<Demande> result = demandeService.AllDemandes();
        assertEquals(1,result.size());
        verify(demandeImp).afficheList();
    }

    @Test
    public  void searchDemandesByLabel() {
        ArrayList<Demande> list = new ArrayList<>();
        Employe employe = new Employe();
        Agence agence = new Agence();
        Client client = new Client();
        employe.setMatricule("EMP001");
        agence.setCode("7297");
        client.setCode("001");
        list.add(new Demande("435673", 4500.23, 5000, 6, "Remarque testing", LocalDateTime.now(), StatusDemande.Accepted, employe, agence, client, null));
        list.add(new Demande("873667", 4300.23, 4000, 7, "testing", LocalDateTime.now(), StatusDemande.Rejected, employe, agence, client, null));
        when(demandeImp.searchDemandesByLabel(String.valueOf(StatusDemande.Accepted))).thenReturn(Collections.singletonList(list.get(0)));
        List<Demande> demandes =demandeService.searchDemandesByLabel(StatusDemande.Accepted);
        assertEquals(list.get(0).getNumber(),demandes.get(0).getNumber());
    }

    @Test
    public void searchDemandesByDate() {
        ArrayList<Demande> list = new ArrayList<>();
        Employe employe = new Employe();
        Agence agence = new Agence();
        Client client = new Client();
        employe.setMatricule("EMP001");
        agence.setCode("7297");
        client.setCode("001");
        list.add(new Demande("334654", 4500.23, 5000, 6, "Remarque testing", LocalDateTime.of(2023, 4, 3, 11, 10, 10), StatusDemande.Accepted, employe, agence, client, null));
        list.add(new Demande("223432", 4300.23, 4000, 7, "testing",  LocalDateTime.of(2023, 4, 4, 12, 20, 20), StatusDemande.Rejected, employe, agence, client, null));
        when(demandeImp.searchDemandesByDate(LocalDate.of(2023, 4, 4))).thenReturn(Collections.singletonList(list.get(1)));
        List<Demande> demandes =demandeService.searchDemandesByDate(LocalDate.of(2023, 4, 4));
        assertEquals(list.get(1).getNumber(),demandes.get(0).getNumber());
    }

    @Test
    public  void updateStatus() {
        demande.setStatus(StatusDemande.Rejected);
        when(demandeImp.UpdateStatus(StatusDemande.Rejected,demande.getNumber())).thenReturn(Optional.ofNullable(demande));
        StatusDemande StatusdemandeUpdated =demandeService.UpdateStatus(StatusDemande.Rejected,demande.getNumber()).get().getStatus();
        assertEquals(StatusDemande.Rejected,StatusdemandeUpdated);
    }

    @Test
    public void insertStatusHistory() {
        when(demandeImp.insertUpdateHistory("222222")).thenReturn(true);
        boolean var = demandeService.insertStatusHistory("222222");
        assertEquals(true,var);
    }
}
