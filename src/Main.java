import java.math.BigInteger;

public class Main {

	public static void main(String[] args) {
		
		StringBuilder binaryString = createEmptyBinaryString();
		binaryString = changeCharacterAtIndex(binaryString,'A');
		
		String binaryHex = convertBinaryToHex(binaryString.toString());
		
		System.out.println("binary: " + binaryString);
		System.out.println("hex: " + binaryHex);
	}
	
	public static StringBuilder createEmptyBinaryString() {
		
		int bitLength = 256;
		StringBuilder emptyBinaryString = new StringBuilder(bitLength);
		
		for(int i = 0; i < bitLength; i++) {
			emptyBinaryString.append("1");
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
		
		return binaryString;

	}
	
	public static int getAsciiValue(char symbol) {
		
		return (int) symbol;
		
	}

}
