package com.breuninger.challenge.interfaceadapters.controller;

import com.breuninger.challenge.interfaceadapters.controller.requests.CreateNewsletterSubscriptionRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class NewsletterSubscriptionControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createEmailSubscription_shouldReturn201_whenRequestIsValid() throws Exception {
        mockMvc.perform(post("/newsletter-subscription")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@example.com\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void createEmailSubscription_shouldReturn400_whenRequestHasInvalidEmail() throws Exception {
        mockMvc.perform(post("/newsletter-subscription")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"invalid-email\"}"))
                .andExpect(status().isBadRequest());
    }
}
