package com.example.rewards.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import com.example.rewards.controller.RewardController;
import com.example.rewards.service.RewardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(RewardController.class)
public class RewardControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RewardService rewardService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCustomerRewardPoints() throws Exception {
        String customerId = "1";
        Map<String, Integer> mockRewardPoints = new HashMap<>();
        mockRewardPoints.put("2024-07-01", 120);
        mockRewardPoints.put("2024-08-01", 150);
        mockRewardPoints.put("2024-09-01", 10);

        // Mock the rewardService behavior
        when(rewardService.getPointsPerMonthForCustomer(customerId, "2024-07-01", "2024-09-30"))
                .thenReturn(mockRewardPoints);

        // Perform a GET request to /api/reward-reports/1
        mockMvc.perform(get("/api/reward-reports/{customerId}", customerId)
                        .param("startDate", "2024-07-01")
                        .param("endDate", "2024-09-30"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.['2024-07-01']").value(120))
                .andExpect(jsonPath("$.['2024-08-01']").value(150))
                .andExpect(jsonPath("$.['2024-09-01']").value(10));
    }

    @Test
    public void testGetAllCustomerRewardPoints() throws Exception {
        Map<String, Integer> mockRewardPointsForCustomer1 = new HashMap<>();
        mockRewardPointsForCustomer1.put("2024-07-01", 120);
        mockRewardPointsForCustomer1.put("2024-08-01", 150);
        mockRewardPointsForCustomer1.put("2024-09-01", 10);

        Map<String, Integer> mockRewardPointsForCustomer2 = new HashMap<>();
        mockRewardPointsForCustomer2.put("2024-07-01", 200);
        mockRewardPointsForCustomer2.put("2024-08-01", 40);
        mockRewardPointsForCustomer2.put("2024-09-01", 0);

        Map<String, Map<String, Integer>> mockAllCustomerRewards = new HashMap<>();
        mockAllCustomerRewards.put("1", mockRewardPointsForCustomer1);
        mockAllCustomerRewards.put("2", mockRewardPointsForCustomer2);

        // Mock the rewardService behavior
        when(rewardService.getAllCustomersRewardPoints("2024-07-01", "2024-09-30"))
                .thenReturn(mockAllCustomerRewards);

        // Perform a GET request to /api/reward-reports
        mockMvc.perform(get("/api/reward-reports")
                        .param("startDate", "2024-07-01")
                        .param("endDate", "2024-09-30"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.['1']['2024-07-01']").value(120))
                .andExpect(jsonPath("$.['1']['2024-08-01']").value(150))
                .andExpect(jsonPath("$.['1']['2024-09-01']").value(10))
                .andExpect(jsonPath("$.['2']['2024-07-01']").value(200))
                .andExpect(jsonPath("$.['2']['2024-08-01']").value(40))
                .andExpect(jsonPath("$.['2']['2024-09-01']").value(0));
    }

    @Test
    public void testCustomerNotFound() throws Exception {
        String customerId = "999";

        // Mock the rewardService behavior for a non-existent customer
        when(rewardService.getPointsPerMonthForCustomer(customerId, "2024-07-01", "2024-09-30"))
                .thenReturn(new HashMap<>());

        // Perform a GET request and expect an empty response
        mockMvc.perform(get("/api/reward-reports/{customerId}", customerId)
                        .param("startDate", "2024-07-01")
                        .param("endDate", "2024-09-30"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
}