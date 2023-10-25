package Dao.DaoImplementation;

import ConnexionBaseDonnes.Connexion;
import Dao.MissionDao;
import Entities.Mission;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MissionImp implements MissionDao {
    private static final Connection connection = Connexion.getInstance().getConnection();

    @Override
    public Optional<Mission> ajouter(Mission mission) {
        try {
            String sql = "INSERT INTO mission (code, name, description) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, mission.getCode());
            preparedStatement.setString(2, mission.getNom());
            preparedStatement.setString(3, mission.getDescription());
            preparedStatement.executeUpdate();

            return Optional.ofNullable(mission);
        } catch (SQLException e) {
            // Gérez l'exception SQLException ici
            e.printStackTrace(); // Vous pouvez imprimer la trace de l'exception pour le débogage
            // Vous pouvez également logger l'exception ou la gérer d'une autre manière
            return Optional.empty(); // En cas d'erreur, retournez un Optional vide
        }
    }



    @Override
    public Optional<Integer> supprimer(Mission mission) {
        String sql = "DELETE FROM mission WHERE code = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, mission.getCode());
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
    public Optional<Mission> chercher(String var) {
       List<Mission> missions = afficheList();
        List<Mission> missionList = missions;
        return missionList.stream()
                .filter(client -> client.getCode().equals(var))
                .findFirst();
    }

    @Override
    public List<Mission> afficheList() {
        List<Mission> missions = new ArrayList<>();
        try {
            String query = "SELECT * FROM mission";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String code = resultSet.getString("code");
                String nom = resultSet.getString("name");
                String description = resultSet.getString("description");

                missions.add(new Mission(code, nom, description));
            }
            return missions;
        } catch (SQLException e) {
            // Gérez l'exception SQL ici (par exemple, journalisation ou autre traitement)
            e.printStackTrace(); // C'est juste un exemple, vous pouvez faire autre chose ici
            return null; // Retournez une Optional vide en cas d'erreur
        }
    }

}
