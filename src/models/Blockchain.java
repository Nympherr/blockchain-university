package models;

import java.util.ArrayList;

import main.Main;

public class Blockchain {
	
	public static int miningDifficulty = 0;
	public static double blockchainVersion = 1.0;
	public static final int MAX_TRANSACTIONS = 100;
	
    private ArrayList<Block> chain;

    public Blockchain() {
        this.chain = new ArrayList<>();
        this.chain.add(createGenesisBlock());
    }

    private Block createGenesisBlock() {
    	Block block = new Block("0", blockchainVersion, miningDifficulty);
    	block.mineBlock();
        return block;
    }

    public void addBlock() {
        if (Main.blockchain == null) {
            Main.blockchain = new Blockchain();
        }
        // Link the new block with the previous block's hash
        String previousBlockHash = this.chain.get(this.chain.size() - 1).getBlockHash();
        Block newBlock = new Block(previousBlockHash, blockchainVersion, miningDifficulty);
        if (newBlock.getTransactionSize() > 0) {
            newBlock.mineBlock();
            this.chain.add(newBlock);
        }
    }
    
    public void displayBlockchain() {
    	for(Block block : chain) {
    		System.out.println(block);
    	}
    }
    
    public ArrayList<Block> getBlockchain(){
    	return chain;
    }
//
//    public Block getLatestBlock() {
//        return this.chain.get(this.chain.size() - 1);
//    }
//
//    public boolean isChainValid() {
//        // Code to validate the integrity of the blockchain
//        // For example, by checking if hashes are consistent and if blocks are properly mined
//    }

    // ... Additional blockchain methods
}