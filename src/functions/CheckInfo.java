package functions;

import java.util.ArrayList;

import main.Main;
import models.Block;
import models.Blockchain;
import models.Transaction;

public class CheckInfo {

	public static void getInformation(Blockchain blockchain) {
		
		if(blockchain == null) {
	    	System.out.println("\n=====================================");
	    	System.out.println("||                                 ||");
	    	System.out.println("||       BENDRA INFORMACIJA        ||");
	    	System.out.println("||                                 ||");
	    	System.out.println("=====================================");
	    	
			System.out.println("\nVartotojų skaičius: " + Main.users.size());
			System.out.println("Kiek sukurta transakcijų: " + Transaction.transactionNumber);
			System.out.println("Kiek transakcijų yra pool'e: " + Main.transactionPool.size());
			System.out.println("Kiek transakcijų yra blokuose: 0");
			System.out.println("Atšauktos transakcijos: " + (Transaction.transactionNumber - Main.transactionPool.size()));
			System.out.println("Iškasti blokai: 0");
			System.out.println();
		}
		else {
			
		    ArrayList<Block> chain = blockchain.getBlockchain();
		    int usedTransactions = 0;
		    
		    for(Block block : chain) {
		    	usedTransactions += block.getTransactionSize();
		    }
		    	
	    	System.out.println("=====================================");
	    	System.out.println("||                                 ||");
	    	System.out.println("||       BENDRA INFORMACIJA        ||");
	    	System.out.println("||                                 ||");
	    	System.out.println("=====================================");
			System.out.println("\nVartotojų skaičius: " + Main.users.size());
			System.out.println("Kiek sukurta transakcijų: " + Transaction.transactionNumber);
			System.out.println("Kiek transakcijų yra pool'e: " + Main.transactionPool.size());
			System.out.println("Kiek transakcijų yra blokuose: " + usedTransactions);
			System.out.println("Atšauktos transakcijos: " + (Transaction.transactionNumber - Main.transactionPool.size() - usedTransactions));
			System.out.println("Iškasti blokai: " + Block.blocksInChain);
			System.out.println();
		}
	}
}
