package Dao.DaoImplementation;
import ConnexionBaseDonnes.Connexion;
import Dao.GlobaleDao;
import Entities.Client;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Optional;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class ClientImp implements GlobaleDao<Client> {
    private SessionFactory sessionFactory;
    public ClientImp() {
        // Initialise la session factory lors de la création de l'objet ClientDAO
        Configuration configuration = new Configuration().configure(); // Charge la configuration depuis hibernate.cfg.xml
        sessionFactory = configuration.buildSessionFactory();
    }
    private static final Connection connection = Connexion.getInstance().getConnection();

    public Optional<Client> ajouterClient(Client client) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(client);
            transaction.commit();
            return Optional.ofNullable(client);
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
        String sql = "DELETE FROM client WHERE code = ?";
        Connection connexion = Connexion.getInstance().getConnection();
        try (PreparedStatement preparedStatement = connexion.prepareStatement(sql)) {
            preparedStatement.setString(1, client.getCode());
            int rowCount = preparedStatement.executeUpdate();

            if (rowCount > 0) {
                return Optional.of(rowCount);
            } else {
                return Optional.empty(); // Aucune ligne supprimée
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Vous pouvez imprimer la trace de l'exception pour le débogage
            return Optional.empty(); // En cas d'erreur, retournez un Optional vide
        }
    }

    @Override
    public Optional<Client> chercher(String code) {
        List<Client> clientsOptional = afficheList();
        List<Client> clients = clientsOptional;
        return clients.stream()
                .filter(client -> client.getCode().equals(code))
                .findFirst();
    }

    @Override
    public List<Client> afficheList() {
        List<Client> clients = new ArrayList<>();
        try {
            Connection connection = Connexion.getInstance().getConnection();
            String query = "SELECT * FROM client";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String code = resultSet.getString("code");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String phoneNumber = resultSet.getString("phone_number");
                Date birthDateSql = resultSet.getDate("birth_date");
                LocalDate birthDate = ((java.sql.Date) birthDateSql).toLocalDate();
                String address = resultSet.getString("address");
                clients.add(new Client(firstName, lastName, phoneNumber, birthDate, code, address));
            }
            return clients;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
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

    public boolean update(Client client) {
    boolean updated = false;

    String sql = "UPDATE client SET first_name = ?, last_name = ?, birth_date = ?, phone_number = ?, address = ? WHERE code = ?";

    try {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, client.getNom());
        preparedStatement.setString(2, client.getPrenom());
        preparedStatement.setObject(3, client.getDateNaissance());
        preparedStatement.setString(4, client.getTelephone());
        preparedStatement.setString(5, client.getAdresse());
        preparedStatement.setString(6, client.getCode());
        updated = preparedStatement.executeUpdate() > 0;
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    return updated;
    }


}
