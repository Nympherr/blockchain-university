import java.math.BigInteger;
import java.util.Scanner;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

	public static void main(String[] args) {
		
		int userChoice = programStart();
		String userMessage;
		
		if(userChoice == 1) {
			userMessage = getUserInput();
		}
		else {
			userMessage = getFileContent();
		}
		
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
		
		if(binaryString.charAt(index) == '1') {
			binaryString.setCharAt(index,'0');
		}
		else {
			binaryString.setCharAt(index,'1');
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
    
    public static int programStart() {
    	
    	Scanner scanner = new Scanner(System.in);
    	
    	System.out.println("How would you like to generate a hash:");
    	System.out.println("1. Write a message");
    	System.out.println("2. Read from a file");
    	
    	do {
    		
        	System.out.print("Your answer: ");
        	int userChoice = scanner.nextInt();
        	
        	if(userChoice == 1 || userChoice == 2 ) {
        		return userChoice;
        	}
        	else {
        		System.out.println("\nYou can only select 1 or 2!");
        	}
    		
    	}while(true);
    }
    
    public static String iterateUserInput(String message) {
    	
		StringBuilder binaryString = createEmptyBinaryString();
		
		for(int i = 0; i < message.length(); i++) {
			char currentLetter = message.charAt(i);
			
			binaryString = changeCharacterAtIndex(binaryString, currentLetter);
		}
		
		return binaryString.toString();
    }

}
