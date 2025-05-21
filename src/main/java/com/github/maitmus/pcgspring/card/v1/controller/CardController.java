package com.github.maitmus.pcgspring.card.v1.controller;

import com.github.maitmus.pcgspring.card.v1.dto.CardDetails;
import com.github.maitmus.pcgspring.card.v1.dto.CreateCardRequest;
import com.github.maitmus.pcgspring.card.v1.dto.UpdateCardRepresentativeRequest;
import com.github.maitmus.pcgspring.card.v1.service.CardService;
import com.github.maitmus.pcgspring.common.dto.CommonResponse;
import com.github.maitmus.pcgspring.user.v1.dto.UserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "카드")
@RestController
@RequestMapping("/v1/card")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "카드 생성")
    public CommonResponse<?> createCard(@RequestBody @Valid CreateCardRequest request,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        return cardService.createCard(request, userDetails);
    }

    @GetMapping
    @Operation(summary = "카드 조회")
    public CommonResponse<CardDetails> getCards(@AuthenticationPrincipal UserDetails userDetails) {
        return cardService.getCards(userDetails);
    }

    @PatchMapping("/representative")
    @Operation(summary = "대표 카드 설정", description = "카드 등록은 복수매 가능하지만, 결제는 대표 카드로 진행")
    public CommonResponse<?> updateCardRepresentative(@AuthenticationPrincipal UserDetails userDetails,
                                                      @RequestBody @Valid UpdateCardRepresentativeRequest request) {
        return cardService.updateCardRepresentative(request, userDetails);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "카드 삭제")
    public CommonResponse<?> deleteCard(@PathVariable Long id,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        return cardService.deleteCard(id, userDetails);
    }
}
