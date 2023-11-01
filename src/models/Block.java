package models;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.List;

import functions.HashFunc;

public class Block {
    private List<Transaction> transactions;
    private String prevBlockHash;
    private String blockHash;
    private int nonce;
    private int version;
    private long timestamp;
    private int difficultyTarget;

    public Block(List<Transaction> transactions, String prevBlockHash, int version, int difficultyTarget) {
        this.transactions = transactions;
        this.prevBlockHash = prevBlockHash;
        this.version = version;
        this.difficultyTarget = difficultyTarget;
        this.nonce = 0;
        this.timestamp = Instant.now().getEpochSecond(); 
    }

    public String calculateHash() {
        return HashFunc.generateHash(transactions.toString() + prevBlockHash + Integer.toString(nonce));
    }

    public void mineBlock(int difficulty) {
        blockHash = this.calculateHash();
        String target = new String(new char[difficulty]).replace('\0', '0');
        while (!blockHash.substring(0, difficulty).equals(target)) {
            nonce++;
            blockHash = this.calculateHash();
        }
        System.out.println("Block Mined with hash: " + blockHash);
    }
    
    public void saveBlockToFile() {
        try (FileWriter writer = new FileWriter("blockchain/blocks/" + this.blockHash + ".txt")) {
            writer.write("Version: " + this.version + "\n");
            writer.write("Previous Block Hash: " + this.prevBlockHash + "\n");
            writer.write("Timestamp: " + this.timestamp + "\n");
            writer.write("Difficulty Target: " + this.difficultyTarget + "\n");
            writer.write("Nonce: " + this.nonce + "\n");
            writer.write("Block Hash: " + this.blockHash + "\n");
            writer.write("Transactions:\n");
            for (Transaction transaction : this.transactions) {
                writer.write(transaction.toString() + "\n"); 
            }
        } catch (IOException e) {
            System.out.println("An error occurred while saving the block: " + e.getMessage());
        }
    }
    public void removeMinedTransactions() {
        for (Transaction tx : this.transactions) {
            String fileName = "blockchain/transactions/Transaction_" + tx.getTransactionID() + ".txt";
            java.nio.file.Path path = java.nio.file.Paths.get(fileName);
            try {
                java.nio.file.Files.deleteIfExists(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
