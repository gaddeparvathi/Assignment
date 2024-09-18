package com.example.rewards.service;

import com.example.rewards.model.Transaction;
import com.example.rewards.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RewardService {

    private final TransactionRepository transactionRepository;

    public RewardService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public int calculatePoints(double amount) {
        int points = 0;
        if (amount > 100) {
            points += (amount - 100) * 2;
            amount = 100;
        }
        if (amount > 50) {
            points += (amount - 50);
        }
        return points;
    }

    public int getTotalPointsForCustomer(String customerId, String startDate, String endDate) {
        List<Transaction> transactions = transactionRepository.findByCustomerIdAndTransactionDateBetween(customerId, startDate, endDate);
        return transactions.stream()
                .mapToInt(transaction -> calculatePoints(transaction.getAmount()))
                .sum();
    }
}
