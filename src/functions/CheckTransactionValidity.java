package functions;

import java.util.ArrayList;

import models.Transaction;

public class CheckTransactionValidity {

	public static void checkValidity(ArrayList<Transaction> transactionPool) {
		
		System.out.println("-------------------------------------------------------------------------------------");
    	System.out.println("==========================================");
    	System.out.println("||                                      ||");
    	System.out.println("||       TRANSAKCIJŲ VALIDAVIMAS        ||");
    	System.out.println("||                                      ||");
    	System.out.println("==========================================");
		System.out.println("\nPradinis transakcijų dydis pool'e: " + transactionPool.size());
		
		int removedTransactions = 0;
		int validTransactions = 0;
		
		for(Transaction transaction : transactionPool) {
			
			String transactionSender = transaction.getSender();
			String transactionReceiver = transaction.getReceiver();
			int transactionAmount = transaction.getAmount();
			String transactionTime = transaction.getCreationTime();
			
			String transactionHash = HashFunc.generateHash(transactionSender + transactionReceiver + transactionAmount + transactionTime);
			
			if(transactionHash.equals(transaction.getTransactionID())) {
				
//				System.out.println("Originalus hash: " + transaction.getTransactionID());
//				System.out.println("Mūsų tikrinamas hash: " + transactionHash);
//				System.out.println();
				validTransactions++;
			}
			else {
//				System.out.println("Originalus hash: " + transaction.getTransactionID());
//				System.out.println("Mūsų tikrinamas hash: " + transactionHash);
//				System.out.println();
				transactionPool.remove(transaction);
				removedTransactions--;
			}
		}
		
		System.out.println("Blogos/ištrintos transakcijos: " + removedTransactions);
		System.out.println("Geros likusios transakcijos: " + validTransactions);
		System.out.println();

	}
}
