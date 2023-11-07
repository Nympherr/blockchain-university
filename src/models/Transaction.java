package models;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.time.LocalDateTime;

import functions.HashFunc;

public class Transaction {

	public static int transactionNumber = 0;
	
	public LocalDateTime creationTime;
	public String transactionID;
	private User sender;
	private User receiver;
	private int balance;
	private boolean transactionUsed = false;
	
	public Transaction(User sender, User receiver, int amount) {
		this.creationTime = LocalDateTime.now();
		this.sender = sender;
		this.receiver = receiver;
		this.balance = amount;
		this.transactionID = HashFunc.generateHash(this.sender.getPublicKey() + this.receiver.getPublicKey() + Integer.toString(amount));
		createTransactionFile();
		transactionNumber++;
	}
	
	public boolean checkBalance() {
		
		if(this.sender.checkBalance() >= getBalance()) {
			System.out.println("Sender has enough balance to send money");
			return true;
		}
		else {
			System.out.println("Sender does not have enough balance.");
			return false;
		}
	}
	
	public void activateTransaction() {
		
		this.transactionUsed = true;
		this.sender.deductBalance(getBalance());
		this.receiver.addBalance(getBalance());
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
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(this.transactionID, that.transactionID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.transactionID);
    }
    
	public String toString() {
		return "TransactionID: " + this.transactionID + "\nSender: " + this.sender.getPublicKey()
				+ "\nReceiver: " + this.receiver.getPublicKey() + "\nBalance: " + Integer.toString(balance)
				+ "\nIs transaction activated: " + this.transactionUsed + "\nTransaction creation time: " + this.creationTime;
	}
	public String getTransactionID() {
		return this.transactionID;
	}
	public int getBalance() {
		return this.balance;
	}
}
