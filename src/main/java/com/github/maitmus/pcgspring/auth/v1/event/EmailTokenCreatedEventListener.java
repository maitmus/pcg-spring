package com.github.maitmus.pcgspring.auth.v1.event;

import com.github.maitmus.pcgspring.auth.v1.entity.EmailToken;
import jakarta.persistence.PostPersist;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class EmailTokenCreatedEventListener {
    private static ApplicationEventPublisher publisher;

    public EmailTokenCreatedEventListener(ApplicationEventPublisher publisher) {
        EmailTokenCreatedEventListener.publisher = publisher;
    }

    @PostPersist
    public void onEmailTokenCreated(EmailToken token) {
        publisher.publishEvent(new EmailTokenCreatedEvent(token));
    }
}
