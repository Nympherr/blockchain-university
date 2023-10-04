import java.math.BigInteger;
import java.util.Scanner;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.regex.Pattern;

public class HashFunc {

	// Program start
	public static void main(String[] args) {
		
	    String inputMessage = programStart(args);
	    inputMessage = checkSymbols(inputMessage);
	    inputMessage = shuffleInput(inputMessage);
	    inputMessage = addSalt(inputMessage);
	    
	    String hashedMessage = hashingFunction(inputMessage);
	    String hash = convertBinaryToHex(hashedMessage);

	    System.out.println("\nHash: " + hash);

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
	
	// Reads file's contents and puts it into string
    private static String readFile(String filePath) throws IOException {
    	
        byte[] encodedBytes = Files.readAllBytes(Paths.get(filePath));
        return new String(encodedBytes, StandardCharsets.UTF_8);
    }
    
    /*
     * Function which is responsible for the start of the program
     * It allows to give arguments in command line and execute the
     * code immediately: F.E. write "java program_name 1 "hello world"
     * 
     * Also acts accordingly if we start from IDE and do not give any
     * arguments
     */
    private static String programStart(String[] args) {
    	
        String userMessage = "";
        
        // If in command line we did not provide 2 arguments
    	if (args.length != 2) {
    		
	        System.out.println("How would you like to hash the message:");
	        System.out.println("1. Write a message");
	        System.out.println("2. Read from a file");

	        Scanner scanner = new Scanner(System.in);
	        System.out.print("Your Answer: ");
	        int userChoice = scanner.nextInt();
	        
	        // 1 - user writes it's own message which he wants to hash
	        if (userChoice == 1) {
	        	
	            scanner.nextLine();
	            System.out.print("Enter message: ");
	            userMessage = scanner.nextLine();
	            
	        } 
	        
	        // 2 - reading file and hashing it
	        else if (userChoice == 2) {
	        	
	            scanner.nextLine();
	            System.out.print("Enter file name: ");
	            String fileName = scanner.nextLine();
	            
	            try {
	                userMessage = readFile(fileName);
	            }
	            catch (IOException e) {
	                System.err.println("Invalid file name or file not found. Please try again.");
	                System.exit(1);
	            }
	        }
	        else {
	        	
	            System.out.println("Invalid answer. Write 1 for message and 2 for file");
	            System.exit(1);
	            
	        }
    	}
    	
    	// This block is executed when we provide 2 arguments in command line
    	// F.E. "java program_name 1 "hello"
    	else {
    		
            int userChoice = Integer.parseInt(args[0]);

            if (userChoice == 1) {
            	
                userMessage = args[1];
                
            }
            else if (userChoice == 2) {
            	
                try {
                    userMessage = readFile(args[1]);
                }
                catch (IOException e) {
                    System.err.println("Invalid file name or file not found. Please try again.");
                    System.exit(1);
                }
            }
            else {
            	
                System.out.println("Invalid answer. Write 1 for message and 2 for file");
                System.exit(1);
                
            }
    	}
    	return userMessage;
    }
    
    /*
     * 1) Creates empty 256-bit size binary string
     * 2) Iterates trough every symbol of the input string
     * 3) With every symbol calculations are made and
     * original 256-bit binary string is modified
     */
    private static String hashingFunction(String message) {
    	
		StringBuilder binaryString = createEmptyBinaryString();
		
		long startTime = System.currentTimeMillis();
		
		for(int i = 0; i < message.length(); i++) {
			
			char currentLetter = message.charAt(i);
			
			binaryString = shuffleStringValues(binaryString, currentLetter);
		}
		
		long endTime = System.currentTimeMillis();
		long executionTime = endTime - startTime;
		System.out.println("\nExecution time: " + executionTime + " milliseconds");
		
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
    	
    	int asciiResult = 0;
    	StringBuilder modifiedInput = new StringBuilder(input);
    	
    	for(int i = 0; i < input.length(); i++) {
    		asciiResult = asciiResult + input.charAt(i);
    	}
    	
    	asciiResult = asciiResult % 256;
    	
		if (asciiResult >= input.length()) {
			asciiResult = asciiResult % input.length();
		}
		
		String removedText = modifiedInput.substring(modifiedInput.length() - asciiResult);
		modifiedInput.delete(removedText.length() - asciiResult, removedText.length());
		modifiedInput.insert(0, removedText);
		
		return modifiedInput.toString();    
	}
}
