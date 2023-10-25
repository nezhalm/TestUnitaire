package Dao.DaoImplementation;

import ConnexionBaseDonnes.Connexion;
import Dao.AgenceDao;
import Entities.Agence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import Entities.Mission;
import Enum.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import static Dao.DaoImplementation.OperationImp.genererCodeUnique;

public class AgenceImp implements AgenceDao {
    private static final Connection connection = Connexion.getInstance().getConnection();
    private SessionFactory sessionFactory;

    public AgenceImp() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    @Override
    public Optional<Agence> ajouter(Agence agence) {
        try {
            String sql = "INSERT INTO agency (code, name, address, tele) VALUES (?, ?, ?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, genererCodeUnique(4));
            preparedStatement.setString(2, agence.getNom());
            preparedStatement.setString(3, agence.getAdresse());
            preparedStatement.setString(4, agence.getTele());
            preparedStatement.executeUpdate();
            return Optional.ofNullable(agence);
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<Integer> supprimer(Agence agence) {

        String sql = "DELETE FROM agency WHERE code = ?";
        Connection connexion = Connexion.getInstance().getConnection();
        try (PreparedStatement preparedStatement = connexion.prepareStatement(sql)) {
            preparedStatement.setString(1, agence.getCode());
            int rowCount = preparedStatement.executeUpdate();

            if (rowCount > 0) {
                return Optional.of(rowCount);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<Agence> chercher(String var){
        try {
            String query = "SELECT * FROM agency";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Agence> agences = new ArrayList<>();
            while (resultSet.next()) {
                String code = resultSet.getString("code");
                String nom = resultSet.getString("name");
                String tele = resultSet.getString("tele");
                String address = resultSet.getString("address");
                agences.add(new Agence(code, nom, tele, address));
            }
            Optional<Agence> agenceTrouvee = agences.stream()
                    .filter(agence -> agence.getCode().equals(var))
                    .findFirst();
            return agenceTrouvee;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Agence> afficheList() {
        List<Agence> agences = new ArrayList<>();

        try (Session session = sessionFactory.openSession()) {
            Query<Agence> query = session.createQuery("FROM Agence", Agence.class);
            agences = query.list();
        } catch (Exception e) {
            System.out.println("Error AgenceImp -> afficheList: " + e.getMessage());
            e.printStackTrace();
        }

        return agences;
    }
}
