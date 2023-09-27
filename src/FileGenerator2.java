import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/*
 * Generates random String pairs in file.
 * String pairs are same, only one letter is different
 * between them
 */
public class FileGenerator2 {

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static void main(String[] args) {
    	
        String fileName = "stringPairs.txt";
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
            	
                String originalString = generateRandomString(stringLength);
                String modifiedString = generateStringWithSingleDifference(originalString);
                String stringPair = originalString + "," + modifiedString + "\n";
                writer.write(stringPair);
                
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
