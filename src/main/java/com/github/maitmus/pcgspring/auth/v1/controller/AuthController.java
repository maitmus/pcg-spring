package com.github.maitmus.pcgspring.auth.v1.controller;

import com.github.maitmus.pcgspring.auth.v1.dto.CreateUserRequest;
import com.github.maitmus.pcgspring.auth.v1.dto.CreateUserResponse;
import com.github.maitmus.pcgspring.auth.v1.dto.FindUsernameRequest;
import com.github.maitmus.pcgspring.auth.v1.dto.FindUsernameResponse;
import com.github.maitmus.pcgspring.auth.v1.dto.LoginRequest;
import com.github.maitmus.pcgspring.auth.v1.dto.LoginResponse;
import com.github.maitmus.pcgspring.auth.v1.dto.ResetPasswordEmailRequest;
import com.github.maitmus.pcgspring.auth.v1.dto.ResetPasswordRequest;
import com.github.maitmus.pcgspring.auth.v1.service.AuthService;
import com.github.maitmus.pcgspring.common.constant.TokenType;
import com.github.maitmus.pcgspring.common.dto.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "인증/인가")
@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "회원가입")
    public CommonResponse<CreateUserResponse> register(@RequestBody @Valid CreateUserRequest request) {
        return new CommonResponse<>(HttpStatus.CREATED, authService.register(request));
    }

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "리프레시 토큰은 쿠키로 발급, 액세스 토큰은 Body에 발급")
    public ResponseEntity<CommonResponse<LoginResponse>> login(@RequestBody @Valid LoginRequest request) {
        LoginResponse response = authService.login(request);

        ResponseCookie refreshTokenCookie =
            ResponseCookie.from(TokenType.REFRESH.getValue(), response.getRefreshToken())
                .httpOnly(true)
                .sameSite("Lax")
                .path("/")
                .build();

        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
            .body(new CommonResponse<>(response));
    }

    @GetMapping("/check-username-duplicate/{username}")
    @Operation(summary = "아이디 중복 체크")
    public CommonResponse<?> checkUsernameDuplicate(@PathVariable String username) {
        authService.checkUsernameDuplicate(username);
        return new CommonResponse<>();
    }

    @PostMapping("/find-username")
    @Operation(summary = "아이디 찾기")
    public CommonResponse<FindUsernameResponse> findUsername(@RequestBody @Valid FindUsernameRequest request) {
        return new CommonResponse<>(authService.findUsername(request));
    }

    @PostMapping("/send-password-reset-email")
    @Operation(summary = "비밀번호 재설정 이메일 전송")
    public CommonResponse<?> createPasswordResetEmailToken(@RequestBody @Valid ResetPasswordEmailRequest request) {
        authService.createPasswordResetEmailToken(request);
        return new CommonResponse<>();
    }

    @PatchMapping("/reset-password")
    @Operation(summary = "비밀번호 재설정", description = "재설정 토큰은 5분 후 만료")
    public CommonResponse<?> resetPassword(@RequestBody @Valid ResetPasswordRequest request) {
        authService.resetPassword(request);
        return new CommonResponse<>();
    }
}
