package com.example.rewards.service;

import com.example.rewards.model.Customer;
import com.example.rewards.model.Transaction;
import com.example.rewards.repository.CustomerRepository;
import com.example.rewards.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RewardService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    /**
     * Retrieve reward points for all customers within the given date range.
     */
    public Map<String, Map<String, Integer>> getAllCustomersRewardPoints(String startDate, String endDate) {
        Map<String, Map<String, Integer>> customerRewards = new HashMap<>();

        // Parse the input date strings to LocalDate
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        // Retrieve all customers
        List<Customer> allCustomers = customerRepository.findAll();

        for (Customer customer : allCustomers) {
            // For each customer, get their points per month and store in the map
            Map<String, Integer> monthlyPoints = getPointsPerMonthForCustomer(customer.getId().toString(), startDate, endDate);
            customerRewards.put(customer.getId().toString(), monthlyPoints);
        }

        return customerRewards;
    }

    /**
     * Calculate reward points per month for a specific customer.
     */
    public Map<String, Integer> getPointsPerMonthForCustomer(String customerId, String startDate, String endDate) {
        // Initialize a map to hold monthly points
        Map<String, Integer> monthlyPoints = new HashMap<>();

        // Parse the input date strings to LocalDate
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        // Find the customer by ID
        Long customerLongId = Long.parseLong(customerId);
        Customer customer = customerRepository.findById(customerLongId).orElse(null);

        if (customer == null) {
            return monthlyPoints; // Return empty map if customer is not found
        }

        // Get all transactions for this customer within the given date range
        List<Transaction> transactions = transactionRepository.findByCustomerAndTransactionDateBetween(customer, start, end);

        // Loop through transactions and calculate points
        for (Transaction transaction : transactions) {
            LocalDate transactionDate = transaction.getTransactionDate();
            String monthKey = transactionDate.getYear() + "-" + String.format("%02d", transactionDate.getMonthValue());

            // Calculate points for this transaction
            int points = calculateRewardPoints(transaction.getAmount());

            // Add points to the corresponding month in the map
            monthlyPoints.put(monthKey, monthlyPoints.getOrDefault(monthKey, 0) + points);
        }

        return monthlyPoints;
    }

    /**
     * Reward points calculation logic
     * 2 points for every dollar spent over $100, 1 point for every dollar between $50 and $100.
     */
    public int calculateRewardPoints(double amount) {
        int points = 0;

        if (amount > 100) {
            points += (amount - 100) * 2;
            points += 50; // For the amount between $50 and $100
        } else if (amount > 50) {
            points += (amount - 50);
        }

        return points;
    }
}