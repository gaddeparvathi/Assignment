package com.example.rewards.service;

import com.example.rewards.model.Transaction;
import com.example.rewards.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class RewardServiceTests {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private RewardService rewardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCalculatePoints() {
        // Test points calculation for various amounts
        assertEquals(70, rewardService.calculatePoints(120));
        assertEquals(30, rewardService.calculatePoints(80));
        assertEquals(0, rewardService.calculatePoints(40));
        assertEquals(50, rewardService.calculatePoints(100));
    }

    @Test
    void testGetTotalPointsForCustomer() {
        // Prepare sample data
        List<Transaction> transactions = Arrays.asList(
                new Transaction(1L, "cust1", 120, "2024-08-15"),
                new Transaction(2L, "cust1", 80, "2024-08-20")
        );

        // Mock repository call
        when(transactionRepository.findByCustomerIdAndTransactionDateBetween("cust1", "2024-08-01", "2024-08-31"))
                .thenReturn(transactions);

        // Calculate expected points: (120 - 100) * 2 + (80 - 50) = 40 + 30 = 70
        int totalPoints = rewardService.getTotalPointsForCustomer("cust1", "2024-08-01", "2024-08-31");

        // Verify the result
        assertEquals(70, totalPoints);
    }
}
