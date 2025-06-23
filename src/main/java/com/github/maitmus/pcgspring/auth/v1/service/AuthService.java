package com.github.maitmus.pcgspring.auth.v1.service;

import com.github.maitmus.pcgspring.auth.v1.dto.CreateUserRequest;
import com.github.maitmus.pcgspring.auth.v1.dto.CreateUserResponse;
import com.github.maitmus.pcgspring.auth.v1.dto.FindUsernameRequest;
import com.github.maitmus.pcgspring.auth.v1.dto.FindUsernameResponse;
import com.github.maitmus.pcgspring.auth.v1.dto.LoginRequest;
import com.github.maitmus.pcgspring.auth.v1.dto.LoginResponse;
import com.github.maitmus.pcgspring.auth.v1.dto.ResetPasswordEmailRequest;
import com.github.maitmus.pcgspring.auth.v1.dto.ResetPasswordRequest;
import com.github.maitmus.pcgspring.auth.v1.entity.EmailToken;
import com.github.maitmus.pcgspring.auth.v1.event.EmailTokenCreatedEvent;
import com.github.maitmus.pcgspring.auth.v1.repository.AuthRepository;
import com.github.maitmus.pcgspring.auth.v1.repository.EmailTokenRepository;
import com.github.maitmus.pcgspring.common.constant.EntityStatus;
import com.github.maitmus.pcgspring.common.constant.TokenType;
import com.github.maitmus.pcgspring.excpetion.BadRequestException;
import com.github.maitmus.pcgspring.excpetion.NotFoundException;
import com.github.maitmus.pcgspring.token.service.TokenService;
import com.github.maitmus.pcgspring.user.v1.entity.User;
import com.github.maitmus.pcgspring.user.v1.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static java.util.UUID.randomUUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final AuthRepository authRepository;
    private final TokenService tokenService;
    private final EmailTokenRepository emailTokenRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final UserRepository userRepository;

    @Transactional
    public CreateUserResponse register(@Valid CreateUserRequest request) {
        String password = hashPassword(request.getPassword());
        request.setHashedPassword(password);

        User user = new User(
            request.getName(),
            request.getNickname(),
            request.getEmail(),
            request.getUsername(),
            request.getPassword(),
            request.getBirth()
        );

        User newUser = authRepository.save(user);

        return new CreateUserResponse(newUser.getId());
    }

    private String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Transactional(readOnly = true)
    public LoginResponse login(@Valid LoginRequest request) {
        User user = authRepository.findByUsernameAndStatus(request.getUsername(), EntityStatus.ACTIVE)
            .orElse(null);

        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("아이디나 비밀번호가 일치하지 않습니다.");
        }

        String accessToken = tokenService.generateToken(user, TokenType.ACCESS);
        String refreshToken = tokenService.generateToken(user, TokenType.REFRESH);

        return LoginResponse.builder()
            .name(user.getName())
            .nickname(user.getNickname())
            .username(user.getUsername())
            .email(user.getEmail())
            .birth(user.getBirth())
            .createdAt(user.getCreatedAt())
            .updatedAt(user.getUpdatedAt())
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }

    @Transactional(readOnly = true)
    public void checkUsernameDuplicate(String username) {
        if (authRepository.existsByUsername(username)) {
            throw new BadRequestException("아이디가 이미 존재합니다.");
        }
    }

    @Transactional(readOnly = true)
    public FindUsernameResponse findUsername(@Valid FindUsernameRequest request) {
        User user =
            authRepository.findByNameAndEmailAndStatus(request.getName(), request.getEmail(), EntityStatus.ACTIVE)
                .orElseThrow(() -> new NotFoundException(
                    "사용자를 찾을 수 없습니다. 이름: " + request.getName() + ", 이메일: " + request.getEmail()));
        return new FindUsernameResponse(user.getUsername());
    }

    @Transactional
    public void createPasswordResetEmailToken(@Valid ResetPasswordEmailRequest request) {
        User user = authRepository.findByEmailAndUsernameAndStatus(request.getEmail(), request.getUsername(),
                EntityStatus.ACTIVE)
            .orElseThrow(() -> new NotFoundException(
                "사용자를 찾을 수 없습니다. 이메일: " + request.getEmail() + ", 아이디: " + request.getUsername()));

        String tokenString = randomUUID()
            .toString()
            .substring(0, 6)
            .toUpperCase();

        EmailToken token = new EmailToken(passwordEncoder.encode(tokenString), user);

        EmailToken newEmailToken = emailTokenRepository.save(token);

        applicationEventPublisher.publishEvent(
            new EmailTokenCreatedEvent(newEmailToken, tokenString)
        );
    }

    @Transactional
    public void resetPassword(@Valid ResetPasswordRequest request) {
        User user = userRepository.findByUsernameAndStatus(request.getUsername(), EntityStatus.ACTIVE)
            .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다. 아이디: " + request.getUsername()));
        LocalDateTime threshold = LocalDateTime.now().minusMinutes(5);

        EmailToken token = emailTokenRepository.findByUserAndStatus(user, EntityStatus.ACTIVE)
            .orElseThrow(() -> new NotFoundException("잘못된 인증 코드입니다."));

        if (!passwordEncoder.matches(request.getToken(), token.getToken())) {
            throw new BadRequestException("잘못된 인증 코드입니다.");
        }

        if (token.getCreatedAt().isBefore(threshold)) {
            emailTokenRepository.delete(token);
            throw new BadRequestException("인증 토큰이 만료되었습니다.");
        }

        String hashedNewPassword = hashPassword(request.getPassword());
        user.setHashedPassword(hashedNewPassword);

        authRepository.save(user);
        emailTokenRepository.delete(token);
    }
}
