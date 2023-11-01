package main;

import models.User;
import models.Block;
import models.Transaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import functions.GetUsers;

public class Main {
    
    public static void main(String[] args) {
        List<User> userList = GetUsers.getUsers();
        List<Transaction> transactionPool = new ArrayList<>();

        Random rand = new Random();
        for (int i = 0; i < 10000; i++) {

            int index1, index2;
            do {
                index1 = rand.nextInt(userList.size());
                index2 = rand.nextInt(userList.size());
            } while (index1 == index2);

            User user1 = userList.get(index1);
            User user2 = userList.get(index2);

            int amount = rand.nextInt(100000) + 1;    
            Transaction transaction = new Transaction(user1, user2, amount);
            transactionPool.add(transaction);
        }

        String previousBlockHash = "some_hash_value";

        while (transactionPool.size() >= 100) {
            // Get first 100 transactions
            List<Transaction> blockTransactions = transactionPool.subList(0, 100);

            // Create new Block
            Block newBlock = new Block(new ArrayList<>(blockTransactions), previousBlockHash, 1, 0);
            newBlock.mineBlock(0); 
            newBlock.saveBlockToFile();
            transactionPool.subList(0, 100).clear(); 
            previousBlockHash = newBlock.calculateHash();
        }
    }
}
