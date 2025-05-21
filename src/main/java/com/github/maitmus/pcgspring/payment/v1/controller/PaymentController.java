package com.github.maitmus.pcgspring.payment.v1.controller;

import com.github.maitmus.pcgspring.common.dto.CommonResponse;
import com.github.maitmus.pcgspring.payment.v1.dto.PayRequest;
import com.github.maitmus.pcgspring.payment.v1.service.PaymentService;
import com.github.maitmus.pcgspring.user.v1.dto.UserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "결제")
@RestController
@RequestMapping("/v1/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping
    @Operation(summary = "결제", description = "결제는 대표 카드로 진행")
    public CommonResponse<?> pay(@AuthenticationPrincipal UserDetails userDetails,
                                 @RequestBody @Valid PayRequest request) {
        return paymentService.pay(userDetails, request);
    }
}
