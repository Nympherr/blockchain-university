package models;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;

import functions.MerkleRootHash;
import functions.NewHash;
import functions.SelectRandomTransactions;
import main.Main;

public class Block {
	
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	public static int blocksInChain = 0;
	
    private ArrayList<Transaction> transactions;
    private String blockHash;
    private String previousBlockHash;
    private String merkleRootHash;
    
    private int blockID;
    private double version;
    private int nonce;
    private int difficultyTarget;
    public String formattedCreationTime;
    public String formattedMinedTime;

    public Block(String prevBlockHash, double blockchainVersion, int difficulty) {
        this.transactions = SelectRandomTransactions.selectRandomTransactions(Main.transactionPool);
        this.previousBlockHash = prevBlockHash;
        this.version = blockchainVersion;
        this.difficultyTarget = difficulty;
        LocalDateTime creationTime = LocalDateTime.now();
        this.formattedCreationTime = creationTime.format(formatter); 
        this.merkleRootHash = MerkleRootHash.createMerkleRootHash(this.transactions);
        this.blockID = blocksInChain;
        this.blockHash = null;
    }

    public String calculateHash() {
        return NewHash.sha256(this.merkleRootHash + this.previousBlockHash + this.formattedCreationTime + this.version + this.difficultyTarget + Integer.toString(nonce));
    }

    public void mineBlock() {
    	
    	if(this.transactions.size() > 0) {
        	nonce = 0;
        	
            blockHash = this.calculateHash();
            String target = new String(new char[this.difficultyTarget]).replace('\0', '0');
            do {
                nonce++;
                blockHash = calculateHash();
            } while (!blockHash.substring(0, this.difficultyTarget).equals(target));
            
            
            System.out.println("\nBlock Mined with hash: " + blockHash);
            System.out.println("Block Mined with nonce: " + nonce);
            Main.transactionPool.removeAll(this.transactions);
            for(Transaction transaction : this.transactions) {
            	Main.usedTransactions.put(transaction, this);
            }
            saveBlockToFile();
            blocksInChain++;
            LocalDateTime minedTime = LocalDateTime.now();
            this.formattedMinedTime = minedTime.format(formatter); 
    	}
    }
    
    public void saveBlockToFile() {
        try (FileWriter writer = new FileWriter("blockchain/blocks/" + this.blockHash + ".txt")) {
            writer.write("Version: " + this.version + "\n");
            writer.write("Previous Block Hash: " + this.previousBlockHash + "\n");
            writer.write("Timestamp: " + this.formattedCreationTime + "\n");
            writer.write("Difficulty Target: " + this.difficultyTarget + "\n");
            writer.write("Nonce: " + this.nonce + "\n");
            writer.write("Block Hash: " + this.blockHash + "\n");
            writer.write("Merkle Root Hash: " + this.merkleRootHash + "\n");
            writer.write("Transactions:\n");
            for (Transaction transaction : this.transactions) {
            	transaction.activateTransaction();
                writer.write(transaction.toString() + "\n"); 
            }
        } catch (IOException e) {
            System.out.println("An error occurred while saving the block: " + e.getMessage());
        }
    }
    public int getTransactionSize() {
    	return this.transactions.size();
    }
    public void removeMinedTransactions() {
        Main.transactionPool.removeAll(this.transactions);
    }
    public String getBlockHash() {
    	return this.blockHash;
    }
    public String toString() {
    	return "\nBlock ID: " + this.blockID + "\nTransactions in block: " + this.transactions.size() +
    			"\nBlock creation time: " + this.formattedCreationTime + "\nNonce: " + this.nonce + "\nVersion: " + this.version
    			+ "\nDifficulty: " + this.difficultyTarget + "\nMerkle root hash: " + this.merkleRootHash +
    			"\nPrevious block hash: " + this.previousBlockHash + "\nBlock hash: " + this.blockHash + "\nWhen was mined: " + this.formattedMinedTime;
    }
    public void showTransactions() {
    	for(Transaction transaction : transactions) {
    		System.out.println(transaction);
    		System.out.println();
    	}
    }
}
