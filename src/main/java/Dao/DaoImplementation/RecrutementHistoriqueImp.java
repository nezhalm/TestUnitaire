package Dao.DaoImplementation;

import ConnexionBaseDonnes.Connexion;
import Dao.RecrutementHistoriqueDao;
import Entities.Agence;
import Entities.Employe;
import Entities.RecrutementHistorique;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class RecrutementHistoriqueImp implements RecrutementHistoriqueDao {
    private static final Connection connection = Connexion.getInstance().getConnection();


    @Override
    public Optional<RecrutementHistorique> AffecterEmploye(RecrutementHistorique recrutementHistorique) {
        try {
            String sql = "INSERT INTO recruitmenthistory (employeecode, agencycode, recruitedat, finishedat) VALUES (?, ?, ?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, recrutementHistorique.getEmploye().getMatricule());
            preparedStatement.setString(2, recrutementHistorique.getAgence().getCode());
            LocalDateTime dateEtHeureCreation = LocalDateTime.now();
            // Convertissez la date et l'heure actuelles en java.sql.Timestamp
            Timestamp timestamp = Timestamp.valueOf(dateEtHeureCreation);
            preparedStatement.setTimestamp(3, timestamp);
            preparedStatement.setTimestamp(4, timestamp);
            preparedStatement.executeUpdate();
            return Optional.ofNullable(recrutementHistorique);
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<RecrutementHistorique> MuterEmploye(Employe employe, Agence agence1,Agence agence2, LocalDateTime datedebut) {
        String updateSql = "UPDATE recruitmenthistory SET  finishedat = ? WHERE employeeCode = ? AND agencyCode = ?";
        RecrutementHistorique recrutementHistorique = null;
        try (PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {
            Timestamp timestamp = Timestamp.valueOf(datedebut);
            updateStatement.setTimestamp(1, timestamp);
            updateStatement.setString(2, employe.getMatricule());
            updateStatement.setString(3, agence1.getCode());
            int updatedRows = updateStatement.executeUpdate();
            if (updatedRows == 1) {
                recrutementHistorique = new RecrutementHistorique(employe, agence2, datedebut, datedebut);
                AffecterEmploye(recrutementHistorique);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
        return Optional.ofNullable(recrutementHistorique);
    }

    @Override
    public List<RecrutementHistorique> HistoriquesAffectations() {
        try {
            String query = "SELECT * FROM recruitmenthistory";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<RecrutementHistorique> recrutementHistoriques = new ArrayList<>();
            while (resultSet.next()) {
                EmployeImp employeImp =new EmployeImp();
                AgenceImp agenceImp =new AgenceImp();

                String employeecode = resultSet.getString("employeecode");
                String agencycode = resultSet.getString("agencycode");
                LocalDateTime recruitedat = resultSet.getTimestamp("recruitedat").toLocalDateTime();
                LocalDateTime finishedat = resultSet.getTimestamp("finishedat").toLocalDateTime();
                recrutementHistoriques.add(new RecrutementHistorique(employeImp.chercher(employeecode).get(), agenceImp.chercher(agencycode).get(), recruitedat, finishedat));
            }

            return recrutementHistoriques;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
