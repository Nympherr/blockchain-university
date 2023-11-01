package functions;

import java.util.Random;

public class GeneratePublicKey {

    public static String generateRandomPublicKey() {
        String alphanumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder publicKey = new StringBuilder();
        Random rand = new Random();

        for (int i = 0; i < 64; i++) {
            int randomIndex = rand.nextInt(alphanumeric.length());
            publicKey.append(alphanumeric.charAt(randomIndex));
        }

        return publicKey.toString();
    }

}
