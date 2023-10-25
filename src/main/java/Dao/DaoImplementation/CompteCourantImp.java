package Dao.DaoImplementation;

import ConnexionBaseDonnes.Connexion;
import Dao.CompteCourantDao;
import Entities.*;
import Enum.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class CompteCourantImp implements CompteCourantDao {
    private static final Connection connection = Connexion.getInstance().getConnection();


    @Override
    public Optional<CompteCourant> ajouter(CompteCourant compte) {
        try {
            String sql = "INSERT INTO current_account (number, balance, created_at, overdraft, employee_code, client_code,agency_code) VALUES (?, ?, ?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, compte.getNumero());
            preparedStatement.setDouble(2, compte.getSolde());
            preparedStatement.setDate(3, java.sql.Date.valueOf(compte.getDateCreation()));
            preparedStatement.setDouble(4, compte.getDecouvert());
            preparedStatement.setString(5, compte.getEmploye().getMatricule());
            preparedStatement.setString(6, compte.getClient().getCode());
            preparedStatement.setString(7, compte.getAgence().getCode());
            preparedStatement.executeUpdate();

            return Optional.of(compte);
        } catch (SQLException e) {
            e.printStackTrace(); // Vous pouvez imprimer la trace de l'exception pour le débogage
            return Optional.empty(); // En cas d'erreur, retournez un Optional vide
        }
    }


    @Override
    public Optional<Integer> supprimer(CompteCourant compteCourant) {
        String sql = "DELETE FROM current_account WHERE number = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, compteCourant.getNumero());
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


    public Optional<CompteCourant> changerStatus(CompteCourant compteCourant, Etat etat) throws Exception {
        String sql = "UPDATE current_account SET account_status = ?::accountstatus WHERE number = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, etat.toString());
            preparedStatement.setString(2, compteCourant.getNumero());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                return Optional.empty();
            } else {
                return Optional.ofNullable(compteCourant);
            }
        }
    }


    @Override
    public Optional<CompteCourant> chercher(String var) {
        List<CompteCourant> employes = afficheList();
        List<CompteCourant> employeList = employes;
        return employeList.stream()
                .filter(employe -> employe.getNumero().equals(var))
                .findFirst();


    }

    public Optional<List<Compte>> chercherParClient(Optional<Client> client) throws Exception {
        String query = "SELECT 'saving' as account_type, number, balance FROM saving_account WHERE client_code = ? " +
                "UNION SELECT 'current' as account_type, number, balance FROM current_account WHERE client_code = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, client.get().getCode());
        preparedStatement.setString(2, client.get().getCode());

        ResultSet resultSet = preparedStatement.executeQuery();

        List<Compte> comptes = new ArrayList<>();
        while (resultSet.next()) {
            String accountType = resultSet.getString("account_type");
            String number = resultSet.getString("number");
            Double balance = Double.valueOf(resultSet.getString("balance"));
            if ("saving".equals(accountType)) {
                CompteEpargne compteEpargne = new CompteEpargne(number, balance, accountType);
                comptes.add(compteEpargne);
            } else if ("current".equals(accountType)) {
                CompteCourant compteCourant = new CompteCourant(number, balance, accountType);
                comptes.add(compteCourant);
            }
        }
        return comptes.isEmpty() ? Optional.empty() : Optional.of(comptes);
    }


    public List<CompteCourant> afficheList() {
        List<CompteCourant> compteCourants = new ArrayList<>();
        try {
            String query = "SELECT * FROM current_account ";
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
                Double overdraft = (double) Integer.parseInt(resultSet.getString("overdraft"));
                String employee_code = resultSet.getString("employee_code");
                String client_code = resultSet.getString("client_code");
                String agence_code = resultSet.getString("agence_code");
                EmployeImp employe = new EmployeImp();
                ClientImp client = new ClientImp();
                AgenceImp agence = new AgenceImp();
                Optional<Employe> emp = employe.chercher(employee_code);
                Optional<Client> cli = client.chercher(client_code);
                compteCourants.add(new CompteCourant(balance, number, created_at, etat, emp.get(), cli.get(), overdraft, agence.chercher(agence_code).get()));
            }
            return compteCourants;
        } catch (SQLException e) {
            e.printStackTrace();
            return compteCourants;
        } catch (Exception e) {
            e.printStackTrace();
            return compteCourants;
        }
    }

    public Optional<CompteCourant> retrait(CompteCourant compteCourant, Employe employe, Double montant, TypeOperation typeOperation) {
        if (montant < 0) {
            System.out.println("Le montant du versement doit être positif");
            System.exit(0);
        }
        if (compteCourant.getSolde() < montant) {
            System.out.println("Fonds insuffisants pour effectuer le retrait");
            System.exit(0);
        }
        try {
            String sql = "UPDATE current_account SET balance = balance - ? WHERE number = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, montant);
            preparedStatement.setString(2, compteCourant.getNumero()); // Remplacez par le nom réel de la colonne d'identification
            int rowsAffected = preparedStatement.executeUpdate();

            // Si la mise à jour a réussi, créez une opération
            if (rowsAffected > 0) {
                OperationImp operationImp = new OperationImp();
                Operation operation = new Operation();
                operation.setComptes(compteCourant);
                operation.setEmployes(employe);
                operation.setMontant(montant);
                operation.setType(typeOperation);
                operationImp.ajouter(operation);
            } else {
                throw new SQLException("Échec de la mise à jour du solde du compte courant");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la mise à jour du solde du compte courant", e);
        } catch (Exception e) {

        }
        return Optional.ofNullable(compteCourant);
    }

    @Override
    public Optional<CompteCourant> versement(CompteCourant compteCourant, Employe employe, Double montant, TypeOperation typeOperation) {
        if (montant < 0) {
            throw new IllegalArgumentException("Le montant du versement doit être positif");
        }
        try {
            String sql = "UPDATE current_account SET balance = balance + ? WHERE number = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, montant);
            preparedStatement.setString(2, compteCourant.getNumero()); // Remplacez par le nom réel de la colonne d'identification
            int rowsAffected = preparedStatement.executeUpdate();

            // Si la mise à jour a réussi, créez une opération
            if (rowsAffected > 0) {
                OperationImp operationImp = new OperationImp();
                Operation operation = new Operation();
                operation.setComptes(compteCourant);
                operation.setEmployes(employe);
                operation.setMontant(montant);
                operation.setType(typeOperation);
                operationImp.ajouter(operation);
            } else {
                throw new SQLException("Échec de la mise à jour du solde du compte courant");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la mise à jour du solde du compte courant", e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(compteCourant);
    }
}
