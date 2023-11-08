package models;

import java.util.ArrayList;
import java.util.List;

import main.Main;

public class Blockchain {
	
	public static int miningDifficulty = 7;
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

//        String previousBlockHash = this.chain.get(this.chain.size() - 1).getBlockHash();
//        Block newBlock = new Block(previousBlockHash, blockchainVersion, miningDifficulty);
//        if (newBlock.getTransactionSize() > 0) {
//            newBlock.mineBlock();
//            this.chain.add(newBlock);
//        }
        addBlockWithCandidates(Main.transactionPool);
    }
    
    public void displayBlockchain() {
    	for(Block block : chain) {
    		System.out.println(block);
    	}
    }
    
    public ArrayList<Block> getBlockchain(){
    	return chain;
    }
    
    public void addBlockWithCandidates(List<Transaction> transactionPool) {
    	
        List<Block> candidates = generateBlockCandidates(transactionPool, 5);
        boolean isMined = false;
        
        int timeLimitInSeconds = 1;
        int maxAttempts = 100;
        
        for (int attempt = 0; attempt < 1; attempt++) {
            for (Block candidate : candidates) {
            	System.out.println("Candidate x is mining");
                if (tryMineBlock(candidate, timeLimitInSeconds, maxAttempts)) {
             
                    this.chain.add(candidate);
                    isMined = true;
                    break;
                }
            }
            
            if (isMined) {
                break;
            }
            else {

                timeLimitInSeconds += 0;
                maxAttempts +=  0;
                candidates = generateBlockCandidates(transactionPool, 5);
            }
        }
        
        if (!isMined) {
            System.out.println("Failed to mine any block after two attempts.");
        }
    }
    private List<Block> generateBlockCandidates(List<Transaction> transactionPool, int numCandidates) {
    	
        List<Block> candidates = new ArrayList<>();
        
        for (int i = 0; i < numCandidates && transactionPool.size() > 0; i++) {
        	               
            String previousBlockHash = this.chain.get(this.chain.size() - 1).getBlockHash();
            Block block = new Block(previousBlockHash, blockchainVersion, miningDifficulty);
            if (block.getTransactionSize() > 0) {
            	candidates.add(block);
            }
            else {
            	i--;
            }

        }
        return candidates;
    }

    private boolean tryMineBlock(Block block, int timeLimitInSeconds, int maxAttempts) {
    	
        long startTime = System.currentTimeMillis();
        int attempts = 0;
        
        while ((System.currentTimeMillis() - startTime) < timeLimitInSeconds * 1000 && attempts < maxAttempts) {
            block.mineBlock();
            
            if (block.getBlockHash().substring(0, miningDifficulty).equals("0".repeat(miningDifficulty))) {
                return true;
            }
            attempts++;
        }
        return false;
    }

}