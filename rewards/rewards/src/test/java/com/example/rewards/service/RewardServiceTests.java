package com.example.rewards.service;

import com.example.rewards.model.Customer;
import com.example.rewards.model.Transaction;
import com.example.rewards.repository.CustomerRepository;
import com.example.rewards.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RewardServiceTests {

    @InjectMocks
    private RewardService rewardService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private TransactionRepository transactionRepository;

    private Customer customer1;
    private Customer customer2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create mock customers
        customer1 = new Customer("joseph", "joseph");
        customer2 = new Customer("john", "john");
    }

    @Test
    void testGetAllCustomersRewardPoints() {
        // Set up mock data for the customer repository
        when(customerRepository.findAll()).thenReturn(Arrays.asList(customer1, customer2));

        // Mock transaction data for customer1
        LocalDate startDate = LocalDate.of(2023, 7, 1);
        LocalDate endDate = LocalDate.of(2023, 9, 30);
        List<Transaction> customer1Transactions = Arrays.asList(
                new Transaction(120, LocalDate.of(2023, 7, 10), customer1),
                new Transaction(70, LocalDate.of(2023, 8, 15), customer1),
                new Transaction(150, LocalDate.of(2023, 9, 5), customer1)
        );

        when(transactionRepository.findByCustomerAndTransactionDateBetween(customer1, startDate, endDate))
                .thenReturn(customer1Transactions);
        // Mock transaction data for customer2
        List<Transaction> customer2Transactions = Arrays.asList(
                new Transaction(60, LocalDate.of(2023, 7, 12), customer2),
                new Transaction(95, LocalDate.of(2023, 8, 22), customer2),
                new Transaction(200, LocalDate.of(2023, 9, 18), customer2)
        );

        when(transactionRepository.findByCustomerAndTransactionDateBetween(customer2, startDate, endDate))
                .thenReturn(customer2Transactions);

        // Test the method
        Map<String, Map<String, Integer>> result = rewardService.getAllCustomersRewardPoints("2023-07-01", "2023-09-30");

        // Validate result for customer1
        assertNotNull(result);
        assertEquals(2, result.size());

        Map<String, Integer> customer1Points = result.get("1");
        assertEquals(3, customer1Points.size()); // 3 months
        assertEquals(90, customer1Points.get("2023-07")); // (120 - 100) * 2 + 50
        assertEquals(20, customer1Points.get("2023-08")); // (70 - 50)
        assertEquals(150, customer1Points.get("2023-09")); // (150 - 100) * 2 + 50

        // Validate result for customer2
        Map<String, Integer> customer2Points = result.get("2");
        assertEquals(3, customer2Points.size()); // 3 months
        assertEquals(10, customer2Points.get("2023-07")); // (60 - 50)
        assertEquals(45, customer2Points.get("2023-08")); // (95 - 50)
        assertEquals(250, customer2Points.get("2023-09")); // (200 - 100) * 2 + 50
    }

    @Test
    void testGetPointsPerMonthForCustomer() {
        // Mock transaction data for customer1
        LocalDate startDate = LocalDate.of(2023, 7, 1);
        LocalDate endDate = LocalDate.of(2023, 9, 30);
        List<Transaction> transactions = Arrays.asList(
                new Transaction(120, LocalDate.of(2023, 7, 10), customer1),
                new Transaction(70, LocalDate.of(2023, 8, 15), customer1),
                new Transaction(150, LocalDate.of(2023, 9, 5), customer1)
        );

        when(transactionRepository.findByCustomerAndTransactionDateBetween(customer1, startDate, endDate))
                .thenReturn(transactions);

        // Test the method
        Map<String, Integer> result = rewardService.getPointsPerMonthForCustomer("1", "2023-07-01", "2023-09-30");

        // Validate the result
        assertNotNull(result);
        assertEquals(3, result.size()); // 3 months of data
        assertEquals(90, result.get("2023-07")); // (120 - 100) * 2 + 50
        assertEquals(20, result.get("2023-08")); // (70 - 50)
        assertEquals(150, result.get("2023-09")); // (150 - 100) * 2 + 50
    }

    @Test
    void testCalculateRewardPoints() {
        // Test various cases
        assertEquals(90, rewardService.calculateRewardPoints(120)); // (120 - 100) * 2 + 50
        assertEquals(20, rewardService.calculateRewardPoints(70)); // (70 - 50)
        assertEquals(250, rewardService.calculateRewardPoints(200)); // (200 - 100) * 2 + 50
        assertEquals(0, rewardService.calculateRewardPoints(40)); // Below 50, no points
    }

    @Test
    void testGetAllCustomersRewardPoints_NoCustomers() {
        // Set up empty customer list
        when(customerRepository.findAll()).thenReturn(Collections.emptyList());

        // Test the method
        Map<String, Map<String, Integer>> result = rewardService.getAllCustomersRewardPoints("2023-07-01", "2023-09-30");

        // Validate the result
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetPointsPerMonthForCustomer_NoTransactions() {
        // Mock no transactions for the customer
        LocalDate startDate = LocalDate.of(2023, 7, 1);
        LocalDate endDate = LocalDate.of(2023, 9, 30);

        when(transactionRepository.findByCustomerAndTransactionDateBetween(customer1, startDate, endDate))
                .thenReturn(Collections.emptyList());

        // Test the method
        Map<String, Integer> result = rewardService.getPointsPerMonthForCustomer("1", "2023-07-01", "2023-09-30");

        // Validate the result
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}