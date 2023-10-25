package Dao.DaoImplementation;
import Enum.*;
import ConnexionBaseDonnes.Connexion;
import Dao.CompteEpargnesDao;
import Entities.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import Enum.*;

public class CompteEpargneImp implements CompteEpargnesDao {
    private static final Connection connection = Connexion.getInstance().getConnection();
@Override
public Optional<CompteEpargne> ajouter(CompteEpargne compteEpargne) {
    try {
        String sql = "INSERT INTO saving_account (number, balance, created_at, interest_rate, employee_code, client_code) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, compteEpargne.getNumero());
        preparedStatement.setDouble(2, compteEpargne.getSolde());
        preparedStatement.setDate(3, java.sql.Date.valueOf(compteEpargne.getDateCreation()));
        preparedStatement.setDouble(4, compteEpargne.getTauxInterer());
        preparedStatement.setString(5, compteEpargne.getEmploye().getMatricule());
        preparedStatement.setString(6, compteEpargne.getClient().getCode());
        preparedStatement.executeUpdate();

        return Optional.of(compteEpargne);
    } catch (SQLException e) {
        // Gérez l'exception SQLException ici
        e.printStackTrace(); // Vous pouvez imprimer la trace de l'exception pour le débogage
        // Vous pouvez également logger l'exception ou la gérer d'une autre manière
        return Optional.empty(); // En cas d'erreur, retournez un Optional vide
    }
}





    @Override
    public Optional<Integer> supprimer(CompteEpargne compteEpargne) {
        String sql = "DELETE FROM saving_account WHERE number = ?";
        Connection connexion = Connexion.getInstance().getConnection();
        try (PreparedStatement preparedStatement = connexion.prepareStatement(sql)) {
            preparedStatement.setString(1, compteEpargne.getNumero());
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
    public Optional<CompteEpargne> chercher(String var) {
        List<CompteEpargne> employes = afficheList();
        List<CompteEpargne> employeList = employes;
        return employeList.stream()
                .filter(employe -> employe.getNumero().equals(var))
                .findFirst();

    }

    @Override
    public List<CompteEpargne> afficheList() {
        List<CompteEpargne> compteEpargnes = new ArrayList<>();

        try {
            String query = "SELECT * FROM saving_account ";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String accountStatusValue = resultSet.getString("account_status");
                Etat etat = null;
                switch (accountStatusValue) {
                    case "Active":
                        etat = Etat.ACTIVE;
                        break;
                    case "Frozen":
                        etat = Etat.FROZEN;
                        break;
                    case "Closed":
                        etat = Etat.CLOSED;
                        break;
                }
                String number = resultSet.getString("number");
                Double balance = Double.valueOf(resultSet.getString("balance"));
                LocalDate created_at = LocalDate.parse(resultSet.getString("created_at"));
                Double interest_rate = (double) Integer.parseInt(resultSet.getString("interest_rate"));
                String employee_code = resultSet.getString("employee_code");
                String client_code = resultSet.getString("client_code");
                String agence_code = resultSet.getString("agence_code");

                EmployeImp employe = new EmployeImp();
                ClientImp client = new ClientImp();
                AgenceImp agenceImp = new AgenceImp();

                Optional<Employe> emp = employe.chercher(employee_code);
                Optional<Client> cli = client.chercher(client_code);
                compteEpargnes.add(new CompteEpargne(balance, number, created_at, etat, emp.get(), cli.get(), interest_rate,agenceImp.chercher(agence_code).get()));
            }

            return compteEpargnes;
        } catch (SQLException e) {
            // Gérer l'exception SQL ici
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            // Gérer d'autres exceptions ici
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Optional<CompteEpargne> changerStatus(CompteEpargne compteEpargne, Etat etat) throws Exception {
        String sql ="UPDATE saving_account SET account_status = ?::accountstatus WHERE number = ?";
        Connection connexion = Connexion.getInstance().getConnection();
        try (PreparedStatement preparedStatement = connexion.prepareStatement(sql)) {
            preparedStatement.setString(1, etat.toString());
            preparedStatement.setString(2, compteEpargne.getNumero());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                return Optional.empty();
            } else {
                return Optional.ofNullable(compteEpargne);
            }
        }
    }

    @Override
    public Optional<List<Compte>> chercherParClient(Optional<Client> client) throws Exception {
        return Optional.empty();
    }



@Override
    public Optional<CompteEpargne> retrait(CompteEpargne compteEpargne, Employe employe, Double montant, TypeOperation typeOperation) throws Exception {
        if (montant < 0) {
            throw new IllegalArgumentException("Le montant du versement doit être positif");
        }
        if (compteEpargne.getSolde() < montant) {
            throw new IllegalArgumentException("Fonds insuffisants pour effectuer le retrait");
        }
        try {
            String sql = "UPDATE saving_account SET balance = balance - ? WHERE number = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, montant);
            preparedStatement.setString(2, compteEpargne.getNumero()); // Remplacez par le nom réel de la colonne d'identification
            int rowsAffected = preparedStatement.executeUpdate();

            // Si la mise à jour a réussi, créez une opération
            if (rowsAffected > 0) {
                OperationImp operationImp = new OperationImp();
                Operation operation = new Operation();
                operation.setComptes(compteEpargne);
                operation.setEmployes(employe);
                operation.setMontant(montant);
                operation.setType(typeOperation);
                operationImp.ajouter(operation);
            } else {
                throw new SQLException("Échec de la mise à jour du solde du compte courant");
            }
        } catch ( SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la mise à jour du solde du compte courant", e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(compteEpargne);
    }

    public  Optional<CompteEpargne> versement(CompteEpargne compteEpargne, Employe employe , Double montant, TypeOperation typeOperation){
        if (montant < 0) {
            throw new IllegalArgumentException("Le montant du versement doit être positif");
        }
        try {
            String sql = "UPDATE saving_account SET balance = balance + ? WHERE number = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, montant);
            preparedStatement.setString(2, compteEpargne.getNumero()); // Remplacez par le nom réel de la colonne d'identification
            int rowsAffected = preparedStatement.executeUpdate();

            // Si la mise à jour a réussi, créez une opération
            if (rowsAffected > 0) {
                OperationImp operationImp = new OperationImp();
                Operation operation = new Operation();
                operation.setComptes(compteEpargne);
                operation.setEmployes(employe);
                operation.setMontant(montant);
                operation.setType(typeOperation);
                operationImp.ajouter(operation);
            } else {
                throw new SQLException("Échec de la mise à jour du solde du compte courant");
            }
        } catch ( SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la mise à jour du solde du compte courant", e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(compteEpargne);
    }





}
