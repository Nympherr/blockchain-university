import java.math.BigInteger;
import java.util.Scanner;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

	public static void main(String[] args) {
		
	    String userMessage = programStart(args);

	    userMessage = addSalt(userMessage) + userMessage;
	    String modifiedUserInput = iterateUserInput(userMessage);
	    String hash = convertBinaryToHex(modifiedUserInput);

	    System.out.println("Binary: " + modifiedUserInput);
	    System.out.println("Hex: " + hash);
	}

	
	public static StringBuilder createEmptyBinaryString() {
		
		int bitLength = 256;
		StringBuilder emptyBinaryString = new StringBuilder(bitLength);
		
		for(int i = 0; i < bitLength; i++) {
			emptyBinaryString.append("0");
		}
		
		return emptyBinaryString;
		
	}
	
	public static String convertBinaryToHex(String binaryString) {
		
		BigInteger binaryDecimal = new BigInteger(binaryString, 2);
		return binaryDecimal.toString(16);
	}
	
	public static StringBuilder changeCharacterAtIndex(StringBuilder binaryString,char symbol) {
		
		int index = getAsciiValue(symbol) - 1;
		
		for(int i = index; i > 0; i = i - 3) {

		
			if(binaryString.charAt(i) == '1') {
				binaryString.setCharAt(i,'0');
			}
			else {
				binaryString.setCharAt(i,'1');
			}
			
		}
		
		binaryString = shiftBinaryStringRight(binaryString, index + 1);
		
		return binaryString;

	}
	
	public static int getAsciiValue(char symbol) {
		
		return (int) symbol;
		
	}
	
	public static StringBuilder shiftBinaryStringRight(StringBuilder binaryString, int shiftAmount) {

	    String removedBits = binaryString.substring(256 - shiftAmount);

	    binaryString.delete(256 - shiftAmount, 256);

	    binaryString.insert(0, removedBits);

	    return binaryString;
	}
	
	public static String getUserInput() {
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("Write a string you wish to hash: ");
		String userInput = scanner.nextLine();
		
		scanner.close();
		
		return userInput;
		
	}

	public static String getFileContent() {
		
		Scanner scanner = new Scanner(System.in);

        boolean validFile = false;
        String fileName = "";
        String fileContents = "";

        while (!validFile) {
            System.out.print("Enter the file name: ");

            fileName = scanner.nextLine();

            try {
                fileContents = readFileToString(fileName);
                System.out.println("File Contents:\n" + fileContents);
                validFile = true;
            } catch (IOException e) {
                System.err.println("Invalid file name or file not found. Please try again.");
            }
        }

        scanner.close();
        return fileContents;
    }
	
    public static String readFileToString(String filePath) throws IOException {
        byte[] encodedBytes = Files.readAllBytes(Paths.get(filePath));
        return new String(encodedBytes, StandardCharsets.UTF_8);
    }
    
    public static String programStart(String[] args) {
    	
        String userMessage = "";
        
    	if (args.length != 2) {
	        System.out.println("How would you like to hash the message:");
	        System.out.println("1. Write a message");
	        System.out.println("2. Read from a file");

	        Scanner scanner = new Scanner(System.in);
	        System.out.print("Your Answer: ");
	        int userChoice = scanner.nextInt();
	        
	        if (userChoice == 1) {
	            scanner.nextLine();
	            System.out.print("Enter message: ");
	            userMessage = scanner.nextLine();
	        } 
	        else if (userChoice == 2) {
	            scanner.nextLine();
	            System.out.print("Enter file name: ");
	            String fileName = scanner.nextLine();
	            try {
	                userMessage = readFileToString(fileName);
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
    	else {
    		
            int userChoice = Integer.parseInt(args[0]);

            if (userChoice == 1) {
                userMessage = args[1];
            }
            else if (userChoice == 2) {
            	
                try {
                    userMessage = readFileToString(args[1]);
                }
                catch (IOException e) {
                    System.err.println("Invalid file name or file not found. Please try again.");
                    System.exit(1);
                }
            }
            else {
                System.out.println("Invalid mode. Use <mode>: 1 for message, 2 for file");
                System.exit(1);
            }
    	}
    	return userMessage;
   }
    
    public static String iterateUserInput(String message) {
    	
		StringBuilder binaryString = createEmptyBinaryString();
		
		for(int i = 0; i < message.length(); i++) {
			char currentLetter = message.charAt(i);
			
			binaryString = changeCharacterAtIndex(binaryString, currentLetter);
		}
		
		return binaryString.toString();
    }
    
    public static String addSalt(String userMessage) {
    	
    	StringBuilder salt = new StringBuilder();
    	
    	for(int i = 0; i < userMessage.length() && i < 10; i++) {
    		
    		char symbol = userMessage.charAt(i);
    		int value = (int) symbol;
    		String temporaryValue = Integer.toString(value);
    		
    		salt.append("X" + temporaryValue);
    		
    			for(int j = 0; j < temporaryValue.length(); j++) {
    				
    				char nestedSymbol = temporaryValue.charAt(j);
    				int nestedValue = (int) nestedSymbol;
    				String temporaryNestedValue = Integer.toString(nestedValue);
    				
    	    		salt.append(temporaryNestedValue);
    	    		
    	    		for(int x = 0; x < temporaryNestedValue.length(); x++) {
    	    			
        				char nestedSymbol2 = temporaryNestedValue.charAt(x);
        				int nestedValue2 = (int) nestedSymbol2;
        				String temporaryNestedValue2 = Integer.toString(nestedValue2);
        				
        	    		salt.append(temporaryNestedValue2);
    	    		}
    			}
    			
    			
    	}
    	System.out.println(salt.toString());
    	return salt.toString();
    }

}
