package com.github.maitmus.pcgspring.auth.v1.event;

import com.github.maitmus.pcgspring.auth.v1.entity.EmailToken;
import com.github.maitmus.pcgspring.mail.service.MailService;
import com.github.maitmus.pcgspring.user.v1.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailTokenCreatedEventHandler {
    private final MailService mailService;

    @EventListener
    public void onEmailTokenCreated(EmailTokenCreatedEvent event) {
        EmailToken token = event.getToken();
        String rawToken = event.getRawToken();

        User user = token.getUser();
        String email = user.getEmail();

        mailService.sendMail(email, "비밀번호 재설정", String.format("인증 코드는 %s입니다.", rawToken));
    }
}
