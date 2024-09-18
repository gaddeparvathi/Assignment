package javacom.example.rewards.controller;

import com.example.rewards.controller.RewardController;
import com.example.rewards.service.RewardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.is;

@WebMvcTest(RewardController.class)
public class RewardControllerTests {

    private MockMvc mockMvc;

    @MockBean
    private RewardService rewardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new RewardController(rewardService)).build();
    }

    @Test
    void testGetRewards() throws Exception {
        // Mock service call
        when(rewardService.getTotalPointsForCustomer("cust1", "2024-08-01", "2024-08-31")).thenReturn(70);

        // Perform request and verify response
        mockMvc.perform(get("/rewards")
                        .param("customerId", "cust1")
                        .param("startDate", "2024-08-01")
                        .param("endDate", "2024-08-31"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.points", is(70)));
    }
}
