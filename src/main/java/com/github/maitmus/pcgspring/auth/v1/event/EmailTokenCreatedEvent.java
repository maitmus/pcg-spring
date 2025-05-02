package com.github.maitmus.pcgspring.auth.v1.event;

import com.github.maitmus.pcgspring.auth.v1.entity.EmailToken;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class EmailTokenCreatedEvent extends ApplicationEvent {
    private final EmailToken token;

    public EmailTokenCreatedEvent(EmailToken token) {
        super(token);
        this.token = token;
    }
}
