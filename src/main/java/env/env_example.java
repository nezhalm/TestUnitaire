package env;

import java.util.HashMap;
import java.util.Map;

// Make sure to rename this class and file to "env".
public class env_example {
    public static Map<String, String> vars() {
        Map<String, String> envMap = new HashMap<>();

        // Your database url, example: jdbc:postgresql://localhost:1234/database
        envMap.put("DB_URL", "Your database url");

        // Your database's username, example: root
        envMap.put("DB_USERNAME", "username");

        // Your database's password
        envMap.put("DB_PASSWORD", "****");

        return envMap;
    }
}
