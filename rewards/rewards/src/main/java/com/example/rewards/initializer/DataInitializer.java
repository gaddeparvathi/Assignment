package com.example.rewards.initializer;

import com.example.rewards.model.Transaction;
import com.example.rewards.repository.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final TransactionRepository transactionRepository;

    public DataInitializer(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void run(String... args) {
        // Insert sample data
        transactionRepository.save(new Transaction(null, "cust1", 120, "2024-08-15"));
        transactionRepository.save(new Transaction(null, "cust1", 80, "2024-08-20"));
        transactionRepository.save(new Transaction(null, "cust2", 200, "2024-08-25"));
    }
}
