package ConnexionBaseDonnes;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import env.env;

public class Connexion {
    private static Connexion instance;

    private Connection connection;

    private Map<String, String> getEnv = env.vars();
    private final String url = getEnv.get("DB_URL");
    private final String user = getEnv.get("DB_USERNAME");
    private final String password = getEnv.get("DB_PASSWORD");

    private Connexion() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("la base de donne est connecter");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("base de donne non connecter");
        }
    }

    public static synchronized Connexion getInstance() {
        if (instance == null) {
            instance = new Connexion();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public boolean isConnectionValid() {
        try {
            return connection.isValid(5);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
