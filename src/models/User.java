package models;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import functions.GenerateNames;
import functions.GeneratePublicKey;
import functions.HashFunc;

public class User {
	
	public static int numberOfUsers = 1;
	
	private int ID;
	private String name;
	private String publicKey;
	private String privateKey;
	private int balance;
	
	public User(){
		
		this.ID = numberOfUsers;
		numberOfUsers++;
		
		this.name = GenerateNames.generateRandomName();
		
		this.publicKey = GeneratePublicKey.generateRandomPublicKey();
		this.publicKey = HashFunc.generateHash(this.publicKey);
		
		int rangeMin = 100;
		int rangeMax = 1000000;
		Random rand = new Random();
		this.balance = rand.nextInt(rangeMax - rangeMin + 2) + rangeMin;
		createUserFile();
	}
	
	public User(String fileName) {
	    try {
	        BufferedReader reader = new BufferedReader(new FileReader(fileName));
	        String line;
	        while ((line = reader.readLine()) != null) {
	            if (line.startsWith("ID: ")) {
	                this.ID = Integer.parseInt(line.substring(4));
	            } else if (line.startsWith("Name: ")) {
	                this.name = line.substring(6);
	            } else if (line.startsWith("Public Key: ")) {
	                this.publicKey = line.substring(12);
	            } else if (line.startsWith("Balance: ")) {
	                this.balance = Integer.parseInt(line.substring(9));
	            }
	        }
	        reader.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public void createUserFile() {
		try {

		    java.nio.file.Path path = java.nio.file.Paths.get("blockchain/users");
		    if (!java.nio.file.Files.exists(path)) {
		        java.nio.file.Files.createDirectories(path);
		    }

		    String fileName = "blockchain/users/User_" + this.ID + ".txt"; // File will be inside the 'users' folder
		    BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
		    writer.write(this.toString());
		    writer.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	public String toString() {
		return "ID: " + this.ID + "\nName: " + this.name + "\nPublic Key: " + this.publicKey + "\nBalance: " + this.balance;
	}
	public void addBalance(int amount) {
		this.balance += amount;
	}
	public void deductBalance(int amount) {
		this.balance -= amount;
	}
	public int checkBalance() {
		return this.balance;
	}
	public String getName() {
		return this.name;
	}
	public void changeFileInfo() {
		try {
			String fileName = "blockchain/users/User_" + this.ID + ".txt";
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
			writer.write(this.toString());
			writer.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
