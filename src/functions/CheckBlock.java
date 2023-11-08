package functions;

import java.util.ArrayList;

import main.Main;
import models.Block;
import models.Blockchain;
import models.Transaction;

public class CheckBlock {

	public static void blockInformation(Blockchain blockchain) {
		
		if(blockchain == null) {
			System.out.println("------------------------");
			System.out.println("Blokai dar nebuvo sukurti");
		}
		else {
			
			System.out.println("------------------------");
			System.out.println("Kaip norite surasti bloką:");
			System.out.println("1.Pagal bloko ID");
			System.out.println("2.Pagal bloko hash");
			System.out.print("Jūsų pasirinkimas: ");
			int choice = Main.scanner.nextInt();
			
			switch(choice) {
			
				case 1:
					System.out.println();
					System.out.println("Įveskite bloko ID:");
					int blockChoice = Main.scanner.nextInt();
					if (blockChoice >= 0 && blockChoice < blockchain.getBlockchain().size()) {
					    Block block = blockchain.getBlockchain().get(blockChoice);
					    System.out.println(block);
					    System.out.println();
					    block.showTransactions();
					}
					else {
					    System.out.println("Toks blokas neegzistuoja.");
					}
					break;
					
				case 2:
					System.out.println();
					System.out.println("Įveskite bloko hash:");
					String blockChoice2 = Main.scanner.next();
					boolean foundBlock = false;
					
					for(Block block : blockchain.getBlockchain()) {
			            if (blockChoice2.equals(block.getBlockHash())) {
						    System.out.println(block);
						    System.out.println();
						    block.showTransactions();
						    foundBlock = true;
			            }
					}
					
					if(foundBlock == false) {
						System.out.println("Toks blokas neegzistuoja.");
					}
					break;
			}
		}
	}
}
