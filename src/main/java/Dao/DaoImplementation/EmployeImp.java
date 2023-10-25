package Dao.DaoImplementation;
import ConnexionBaseDonnes.Connexion;
import Dao.EmployeDao;
import Entities.Employe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class EmployeImp implements EmployeDao {
    private static final Connection connection = Connexion.getInstance().getConnection();

    @Override
    public Optional<Employe> ajouter(Employe employe) {
        String sql = "INSERT INTO employee (code, first_name, last_name, birth_date, phone_number, email, recruited_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, employe.getMatricule());
            preparedStatement.setString(2, employe.getNom());
            preparedStatement.setString(3, employe.getPrenom());
            preparedStatement.setObject(4, employe.getDateNaissance());
            preparedStatement.setString(5, employe.getTelephone());
            preparedStatement.setString(6, employe.getEmail());
            preparedStatement.setObject(7, employe.getDateRecrutement());
            int lignesModifiees = preparedStatement.executeUpdate();
            if (lignesModifiees > 0) {
                return Optional.of(employe);
            }
        } catch (SQLException e) {
            // Gérez l'exception SQLException ici
            e.printStackTrace(); // Vous pouvez imprimer la trace de l'exception pour le débogage
            // Vous pouvez également logger l'exception ou la gérer d'une autre manière
            return Optional.empty(); // En cas d'erreur, retournez un Optional vide
        }

        return Optional.empty();

    }
    @Override
    public Optional<Integer> supprimer(Employe employe) {
        String sql = "DELETE FROM employee WHERE code = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, employe.getMatricule());
            int rowCount = preparedStatement.executeUpdate();

            if (rowCount > 0) {
                return Optional.of(rowCount);
            } else {
                return Optional.empty(); // Aucune ligne supprimée
            }
        } catch (SQLException e) {
            // Gérez l'exception SQLException ici
            e.printStackTrace(); // Vous pouvez imprimer la trace de l'exception pour le débogage
            // Vous pouvez également logger l'exception ou la gérer d'une autre manière
            return Optional.empty(); // En cas d'erreur, retournez un Optional vide
        }
    }


    @Override
    public Optional<Employe> chercher(String matricule) {
      List<Employe>employes = afficheList();
        List<Employe> employeList = employes;
        return employeList.stream()
                .filter(employe -> employe.getMatricule().equals(matricule))
                .findFirst();
    }

    @Override
    public List<Employe> afficheList() {
        List<Employe> employes = new ArrayList<>();
        String query = "SELECT * FROM employee";

        try (
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String code = resultSet.getString("code");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String phoneNumber = resultSet.getString("phone_number");
                Date birthDateSql = resultSet.getDate("birth_date");
                LocalDate birthDate = ((java.sql.Date) birthDateSql).toLocalDate();
                Date dateRecrutement = resultSet.getDate("recruited_at");
                LocalDate rec = ((java.sql.Date) dateRecrutement).toLocalDate();
                String email = resultSet.getString("email");
                employes.add(new Employe(code, firstName, lastName, birthDate, phoneNumber, email, rec));
            }
            return employes;
        } catch (SQLException e) {
            // Gérez l'exception SQL ici (par exemple, journalisation ou autre traitement)
            e.printStackTrace(); // C'est juste un exemple, vous pouvez faire autre chose ici
            throw new RuntimeException("Erreur lors de la récupération des employés.", e);
        }
    }
}
