package com.breuninger.challenge.businesslogic;

import com.breuninger.challenge.domainmodel.NewsletterSubscription;
import com.breuninger.challenge.interfaceadapters.controller.requests.CreateNewsletterSubscriptionRequestDto;
import com.breuninger.challenge.interfaceadapters.kafka.MessageProducer;
import com.breuninger.challenge.interfaceadapters.repository.NewsletterSubscriptionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

import org.modelmapper.ModelMapper;

@Service
public class NewsletterSubscriptionService {

    @Autowired
    private NewsletterSubscriptionRepository newsletterSubscriptionRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MessageProducer messageProducer;

    private static final String EMAIL_REGEX = "^((?:[A-Za-z0-9!#$%&'*+\\-\\/=?^_`{|}~]|(?<=^|\\.)\"|\"(?=$|\\.|@)|(?<=\".*)[ .](?=.*\")|(?<!\\.)\\.){1,64})(@)((?:[A-Za-z0-9.\\-])*(?:[A-Za-z0-9])\\.(?:[A-Za-z0-9]){2,})$";

    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);

    private static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public void saveNewsletterSubscription(
            CreateNewsletterSubscriptionRequestDto createNewsletterSubscriptionRequestDto) {
        if (isValidEmail(createNewsletterSubscriptionRequestDto.getEmail())) {
            NewsletterSubscription newsletterSubscription = modelMapper.map(createNewsletterSubscriptionRequestDto,
                    NewsletterSubscription.class);

            newsletterSubscriptionRepository.save(newsletterSubscription);
            messageProducer.sendNewsletterMessage( newsletterSubscription);
            System.err.println("Message sent to Kafka: " + newsletterSubscription.getEmail());
        } else {

            throw new IllegalArgumentException("Invalid email format");
        }
    }
}