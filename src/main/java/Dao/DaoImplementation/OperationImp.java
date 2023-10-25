package Dao.DaoImplementation;

import ConnexionBaseDonnes.Connexion;
import Dao.OperationDao;
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

public class OperationImp implements OperationDao {

    public Optional<Operation> ajouter(Operation operation) {
        Connection connexion = Connexion.getInstance().getConnection();
        try {
            String sql = "INSERT INTO operation (number, created_at, price, payment, employee_code, account_number) VALUES (?, ?, ?, CAST(? AS paymenttype), ?, ?)";
            String operationType = (operation.getType().equals(TypeOperation.Withdrawal)) ? "Withdrawal" : (operation.getType().equals(TypeOperation.Deposit)) ? "Deposit" : "Autre";

            PreparedStatement preparedStatement = connexion.prepareStatement(sql);
            preparedStatement.setString(1, genererCodeUnique(6));
            operation.setDateCreation(LocalDate.now());
            preparedStatement.setDate(2, java.sql.Date.valueOf(operation.getDateCreation()));
            preparedStatement.setDouble(3, operation.getMontant());
            preparedStatement.setString(4, operationType);
            preparedStatement.setString(5, operation.getEmployes().getMatricule());
            preparedStatement.setString(6, operation.getComptes().getNumero());
            preparedStatement.executeUpdate();

            return Optional.ofNullable(operation);
        } catch (SQLException e) {
            // Gérez l'exception SQLException ici
            e.printStackTrace(); // Vous pouvez imprimer la trace de l'exception pour le débogage
            // Vous pouvez également logger l'exception ou la gérer d'une autre manière
            return Optional.empty(); // En cas d'erreur, retournez un Optional vide
        }
    }



    @Override
    public Optional<Integer> supprimer(Operation operation) {
        String sql = "DELETE FROM operation WHERE number = ?";
        Connection connexion = Connexion.getInstance().getConnection();
        try (PreparedStatement preparedStatement = connexion.prepareStatement(sql)) {
            preparedStatement.setString(1, operation.getNumero());
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
    public Optional<Operation> chercher(String var)  {
       List<Operation> operations = afficheList();
        List<Operation> operationList = operations;
        return operationList.stream()
                .filter(operation -> operation.getNumero().equals(var))
                .findFirst();
    }

    @Override
    public List<Operation> afficheList()  {
        List<Operation> operations = new ArrayList<>();
        EmployeImp employes = new EmployeImp();
        CompteCourantImp compteCourantImp = new CompteCourantImp();

        try (Connection connection = Connexion.getInstance().getConnection()) {
            String query = "SELECT * FROM operation";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String number = resultSet.getString("number");
                LocalDate createdAt = resultSet.getDate("created_at").toLocalDate();
                Double price = (double) resultSet.getInt("price");
                String employeeCode = resultSet.getString("employee_code");
                String accountNumber = resultSet.getString("account_number");
                TypeOperation type = TypeOperation.valueOf(resultSet.getString("payment"));

                Employe employe = employes.chercher(employeeCode).orElse(null);
                CompteCourant compteCourant = compteCourantImp.chercher(accountNumber).orElse(null);

                Operation operation = new Operation(employe, compteCourant, number, createdAt, price,type);
                operations.add(operation);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return operations;
    }

    public Optional<Employe> employeExiste(String matricule) throws Exception {
        String sql = "SELECT * FROM employee WHERE code = ?";
        Connection connection = Connexion.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, matricule);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String code = resultSet.getString("code");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                java.sql.Date birthDateSql = resultSet.getDate("birth_date");
                LocalDate birthDate = birthDateSql.toLocalDate();
                String phoneNumber = resultSet.getString("phone_number");
                String email = resultSet.getString("email");
                java.sql.Date recruitedAtSql = resultSet.getDate("recruited_at");
                LocalDate recruitedAt = recruitedAtSql.toLocalDate();
                Employe employe = new Employe(code, firstName, lastName, birthDate, phoneNumber, email, recruitedAt);
                return Optional.of(employe);
            } else {
                return Optional.empty();
            }
    }


    public static String genererCodeUnique(int longueur) {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < longueur; i++) {
            int chiffre = random.nextInt(10);
            code.append(chiffre);
        }
        return code.toString();
    }





}
