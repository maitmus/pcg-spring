package com.github.maitmus.pcgspring.auth.v1.controller;

import com.github.maitmus.pcgspring.auth.v1.dto.*;
import com.github.maitmus.pcgspring.auth.v1.service.AuthService;
import com.github.maitmus.pcgspring.common.constant.TokenType;
import com.github.maitmus.pcgspring.common.dto.CommonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponse<CreateUserResponse> register(@RequestBody @Valid CreateUserRequest request) {
        return new CommonResponse<>(HttpStatus.CREATED, authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponse<LoginResponse>> login(@RequestBody @Valid LoginRequest request) {
        LoginResponse response = authService.login(request);

        ResponseCookie refreshTokenCookie = ResponseCookie.from(TokenType.REFRESH.getValue(), response.getRefreshToken())
                .httpOnly(true)
                .sameSite("Lax")
                .path("/")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(new CommonResponse<>(response));
    }

    @GetMapping("/check-username-duplicate/{username}")
    public CommonResponse<?> checkUsernameDuplicate(@PathVariable String username) {
        authService.checkUsernameDuplicate(username);
        return new CommonResponse<>();
    }

    @PostMapping("/find-username")
    public CommonResponse<FindUsernameResponse> findUsername(@RequestBody @Valid FindUsernameRequest request) {
        return new CommonResponse<>(authService.findUsername(request));
    }

    @PostMapping("/send-password-reset-email")
    public CommonResponse<?> createPasswordResetEmailToken(@RequestBody @Valid ResetPasswordEmailRequest request) {
        authService.createPasswordResetEmailToken(request);
        return new CommonResponse<>();
    }

    @PatchMapping("/reset-password")
    public CommonResponse<?> resetPassword(@RequestBody @Valid ResetPasswordRequest request) {
        authService.resetPassword(request);
        return new CommonResponse<>();
    }
}
