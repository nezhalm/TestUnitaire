package Utils;

import java.util.Random;

public class ExtraMethods {
    public static String generateUniqueCode(int longueur) {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < longueur; i++) {
            int num = random.nextInt(10);
            code.append(num);
        }
        return code.toString();
    }
}
