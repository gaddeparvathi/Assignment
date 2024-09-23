package com.example.rewards.controller;

import com.example.rewards.model.Customer;
import com.example.rewards.repository.CustomerRepository;
import com.example.rewards.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;


@RestController
@RequestMapping("/reward-reports")
public class RewardController {

    @Autowired
    private RewardService rewardService;

    @Autowired
    private CustomerRepository customerRepository;

    // 1. Get Reward Points per Month for a Specific Customer
    @GetMapping("/customer/{customerId}/monthly")
    public ResponseEntity<Map<String, Object>> getCustomerMonthlyRewardReport(
            @PathVariable String customerId,
            @RequestParam String startDate,
            @RequestParam String endDate) {

        // Fetch customer details to include in the response
        Customer customer = customerRepository.findById(Long.parseLong(customerId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        // Get the reward points broken down by month
        Map<String, Integer> pointsPerMonth = rewardService.getPointsPerMonthForCustomer(customerId, startDate, endDate);

        // Construct the response object
        Map<String, Object> response = new HashMap<>();
        response.put(customer.getUsername(), pointsPerMonth);

        return ResponseEntity.ok(response);
    }

    // 2. Get Reward Points per Month for All Customers
    @GetMapping("/all/monthly")
    public ResponseEntity<Map<String, Map<String, Integer>>> getAllCustomersMonthlyRewardReports(
            @RequestParam String startDate,
            @RequestParam String endDate) {

        List<Customer> customers = customerRepository.findAll();
        Map<String, Map<String, Integer>> customerReports = new HashMap<>();

        for (Customer customer : customers) {
            Map<String, Integer> pointsPerMonth = rewardService.getPointsPerMonthForCustomer(String.valueOf(customer.getId()), startDate, endDate);
            customerReports.put( customer.getUsername(), pointsPerMonth);
        }

        return ResponseEntity.ok(customerReports);
    }
}