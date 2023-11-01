package testing;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/*
 * Generates random String pairs in file.
 * String pairs are same length but their content is different
 * (This class helps to test the hash function)
 */
public class StringGenerator {

	private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	public static void main(String[] args) {
		
		// Change all these variables as you need
        String fileName = "didelisATS.txt";
        int numRows = 25000;
        int stringLength = 1000;

        generateStringPairs(fileName, numRows, stringLength);
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

    private static void generateStringPairs(String fileName, int numRows, int stringLength) {
    	
        try (FileWriter writer = new FileWriter(fileName, true)) {
        	
            for (int i = 0; i < numRows; i++) {
            	
                String firstString = generateRandomString(stringLength);
                String secondString = generateRandomString(stringLength);
                String stringPair = firstString + "," + secondString + "\n";
                writer.write(stringPair);
                
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}