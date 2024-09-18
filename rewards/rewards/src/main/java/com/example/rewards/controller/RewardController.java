package com.example.rewards.controller;

import com.example.rewards.service.RewardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RewardController {

    private final RewardService rewardService;

    public RewardController(RewardService rewardService) {
        this.rewardService = rewardService;
    }
    @GetMapping("/rewards")
    public int getRewards(
            @RequestParam String customerId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        return rewardService.getTotalPointsForCustomer(customerId, startDate, endDate);
    }
}
