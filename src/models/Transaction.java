package models;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import functions.HashFunc;

public class Transaction {

	public static int transactionNumber = 1;
	
	public String transactionID;
	public User sender;
	public User receiver;
	private int balance;
	private boolean used = false;
	
	public Transaction(User sender, User receiver, int amount) {
		this.sender = sender;
		this.receiver = receiver;
		this.balance = amount;
		sender.deductBalance(amount);
		receiver.addBalance(amount);
		this.transactionID = HashFunc.generateHash(this.sender.getName() + this.receiver.getName() + Integer.toString(amount));
		this.used = true;
		createTransactionFile();
		transactionNumber++;
	}
	
    public void createTransactionFile() {
        try {
            Path path = Paths.get("blockchain/transactions");
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            String fileName = "blockchain/transactions/Transaction_" + this.transactionID + ".txt";
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(this.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
	public String toString() {
		return "TransactionID: " + this.transactionID + "\nSender: " + this.sender.getName() + "\nReceiver: " + this.receiver.getName() + "\nBalance: " + Integer.toString(balance);
	}
	public String getTransactionID() {
		return this.transactionID;
	}
}
