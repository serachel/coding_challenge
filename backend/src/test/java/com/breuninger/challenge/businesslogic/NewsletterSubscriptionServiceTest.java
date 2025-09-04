package com.breuninger.challenge.businesslogic;

import com.breuninger.challenge.domainmodel.NewsletterSubscription;
import com.breuninger.challenge.interfaceadapters.controller.requests.CreateNewsletterSubscriptionRequestDto;
import com.breuninger.challenge.interfaceadapters.kafka.MessageProducer;
import com.breuninger.challenge.interfaceadapters.repository.NewsletterSubscriptionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class NewsletterSubscriptionServiceTest {

    @Mock
    private NewsletterSubscriptionRepository newsletterSubscriptionRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private MessageProducer messageProducer;

    @InjectMocks
    private NewsletterSubscriptionService classUnderTest;    

    @Test
    @DisplayName("Should successfully save email when format is valid")
    void shouldSuccessfullySaveEmail() {
        var dto = new CreateNewsletterSubscriptionRequestDto();
        dto.setEmail("test@test.de");
        var newsletterSubscriptionEntity = new NewsletterSubscription();
        newsletterSubscriptionEntity.setEmail("test@test.de");  
    
        when(modelMapper.map(any(CreateNewsletterSubscriptionRequestDto.class), eq(NewsletterSubscription.class)))
                .thenReturn(newsletterSubscriptionEntity);

        classUnderTest.saveNewsletterSubscription(dto);
        verify(newsletterSubscriptionRepository, times(1)).save(newsletterSubscriptionEntity);  
       
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when email format is invalid")
    void shouldThrowIllegalArgumentExceptionWhenEmailFormatIsInvalid() {
        var dto = new CreateNewsletterSubscriptionRequestDto();
        dto.setEmail("invalidemail.format");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            classUnderTest.saveNewsletterSubscription(dto);
        });

        assertEquals("Invalid email format", exception.getMessage());
        verify(newsletterSubscriptionRepository, never()).save(any(NewsletterSubscription.class));
    }   
}
