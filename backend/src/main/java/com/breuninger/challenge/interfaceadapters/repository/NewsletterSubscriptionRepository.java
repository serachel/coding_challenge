package com.breuninger.challenge.interfaceadapters.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.breuninger.challenge.domainmodel.NewsletterSubscription;

@Repository
public interface NewsletterSubscriptionRepository extends JpaRepository<NewsletterSubscription,String>{

}
