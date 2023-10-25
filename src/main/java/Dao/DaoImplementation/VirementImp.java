package Dao.DaoImplementation;
import Dao.VirementDao;
import Entities.*;
import ConnexionBaseDonnes.Connexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import Enum.*;

public class VirementImp implements VirementDao {
    private static final Connection connection = Connexion.getInstance().getConnection();


    @Override
    public Optional<Virement> ajouter(Virement virement) {
        try {
            String sql = "INSERT INTO payment (number, createdat, price, sourceaccountnumber, destinationaccountnumber) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, genererCodeUnique(4));
            LocalDateTime dateEtHeureCreation = LocalDateTime.now();
            // Convertissez la date et l'heure actuelles en java.sql.Timestamp
            Timestamp timestamp = Timestamp.valueOf(dateEtHeureCreation);
            preparedStatement.setTimestamp(2, timestamp);
            preparedStatement.setDouble(3, virement.getMontant());
            preparedStatement.setString(4, virement.getComptes().getNumero());
            preparedStatement.setString(5, virement.getDestinataire().getNumero());
            preparedStatement.executeUpdate();

            return Optional.ofNullable(virement);
        } catch (SQLException e) {
            e.printStackTrace(); // Vous pouvez imprimer la trace de l'exception pour le débogage
            return Optional.empty(); // En cas d'erreur, retournez un Optional vide
        }
    }

//test

    @Override
    public List<Virement> afficheList()  {
        return null;
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

    public  Optional<Compte> retrait(CompteCourant compteCourant, Double montant){
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
        } catch ( SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la mise à jour du solde du compte courant", e);
        } catch (Exception e) {

        }
        return Optional.ofNullable(compteCourant);
    }

    public  Optional<Compte> versement(CompteCourant compteCourant, Double montant){
        if (montant < 0) {
            throw new IllegalArgumentException("Le montant du versement doit être positif");
        }
        try {
            String sql = "UPDATE current_account SET balance = balance + ? WHERE number = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, montant);
            preparedStatement.setString(2, compteCourant.getNumero()); // Remplacez par le nom réel de la colonne d'identification
            preparedStatement.executeUpdate();

        } catch ( SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la mise à jour du solde du compte courant", e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(compteCourant);
    }

    @Override
    public Optional<Integer> supprimer(Virement virement){
        return Optional.empty();
    }

    @Override
    public Optional<Virement> chercher(String var)  {
        return Optional.empty();
    }

}
