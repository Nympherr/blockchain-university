import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/*
 * Same as "StringGenerator" class but only difference
 * is that the string pairs are different just by 1 symbol
 */
public class StringGenerator2 {

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static void main(String[] args) {
    	
		// Change all these variables as you need
        String fileName = "didelisATSSimb.txt";
        int numRows = 25000;
        int stringLength = 1000;

        generateStringPairsWithSingleDifference(fileName, numRows, stringLength);
    }

    private static String generateRandomString(int length) {
    	
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();

        for (int i = 0; i < length; i++) {
        	
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
            
        }

        return sb.toString();
    }

    private static String generateStringWithSingleDifference(String originalString) {
    	
        Random random = new Random();
        int position = random.nextInt(originalString.length());
        char replacementChar = CHARACTERS.charAt(random.nextInt(CHARACTERS.length()));

        StringBuilder modifiedString = new StringBuilder(originalString);
        modifiedString.setCharAt(position, replacementChar);

        return modifiedString.toString();
        
    }

    private static void generateStringPairsWithSingleDifference(String fileName, int numRows, int stringLength) {
    	
        try (FileWriter writer = new FileWriter(fileName, true)) {
            for (int i = 0; i < numRows; i++) {
                String originalString;
                String modifiedString;

                do {
                    originalString = generateRandomString(stringLength);
                    modifiedString = generateStringWithSingleDifference(originalString);
                }
                while (originalString.equals(modifiedString));

                String stringPair = originalString + "," + modifiedString + "\n";
                writer.write(stringPair);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
