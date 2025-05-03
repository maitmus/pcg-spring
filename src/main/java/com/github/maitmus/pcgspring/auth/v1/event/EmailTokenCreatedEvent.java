package com.github.maitmus.pcgspring.auth.v1.event;

import com.github.maitmus.pcgspring.auth.v1.entity.EmailToken;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class EmailTokenCreatedEvent extends ApplicationEvent {
    private final EmailToken token;
    private final String rawToken;

    public EmailTokenCreatedEvent(EmailToken token, String rawToken) {
        super(token);
        this.token = token;
        this.rawToken = rawToken;
    }
}
