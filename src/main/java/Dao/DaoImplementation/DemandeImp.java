package Dao.DaoImplementation;
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
                this.insertUpdateHistory(number);
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
