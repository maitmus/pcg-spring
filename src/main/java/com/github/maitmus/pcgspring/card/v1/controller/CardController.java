package com.github.maitmus.pcgspring.card.v1.controller;

import com.github.maitmus.pcgspring.card.v1.dto.CardDetails;
import com.github.maitmus.pcgspring.card.v1.dto.CreateCardRequest;
import com.github.maitmus.pcgspring.card.v1.dto.UpdateCardRepresentativeRequest;
import com.github.maitmus.pcgspring.card.v1.service.CardService;
import com.github.maitmus.pcgspring.common.dto.CommonResponse;
import com.github.maitmus.pcgspring.user.dto.UserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/card")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponse<?> createCard(@RequestBody @Valid CreateCardRequest request,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        return cardService.createCard(request, userDetails);
    }

    @GetMapping
    public CommonResponse<CardDetails> getCards(@AuthenticationPrincipal UserDetails userDetails) {
        return cardService.getCards(userDetails);
    }

    @PatchMapping("/representative")
    public CommonResponse<?> updateCardRepresentative(@AuthenticationPrincipal UserDetails userDetails,
                                                      @RequestBody @Valid UpdateCardRepresentativeRequest request) {
        return cardService.updateCardRepresentative(request, userDetails);
    }
}
