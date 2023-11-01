package functions;
import java.math.BigInteger;
import java.text.Normalizer;
import java.util.regex.Pattern;

public class HashFunc {

	 public static String generateHash(String inputMessage) {

	        inputMessage = checkSymbols(inputMessage);
	        inputMessage = shuffleInput(inputMessage);
	        inputMessage = addSalt(inputMessage);

	        String hashedMessage = hashingFunction(inputMessage);

	        String hash = convertBinaryToHex(hashedMessage);

	        return hash;
	    }
	
	// Creates 256bit empty binary string(all values are 0's) 
	private static StringBuilder createEmptyBinaryString() {
		
		int bitLength = 256;
		StringBuilder emptyBinaryString = new StringBuilder(bitLength);
		
		for(int i = 0; i < bitLength; i++) {
			emptyBinaryString.append("0");
		}
		
		return emptyBinaryString;
	}
	
	// Takes binary value and converts it to hexadecimal
	private static String convertBinaryToHex(String binaryString) {
		
		BigInteger binaryDecimal = new BigInteger(binaryString, 2);
		return binaryDecimal.toString(16);
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
    	
    	// "X" -> 50 =50
    	// 5 -> 60
    		
    	// 0 -> 55
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
    private static String checkSymbols(String str) {
    	
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD); 
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }
    
    /*
     * Shuffles the original input String before adding salt
     * Sums up ASCII values of all characters, then divides
     * that result and shuffles all string to the right
     */
    private static String shuffleInput(String input) {
    	
    	if(input.isEmpty()) {
    		return "";
    	}
    	

    	int asciiResult = 0;
    	StringBuilder modifiedInput = new StringBuilder(input);
    	
    	for(int i = 0; i < input.length(); i++) {
    		asciiResult = asciiResult + input.charAt(i);
    	}
    	
    	asciiResult = asciiResult % 256;
    	
		if (asciiResult >= input.length()) {
			asciiResult = asciiResult % input.length();
		}
		
		if (asciiResult <= 0 || asciiResult >= input.length()) {
	        return input;
	    }
		
		String removedText = modifiedInput.substring(modifiedInput.length() - asciiResult);
		modifiedInput.delete(removedText.length() - asciiResult, removedText.length());
		modifiedInput.insert(0, removedText);
		
		return modifiedInput.toString();    
	}
}
