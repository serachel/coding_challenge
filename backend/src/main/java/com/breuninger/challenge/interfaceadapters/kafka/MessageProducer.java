package com.breuninger.challenge.interfaceadapters.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.breuninger.challenge.domainmodel.NewsletterSubscription;

@Component
public class MessageProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    
    @Value("${spring.values.topic-name}")
    private String topic;

    public void sendNewsletterMessage(NewsletterSubscription newsletterSubscription) {
        kafkaTemplate.send(topic, newsletterSubscription);
    }

}
