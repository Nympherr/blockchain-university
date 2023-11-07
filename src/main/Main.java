package main;
import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) {

    	Scanner scanner = new Scanner(System.in);
    	boolean programRunning = true;
    	
    	while(programRunning) {
	    	System.out.println("==================================");
	    	System.out.println("||                              ||");
	    	System.out.println("||       PROGRAM START          ||");
	    	System.out.println("||                              ||");
	    	System.out.println("==================================");
	    	System.out.println();
	    	System.out.println("------------------------");
	    	System.out.println("1.Sukurti atsitiktinius vartotojus");
	    	System.out.println("2.Sukurti atsitiktines transakcijas");
	    	System.out.println("3.Bloko kasimas");
	    	System.out.println("4.Bloko peržiūra");
	    	System.out.println("5.Transakcijos peržiūra");
	    	System.out.println("6.Bendra informacija");
	    	System.out.println("7.Baigti darbą");
	    	System.out.println("------------------------");
	    	System.out.print("Jūsų pasirinkimas:");
	    	int choice = scanner.nextInt();
	    	
	    	switch(choice) {
	    	
	    		case 1:
	    			System.out.println("This is first option");
	    			break;
	    		case 2:
	    			System.out.println("This is second option");
	    			break;
	    		case 3:
	    			System.out.println("This is third option");
	    			break;
	    		case 4:
	    			System.out.println("This is fourth option");
	    			break;
	    		case 5:
	    			System.out.println("This is fifth option");
	    			break;
	    		case 6:
	    			System.out.println("This is sixth option");
	    			break;
	    		case 7:
	    			System.out.println("Program ends");
	    			programRunning = false;
	    			break;
	    		default:
	    			System.out.println("Blogas pasirinkimas, bandykite per naujo");
	    			break;
	    	}
    	}
    }
}
