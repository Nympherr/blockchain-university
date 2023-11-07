package models;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import functions.GenerateNames;
import functions.GeneratePrivateKey;
import functions.HashFunc;

public class User {
	
	private Random rand = new Random();
	private final int rangeMin = 100;
	private final int rangeMax = 1000000;
	
	public static int numberOfUsers = 0;
	
	private int ID;
	private String name;
	private String publicKey;
	private String privateKey;
	private int balance;
	
	public User(){
		
		this.ID = numberOfUsers;
		this.name = GenerateNames.generateRandomName();
		this.privateKey = GeneratePrivateKey.generateRandomPrivateKey();
		this.publicKey = HashFunc.generateHash(this.privateKey);
		this.balance = rand.nextInt(rangeMax - rangeMin + 2) + rangeMin;
		
		createUserFile();
		numberOfUsers++;
		
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
		}
		catch (IOException e) {
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
	public String getPublicKey() {
		return this.publicKey;
	}
}
