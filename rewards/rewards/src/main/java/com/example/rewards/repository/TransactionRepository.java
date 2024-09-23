package com.example.rewards.repository;

import com.example.rewards.model.Customer;
import com.example.rewards.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // Method to find transactions for a customer between a date range
    //List<Transaction> findByCustomerAndLocalDateBetween(Customer customer, LocalDate startDate, LocalDate endDate);
    List<Transaction> findByCustomerAndTransactionDateBetween(Customer customer, LocalDate startDate, LocalDate endDate);

}