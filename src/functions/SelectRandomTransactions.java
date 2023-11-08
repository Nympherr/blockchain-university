package functions;

import java.util.ArrayList;
import java.util.Random;

import main.Main;
import models.Transaction;
import models.Blockchain;

public class SelectRandomTransactions {
	
	public static ArrayList<Transaction> selectRandomTransactions(ArrayList<Transaction> transactions){
		
		ArrayList<Transaction> copyTransactions = new ArrayList<>(transactions);

		ArrayList<Transaction> transactionList = new ArrayList<>();
		Random rand = new Random();
		
		int maxAmount = Blockchain.MAX_TRANSACTIONS;
		int transactionPoolSize = copyTransactions.size();
		
		for(int i = 0; i < maxAmount && transactionPoolSize > 0; i++) {
			
			int index = rand.nextInt(transactionPoolSize);
			Transaction currentTransaction = copyTransactions.get(index);
			if(currentTransaction.checkBalance()) {
				transactionList.add(currentTransaction);
				copyTransactions.remove(index);
				transactionPoolSize--;
			}
			else {
				Main.transactionPool.remove(index);
				copyTransactions.remove(index);
				transactionPoolSize--;
				i--;
			}

		}
		
		return transactionList;	
	}
}
