package com.breuninger.challenge.interfaceadapters.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.breuninger.challenge.businesslogic.NewsletterSubscriptionService;
import com.breuninger.challenge.interfaceadapters.controller.requests.CreateNewsletterSubscriptionRequestDto;

import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.breuninger.challenge.domainmodel.NewsletterSubscription;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/newsletter-subscription")
public class NewsletterSubscriptionController {

    @Autowired
    private NewsletterSubscriptionService newsletterSubscriptionService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "400", description = "Invalid email format")
    })
    @PostMapping
    public ResponseEntity<Void> createEmailSubscription(
            @RequestBody CreateNewsletterSubscriptionRequestDto createNewsletterSubscriptionRequestDto) {
        try {
            newsletterSubscriptionService.saveNewsletterSubscription(createNewsletterSubscriptionRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}