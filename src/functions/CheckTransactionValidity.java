package functions;

import java.util.ArrayList;
import java.util.Iterator;

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
		Iterator<Transaction> iterator = transactionPool.iterator();
		
		while (iterator.hasNext()) {
		    Transaction transaction = iterator.next();

		    String transactionSender = transaction.getSender();
		    String transactionReceiver = transaction.getReceiver();
		    int transactionAmount = transaction.getAmount();
		    String transactionTime = transaction.getCreationTime();

		    String transactionHash = NewHash.sha256(transactionSender + transactionReceiver + transactionAmount + transactionTime);

		    if(transactionHash.equals(transaction.getTransactionID())) {
		        validTransactions++;
		    } else {
		        iterator.remove();
		        removedTransactions++;
		    }
		}
		
		System.out.println("Blogos/ištrintos transakcijos: " + removedTransactions);
		System.out.println("Geros likusios transakcijos: " + validTransactions);
		System.out.println();

	}
}
