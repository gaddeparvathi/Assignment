package com.example.rewards.model;


import jakarta.persistence.*;

    @Entity
    public class Transaction {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String customerId;
        private double amount;
        private String transactionDate;

        // Default constructor
        public Transaction() {}

        public Transaction(Long id, String customerId, double amount, String transactionDate) {
            this.id = id;
            this.customerId = customerId;
            this.amount = amount;
            this.transactionDate = transactionDate;
        }

        // Getters and setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getCustomerId() { return customerId; }
        public void setCustomerId(String customerId) { this.customerId = customerId; }
        public double getAmount() { return amount; }
        public void setAmount(double amount) { this.amount = amount; }
        public String getTransactionDate() { return transactionDate; }
        public void setTransactionDate(String transactionDate) { this.transactionDate = transactionDate; }
    }

