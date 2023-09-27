import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.text.Normalizer;
import java.util.regex.Pattern;

/*
 * This class compares hashes in a file
 * File must have string pairs(separated by comma in 1 line)
 * At the end it displays how many percentage of bits are different
 * (shows MIN, MAX, AVG values)
 */
public class CompareHashes2 {

	// Program start
	public static void main(String[] args) {
		
        String fileName = "oneLetter.txt";

        int minPercentageDifference = Integer.MAX_VALUE;
        int maxPercentageDifference = 0;
        int totalPercentageDifference = 0;
        int numRows = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = br.readLine()) != null) {
            	
                String[] words = line.split(",");
                String word1 = words[0].trim();
                String word2 = words[1].trim();

                String hashedWord1 = hashingFunction(addSalt(deAccent(word1)));
                String hashedWord2 = hashingFunction(addSalt(deAccent(word2)));

                int percentageDifference = calculatePercentageDifference(hashedWord1, hashedWord2);

                minPercentageDifference = Math.min(minPercentageDifference, percentageDifference);
                maxPercentageDifference = Math.max(maxPercentageDifference, percentageDifference);

                totalPercentageDifference += percentageDifference;

                numRows++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        double averagePercentageDifference = (double) totalPercentageDifference / numRows;

        System.out.println("Minimum Percentage Difference: " + minPercentageDifference + "%");
        System.out.println("Maximum Percentage Difference: " + maxPercentageDifference + "%");
        System.out.println("Average Percentage Difference: " + averagePercentageDifference + "%");
    }
	
	private static StringBuilder createEmptyBinaryString() {
		
		int bitLength = 256;
		StringBuilder emptyBinaryString = new StringBuilder(bitLength);
		
		for(int i = 0; i < bitLength; i++) {
			emptyBinaryString.append("0");
		}
		
		return emptyBinaryString;
		
	}
	
	/*
	 * Takes a binary string and a current symbol we are working with
	 * (From input message)
	 * It gets symbol's ASCII value and checks what value is at that
	 * index in binary string. And just transforms 1 to 0 and 0 to 1
	 * Then ASCII value is decreased and same process repeated
	 * 
	 * At the end binary string is shifted right according to original
	 * symbol's ASCII value
	 */
	private static StringBuilder shuffleStringValues(StringBuilder binaryString,char symbol) {
		
		int index = getAsciiValue(symbol) - 1;
		
		if (index >= 0 && index < binaryString.length()) {
			for(int i = index; i > 0; i = i - 3) {

				if(binaryString.charAt(i) == '1') {
					binaryString.setCharAt(i,'0');
				}
				else {
					binaryString.setCharAt(i,'1');
				}
			
			}
		}
		
		return shiftBinaryString(binaryString, index + 1);
		
	}
	
	// Get's symbol's ASCII value
	private static int getAsciiValue(char symbol) {
		
		return (int) symbol;
		
	}
	
	
	//Shifts all binary string to right, according to symbol's ASCII value
	private static StringBuilder shiftBinaryString(StringBuilder binaryString, int shiftAmount) {

		if (shiftAmount <= 0 || shiftAmount >= binaryString.length()) {
	        return binaryString;
	    }
		
	    String removedBits = binaryString.substring(256 - shiftAmount);

	    binaryString.delete(256 - shiftAmount, 256);

	    binaryString.insert(0, removedBits);

	    return binaryString;
	}
    
    /*
     * 1) Creates empty 256-bit size binary string
     * 2) Iterates trough every symbol of the input string
     * 3) With every symbol calculations are made and
     * original 256-bit binary string is modified
     */
    private static String hashingFunction(String message) {
    	
		StringBuilder binaryString = createEmptyBinaryString();
		
		for(int i = 0; i < message.length(); i++) {
			
			char currentLetter = message.charAt(i);
			
			binaryString = shuffleStringValues(binaryString, currentLetter);
		}
		
		return binaryString.toString();
		
    }
    
    /*
     * Add's salt to the input before hashing function is started
     * Salt function works as follows:
     * We take symbol's ASCII value, put it to salt, then we iterate trough
     * the ASCII value and get it's own ASCII value, append it to salt and
     * iterate once again with the same process (3 inner loops)
     */
    private static String addSalt(String userMessage) {
    	
    	StringBuilder salt = new StringBuilder();
    	
    	if(userMessage.isEmpty()) {
    		userMessage = "EMPTY";
    	}
    	
    	for(int i = 0; i < userMessage.length() && i < 10; i++) {
    		
    		char symbol = userMessage.charAt(i);
    		int asciiValue = (int) symbol;
    		
    		String firstSalt = Integer.toString(asciiValue);
    		salt.append("X" + firstSalt);
    		
    		for(int j = 0; j < firstSalt.length(); j++) {
    				
    			char symbol2 = firstSalt.charAt(j);
    			int asciiValue2 = (int) symbol2;
    				
    			String secondSalt = Integer.toString(asciiValue2);
    	    	salt.append(secondSalt);
    	    		
    	    	for(int x = 0; x < secondSalt.length(); x++) {
    	    			
        			char symbol3 = secondSalt.charAt(x);
        			int asciiValue3 = (int) symbol3;
        				
        			String thirdSalt = Integer.toString(asciiValue3);
        	    	salt.append(thirdSalt);
        	    		
    	    	}
    	    		
    		}
    			
    	}
    	
    	return salt.toString();
    	
    }
    
    /*
     * Converts non ASCII value's to ASCII.
     * For example it will return ė as e or ą as a.
     */
    private static String deAccent(String str) {
    	
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD); 
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
        
    }

    private static int calculatePercentageDifference(String binary1, String binary2) {
        int totalBits = binary1.length();
        int differentBits = 0;
        for (int i = 0; i < totalBits; i++) {
            if (binary1.charAt(i) != binary2.charAt(i)) {
                differentBits++;
            }
        }
        return (int) ((differentBits / (double) totalBits) * 100);
    }
}
