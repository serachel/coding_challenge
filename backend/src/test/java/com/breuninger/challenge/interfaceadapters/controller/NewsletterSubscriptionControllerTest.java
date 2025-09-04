package com.breuninger.challenge.interfaceadapters.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.junit.jupiter.api.extension.ExtendWith;

import com.breuninger.challenge.businesslogic.NewsletterSubscriptionService;
import com.breuninger.challenge.interfaceadapters.controller.requests.CreateNewsletterSubscriptionRequestDto;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class NewsletterSubscriptionControllerTest {

    @Mock
    private NewsletterSubscriptionService newsletterSubscriptionService;

    @InjectMocks
    private NewsletterSubscriptionController classUnderTest;

    @Test
    void shouldReturn201WhenEmailIsValid() {
        // Arrange
        CreateNewsletterSubscriptionRequestDto dto = new CreateNewsletterSubscriptionRequestDto();
        dto.setEmail("test@example.com");

        doNothing().when(newsletterSubscriptionService).saveNewsletterSubscription(dto);

        ResponseEntity<Void> response = classUnderTest.createEmailSubscription(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void shouldReturn400WhenEmailIsInvalid() {
        CreateNewsletterSubscriptionRequestDto dto = new CreateNewsletterSubscriptionRequestDto();
        dto.setEmail("invalid-email");

        doThrow(new IllegalArgumentException("Invalid email format"))
                .when(newsletterSubscriptionService).saveNewsletterSubscription(dto);

        ResponseEntity<Void> response = classUnderTest.createEmailSubscription(dto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
