package functions;

import java.util.ArrayList;
import java.util.Map;

import main.Main;
import models.Block;
import models.Transaction;

public class CheckTransaction {

	public static void getTransactionInfo(Map<Transaction, Block> usedTransactions, ArrayList<Transaction> transactionPool) {
		
		System.out.println("------------------------");
		System.out.println("Įveskite transakcijos ID:");
		System.out.print("Jūsų pasirinkimas: ");
		String transactionID = Main.scanner.next();
		boolean foundTransaction = false;
			
		for(Transaction transaction : usedTransactions.keySet()) {
			if (transaction.getTransactionID().equals(transactionID)) {
				Block associatedBlock = usedTransactions.get(transaction);
				System.out.println(associatedBlock);
				System.out.println();
		        System.out.println(transaction);
		        foundTransaction = true;
		        break;
		    }
		}
		if(foundTransaction == false) {
			for(Transaction transaction : transactionPool) {
				if(transaction.getTransactionID().equals(transactionID)) {
					System.out.println();
					System.out.println(transaction);
					foundTransaction = true;
				break;
				}
			}
		}
		if(foundTransaction == false) {
			System.out.println("Tokia transakcija neegzistuoja");
		}
					
	}
}
