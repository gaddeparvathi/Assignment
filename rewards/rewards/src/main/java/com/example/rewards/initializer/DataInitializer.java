package com.example.rewards.initializer;

import com.example.rewards.model.Customer;
import com.example.rewards.model.Transaction;
import com.example.rewards.repository.CustomerRepository;
import com.example.rewards.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public void run(String... args) throws Exception {
        // Create and save some customers
        Customer customer1 = new Customer("Joseph", "joseph@example.com");
        Customer customer2 = new Customer("john", "john@example.com");

        customerRepository.saveAll(Arrays.asList(customer1, customer2));

        // Create and save some transactions for customer1
        Transaction transaction1 = new Transaction(120.0, LocalDate.of(2024, 7, 15), customer1); // 90 points
        Transaction transaction2 = new Transaction(80.0, LocalDate.of(2024, 7, 25), customer1);  // 30 points
        Transaction transaction3 = new Transaction(150.0, LocalDate.of(2024, 8, 10), customer1); // 150 points
        Transaction transaction4 = new Transaction(60.0, LocalDate.of(2024, 9, 5), customer1);   // 10 points

        // Create and save some transactions for customer2
        Transaction transaction5 = new Transaction(200.0, LocalDate.of(2024, 7, 20), customer2); // 250 points
        Transaction transaction6 = new Transaction(90.0, LocalDate.of(2024, 8, 15), customer2);  // 40 points
        Transaction transaction7 = new Transaction(50.0, LocalDate.of(2024, 9, 8), customer2);   // 0 points

        // Save all transactions
        transactionRepository.saveAll(Arrays.asList(transaction1, transaction2, transaction3, transaction4,
                transaction5, transaction6, transaction7));

        System.out.println("Sample data initialized.");
    }
}