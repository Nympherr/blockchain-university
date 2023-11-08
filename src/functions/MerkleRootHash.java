package functions;

import java.util.ArrayList;
import java.util.List;

import models.Transaction;

public class MerkleRootHash {

	 public static String createMerkleRootHash(ArrayList<Transaction> transactionList) {
	        List<String> treeLayer = new ArrayList<>();

	        // Step 1: Hash individual transactions
	        for (Transaction transaction : transactionList) {
	            treeLayer.add(transaction.getTransactionID()); // Assuming getTransactionID() returns the hashed transaction ID
	        }

	        // Step 2 and 3: Combine and hash pairs until one hash remains
	        while (treeLayer.size() > 1) {
	            List<String> newTreeLayer = new ArrayList<>();

	            for (int i = 0; i < treeLayer.size() - 1; i += 2) {
	                newTreeLayer.add(combineAndHash(treeLayer.get(i), treeLayer.get(i + 1)));
	            }

	            // Handle odd number of elements
	            if (treeLayer.size() % 2 != 0) {
	                newTreeLayer.add(treeLayer.get(treeLayer.size() - 1));
	            }

	            treeLayer = newTreeLayer;
	        }

	        // Step 4: The last remaining hash is the Merkle root
	        String answer = treeLayer.size() == 1 ? treeLayer.get(0) : "";
	        return hash(answer);
	    }
	
    private static String hash(String data) {
        return NewHash.sha256(data);
    }
    
    private static String combineAndHash(String hash1, String hash2) {
        return hash(hash1 + hash2);
    }
}
