import java.math.BigInteger;

public class Main {

	public static void main(String[] args) {
		
		String binaryString = createEmptyBinaryString();
		String binaryHex = convertBinaryToHex(binaryString);
		
		System.out.println("binary: " + binaryString);
		System.out.println("hex: " + binaryHex);
	}
	
	public static String createEmptyBinaryString() {
		
		int bitLength = 256;
		StringBuilder emptyBinaryString = new StringBuilder(bitLength);
		
		for(int i = 0; i < bitLength; i++) {
			emptyBinaryString.append("1");
		}
		
		return emptyBinaryString.toString();
		
	}
	
	public static String convertBinaryToHex(String binaryString) {
		
		BigInteger binaryDecimal = new BigInteger(binaryString, 2);
		return binaryDecimal.toString(16);
	}

}
