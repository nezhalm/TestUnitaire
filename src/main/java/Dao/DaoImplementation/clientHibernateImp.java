package Dao.DaoImplementation;

import Dao.GlobaleDao;
import Entities.Client;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Optional;

public class clientHibernateImp implements GlobaleDao<Client> {
    private SessionFactory sessionFactory;

    public clientHibernateImp() {
        // Initialise la session factory lors de la création de l'objet ClientImp
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
    }

    @Override
    public Optional<Client> ajouter(Client client) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.save(client);
            transaction.commit();
            return Optional.of(client);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return Optional.empty();
        } finally {
            session.close();
        }
    }

    @Override
    public Optional<Integer> supprimer(Client client) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.delete(client);
            transaction.commit();
            return Optional.of(1); // Une ligne supprimée
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return Optional.empty();
        } finally {
            session.close();
        }
    }

    @Override
    public Optional<Client> chercher(String code) {
        Session session = sessionFactory.openSession();
        Client client = session.get(Client.class, code);
        session.close();
        return Optional.ofNullable(client);
    }

    @Override
    public List<Client> afficheList() {
        Session session = sessionFactory.openSession();
        List<Client> clients = session.createQuery("FROM Client", Client.class).list();
        session.close();
        return clients;
    }


    public boolean update(Client client) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.merge(client);
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
}
