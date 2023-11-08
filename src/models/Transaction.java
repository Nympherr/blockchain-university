package models;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import functions.HashFunc;

public class Transaction {

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	public static int transactionNumber = 0;
	
	public String formattedCreationTime;
	public String transactionID;
	private User sender;
	private User receiver;
	private int balance;
	private boolean transactionUsed = false;
	
	public Transaction(User sender, User receiver, int amount) {
		
        LocalDateTime creationTime = LocalDateTime.now();
        this.formattedCreationTime = creationTime.format(formatter); 
		this.sender = sender;
		this.receiver = receiver;
		this.balance = amount;
		this.transactionID = HashFunc.generateHash(this.sender.getPublicKey() + this.receiver.getPublicKey() + Integer.toString(amount) + this.formattedCreationTime);
		createTransactionFile();
		transactionNumber++;
	}
	
	public boolean checkBalance() {
		
		if(this.sender.checkBalance() >= getBalance()) {
			return true;
		}
		else {
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
				+ "\nIs transaction activated: " + this.transactionUsed + "\nTransaction creation time: " + this.formattedCreationTime;
	}
	public String getTransactionID() {
		return this.transactionID;
	}
	public int getBalance() {
		return this.balance;
	}
	public String getSender() {
		return this.sender.getPublicKey();
	}
	public String getReceiver() {
		return this.receiver.getPublicKey();
	}
	public int getAmount() {
		return this.balance;
	}
	public String getCreationTime() {
		return this.formattedCreationTime;
	}
}
