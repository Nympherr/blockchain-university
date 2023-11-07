package main;

import models.User;
import models.Block;
import models.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import functions.Cleanup;
import functions.GenerateTransactions;
import functions.GenerateUsers;

public class Main {
    
	public static ArrayList<User> users = new ArrayList<>();
	public static ArrayList<Block> blocks = new ArrayList<>();
	public static ArrayList<Transaction> transactionPool = new ArrayList<>();
	public static Map<Transaction, Block> usedTransactions = new HashMap<>();
	public static Scanner scanner = new Scanner(System.in);
	
    public static void main(String[] args) {
    
    	boolean programRunning = true;
    	
    	while(programRunning) {
	    	System.out.println("=====================================");
	    	System.out.println("||                                 ||");
	    	System.out.println("||       PROGRAMOS PRADŽIA         ||");
	    	System.out.println("||                                 ||");
	    	System.out.println("=====================================");
	    	System.out.println();
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
	    			GenerateUsers.generateUsers();
	    			break;
	    		case 2:
	    			GenerateTransactions.generateTransactions();;
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
	    			Cleanup.deleteFilesInDirectory("blockchain/transactions");
	    			Cleanup.deleteFilesInDirectory("blockchain/users");
	    			Cleanup.deleteFilesInDirectory("blockchain/blocks");
	    			System.out.println("Program end");
	    			programRunning = false;
	    			break;
	    		default:
	    			System.out.println("Blogas pasirinkimas, bandykite per naujo");
	    			break;
	    	}
    	}
    }
}
