package com.breuninger.challenge.interfaceadapters.kafka;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import com.breuninger.challenge.domainmodel.NewsletterSubscription;

@ExtendWith(MockitoExtension.class)
class MessageProducerTest {
    @Value("${spring.values.topic-name}") private String topic;
    // Mock des KafkaTemplate reicht aus, da die Klasse nur send() aufruft
    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    // InjectMocks sorgt dafür, dass der Mock automatisch in die Klasse injiziert
    // wird
    @InjectMocks
    private MessageProducer messageProducer;

    @Test
    void shouldSendNewsletterMessage() {
        // given: ein Testobjekt

        

        NewsletterSubscription subscription = new NewsletterSubscription();
        subscription.setEmail("test@test.de");

        // when: Methode aufrufen
        messageProducer.sendNewsletterMessage(subscription);

        // then: prüfen, dass KafkaTemplate.send() genau einmal aufgerufen wurde
        // So testest du die Logik ohne echten Kafka-Broker
        verify(kafkaTemplate, times(1)).send(topic,subscription);
    }
}
