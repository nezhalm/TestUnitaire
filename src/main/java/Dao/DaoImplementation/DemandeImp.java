package Dao.DaoImplementation;
import Service.DemandeService;
import Utils.ExtraMethods;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.*;
import Dao.DemandeDao;
import Entities.*;
import Enum.*;
import org.hibernate.cfg.Configuration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class DemandeImp implements DemandeDao {
    private static SessionFactory sessionFactory;
    public DemandeImp() {
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
    }

    @Override
    public Optional<Demande> ajouter(Demande demande) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(demande);
            transaction.commit();
            session.close();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            demande = null;
            e.printStackTrace();
        }
        return Optional.ofNullable(demande);
    }





    @Override
    public Optional<Demande> chercher(String var) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Demande> query = builder.createQuery(Demande.class);
            Root<Demande> demandeRoot = query.from(Demande.class);
            query.select(demandeRoot);
            query.where(builder.equal(demandeRoot.get("number"), var));
            Demande demande = session.createQuery(query).uniqueResult();
            return Optional.ofNullable(demande);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
    @Override
    public List<Demande> afficheList() {
        Session session = sessionFactory.openSession();
        List<Demande> demandes = session.createQuery("FROM Demande", Demande.class).list();
        for (Demande demande : demandes) {
            Hibernate.initialize(demande.getUpdateHistory());
        }
        session.close();
        return demandes;
    }

    public Optional<Demande> UpdateStatus(StatusDemande status, String number) {
        try (Session session = sessionFactory.openSession()) {
            Optional<Demande> demandeOptional = this.chercher(number);
            if (demandeOptional.isPresent()) {
                Demande demande = demandeOptional.get();
                demande.setStatus(status);
                session.beginTransaction();
                session.merge(demande);
                session.getTransaction().commit();
                return Optional.of(demande);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public boolean insertUpdateHistory(String number) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            LocalDateTime now = LocalDateTime.now();
            Demande demande = new Demande();
            UpdateDemandeHistory updateHistory = new UpdateDemandeHistory();

            demande.setNumber(number);
            updateHistory.setDemande(demande);
            updateHistory.setUpdatedAt(now);

            session.save(updateHistory);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    public List<Demande> searchDemandesByLabel(String label) {
        Session session = null;
        List<Demande> demandes = null;
        try {
            session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Demande> criteriaQuery = builder.createQuery(Demande.class);
            Root<Demande> demandeRoot = criteriaQuery.from(Demande.class);
            StatusDemande statusDemande = StatusDemande.valueOf(label);
            criteriaQuery.select(demandeRoot)
                    .where(builder.equal(demandeRoot.get("status"), statusDemande));

            demandes = session.createQuery(criteriaQuery).getResultList();
            for (Demande demande : demandes) {
                Hibernate.initialize(demande.getUpdateHistory());
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return demandes;
    }
    public List<Demande> searchDemandesByDate(LocalDate label) {
        Session session = null;
        List<Demande> demandes = null;
        try {
            session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Demande> criteriaQuery = builder.createQuery(Demande.class);
            Root<Demande> demandeRoot = criteriaQuery.from(Demande.class);
            LocalDate date = label;
            criteriaQuery.select(demandeRoot)
                    .where(builder.equal(
                            builder.function("DATE", LocalDate.class, demandeRoot.get("date")),
                            date
                    ));
            demandes = session.createQuery(criteriaQuery).getResultList();
            for (Demande demande : demandes) {
                Hibernate.initialize(demande.getUpdateHistory());
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return demandes;
    }

    @Override
    public Optional<Integer> supprimer(Demande demande) {
        return Optional.empty();
    }
}
//package Service;
//        import Dao.DaoImplementation.DemandeImp;
//        import Entities.Agence;
//        import Entities.Client;
//        import Entities.Demande;
//        import Entities.Employe;
//        import Service.DemandeService;
//        import Utils.ExtraMethods;
//        import org.junit.*;
//        import Enum.*;
//        import java.time.LocalDate;
//        import java.time.LocalDateTime;
//        import java.util.ArrayList;
//        import java.util.Collections;
//        import java.util.List;
//        import java.util.Optional;
//        import static junit.framework.TestCase.assertSame;
//        import static org.junit.jupiter.api.Assertions.assertEquals;
//        import static org.mockito.Mockito.*;
//public class DemandeServiceTest {
//    DemandeService demandeService;
//    DemandeImp demandeImp;
//    Demande demande;
//    @Before
//    public void setUp(){
//        demandeImp =mock(DemandeImp.class);
//        demandeService =new DemandeService(demandeImp);
//        String number = ExtraMethods.generateUniqueCode(6);
//        Employe employe = new Employe();
//        Agence agence = new Agence();
//        Client client = new Client();
//        employe.setMatricule("EMP001");
//        agence.setCode("7297");
//        client.setCode("001");
//        demande = new Demande(number, 3564.34, 150000, 20, "Remarque testing", LocalDateTime.now(), StatusDemande.Pending, employe, agence, client, null);
//    }
//
//    @Test
//    public void addTest() {
//        when(demandeImp.ajouter(demande)).thenReturn(Optional.of(demande));
//        Optional<Demande> optionalDemande = demandeService.ajouterDemande(demande);
//        assertSame(demande,optionalDemande.get());
//        verify(demandeImp).ajouter(demande);
//    }
//
//    @Test
//    public void getAllTest(){
//        ArrayList<Demande> list = new ArrayList<>();
//        list.add(demande);
//        when(demandeImp.afficheList()).thenReturn(list);
//        List<Demande> result = demandeService.AllDemandes();
//        assertEquals(1,result.size());
//        verify(demandeImp).afficheList();
//    }
//
//    @Test
//    public void calculeMensualiteTest(){
//        Double result =demandeService.calculeMensualite(11,560000);
//        assertEquals(54014.28,result);
//    }
//
//
//    @Test
//    public void searchDemandesByLabelTest() {
//        ArrayList<Demande> list = new ArrayList<>();
//        Employe employe = new Employe();
//        Agence agence = new Agence();
//        Client client = new Client();
//        employe.setMatricule("EMP001");
//        agence.setCode("7297");
//        client.setCode("001");
//        list.add(new Demande("435673", 4500.23, 5000, 6, "Remarque testing", LocalDateTime.now(), StatusDemande.Accepted, employe, agence, client, null));
//        list.add(new Demande("873667", 4300.23, 4000, 7, "testing", LocalDateTime.now(), StatusDemande.Rejected, employe, agence, client, null));
//        when(demandeImp.searchDemandesByLabel(String.valueOf(StatusDemande.Accepted))).thenReturn(Collections.singletonList(list.get(0)));
//        List<Demande> demandes =demandeService.searchDemandesByLabel(StatusDemande.Accepted);
//        assertEquals(list.get(0).getNumber(),demandes.get(0).getNumber());
//    }
//
//    @Test
//    public void searchDemandesByDateTest() {
//        ArrayList<Demande> list = new ArrayList<>();
//        Employe employe = new Employe();
//        Agence agence = new Agence();
//        Client client = new Client();
//        employe.setMatricule("EMP001");
//        agence.setCode("7297");
//        client.setCode("001");
//        list.add(new Demande("334654", 4500.23, 5000, 6, "Remarque testing", LocalDateTime.of(2023, 4, 3, 11, 10, 10), StatusDemande.Accepted, employe, agence, client, null));
//        list.add(new Demande("223432", 4300.23, 4000, 7, "testing",  LocalDateTime.of(2023, 4, 4, 12, 20, 20), StatusDemande.Rejected, employe, agence, client, null));
//        when(demandeImp.searchDemandesByDate(LocalDate.of(2023, 4, 4))).thenReturn(Collections.singletonList(list.get(1)));
//        List<Demande> demandes =demandeService.searchDemandesByDate(LocalDate.of(2023, 4, 4));
//        assertEquals(list.get(1).getNumber(),demandes.get(0).getNumber());
//    }
//
//    @Test
//    public void UpdateStatusTest() {
//        demande.setStatus(StatusDemande.Rejected);
//        when(demandeImp.UpdateStatus(StatusDemande.Rejected,demande.getNumber())).thenReturn(Optional.ofNullable(demande));
//        StatusDemande StatusdemandeUpdated =demandeService.UpdateStatus(StatusDemande.Rejected,demande.getNumber()).get().getStatus();
//        assertEquals(StatusDemande.Rejected,StatusdemandeUpdated);
//    }
//}
