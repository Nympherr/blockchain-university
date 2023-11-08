package functions;

import java.util.Random;

import main.Main;
import models.User;
import models.Transaction;

public class GenerateTransactions {
	
	public static void generateTransactions() {
		
		System.out.println("");
    	System.out.println("============================================");
    	System.out.println("||                                        ||");
    	System.out.println("||       NAUJŲ TRANSAKCIJŲ KŪRIMAS        ||");
    	System.out.println("||                                        ||");
    	System.out.println("============================================");
		System.out.println("");
		System.out.println("Vartotojų skaičius: " + User.numberOfUsers);
		System.out.println("Nepanaudotų transakcijų skaičius: " + Main.transactionPool.size());
		System.out.println("Panaudotų transakcijų skaičius: " + (Transaction.transactionNumber - Main.transactionPool.size()));
		System.out.print("Kiek norėsite sukurti transakcijų: ");
		int number = Main.scanner.nextInt();
		int userCount = Main.users.size();
		Random rand = new Random();
		
		for(int i = 0; i < number; i++) {
			int amount = rand.nextInt(100000) + 1;
			int firstIndex;
			int secondIndex;
			do {
				firstIndex = rand.nextInt(userCount);
				secondIndex = rand.nextInt(userCount);
			}while(firstIndex == secondIndex);
			
			User sender = Main.users.get(firstIndex);
			User receiver = Main.users.get(secondIndex);
			Transaction transaction = new Transaction(sender,receiver, amount);
			Main.transactionPool.add(transaction);
		}
		
		System.out.println("Naujos transakcijos sukurtos");
		System.out.println("");
		
	}
}
