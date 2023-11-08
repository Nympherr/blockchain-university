package functions;

import models.User;
import main.Main;

public class GenerateUsers {
	
	public static void generateUsers() {
		
		System.out.println("");
    	System.out.println("===========================================");
    	System.out.println("||                                       ||");
    	System.out.println("||       NAUJŲ VARTOTOJŲ KŪRIMAS         ||");
    	System.out.println("||                                       ||");
    	System.out.println("===========================================");
		System.out.println("");
		System.out.println("Dabar yra sukurta vartotojų: " + User.numberOfUsers);
		System.out.print("Kiek norėsite sukurti vartotojų: ");
		int number = Main.scanner.nextInt();
		
		for(int i = 0; i < number; i++) {
			User user = new User();
			Main.users.add(user);
		}
		System.out.println("Nauji vartotojai sukurti. Iš viso vartotojų: " + User.numberOfUsers);
		System.out.println("");
		
	}

}
