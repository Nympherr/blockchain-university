package models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

import main.Main;

public class Blockchain {
	
	public static int miningDifficulty = 6;
	public static double blockchainVersion = 1.0;
	public static final int MAX_TRANSACTIONS = 100;
	
    private ArrayList<Block> chain;

    public Blockchain() {
        this.chain = new ArrayList<>();
        this.chain.add(createGenesisBlock());
    }

    private Block createGenesisBlock() {
    	Block block = new Block("0", blockchainVersion, 4);
    	block.mineGenesisBlock();
        return block;
    }

    public void addBlock() {
    	
        if (Main.blockchain == null) {
            Main.blockchain = new Blockchain();
        }

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
        Random random = new Random();
        int timeLimitInSeconds = 10;
        boolean isMined = false;
        
        for (int attempt = 0; attempt < 5; attempt++) {
            List<Block> candidates = generateBlockCandidates(transactionPool, 5);
            Map<Integer, Block> candidateMap = new HashMap<>();
            IntStream.range(0, candidates.size()).forEach(i -> candidateMap.put(i, candidates.get(i)));

            while (!candidateMap.isEmpty()) {
                List<Integer> keys = new ArrayList<>(candidateMap.keySet());
                int randomKeyIndex = random.nextInt(keys.size());
                int candidateIndex = keys.get(randomKeyIndex);
                Block candidate = candidateMap.get(candidateIndex);

                System.out.println();
                System.out.println("Candidate " + (candidateIndex + 1) + " is mining the block");
                if (tryMineBlock(candidate, timeLimitInSeconds)) {
                    System.out.println("Candidate " + (candidateIndex + 1) + " successfully mined the block");
                    this.chain.add(candidate);
                    isMined = true;
                    break;
                }
                System.out.println("Candidate " + (candidateIndex + 1) + " failed to mine the block");
                candidateMap.remove(candidateIndex);
            }

            if (isMined) {
                break;
            } else {
                miningDifficulty--;
            }
        }

        if (!isMined) {
            if (Main.transactionPool.size() < 0) {
                System.out.println("No more transactions in the pool");
            } else {
                System.out.println("Failed to mine blocks. Generating new candidates.");
            }
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

    private boolean tryMineBlock(Block block, int timeLimitInSeconds) {
    	
        long startTime = System.currentTimeMillis();
        int nonce = 0;
        
       	if(block.mineBlock(nonce) == false) {
    		return false;
    	}
        
        while ((System.currentTimeMillis() - startTime) < timeLimitInSeconds * 1000) {
        	
        	block.mineBlock(nonce);
        	
            if (block.getBlockHash().substring(0, miningDifficulty).equals("0".repeat(miningDifficulty))) {
            	
            	System.out.println("Blokas iškastas su hash: " + block.getBlockHash());
                System.out.println("Blokas iškastas su nonce: " + nonce);
                Main.transactionPool.removeAll(block.getTransactions());
                for(Transaction transaction : block.getTransactions()) {
                	Main.usedTransactions.put(transaction, block);
                }
                block.saveBlockToFile();
                Block.blocksInChain++;
            	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime minedTime = LocalDateTime.now();
                block.formattedMinedTime = minedTime.format(formatter); 
            	return true;
            	
            }
            
            nonce++;
        }
        return false;
    }

}