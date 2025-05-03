package com.github.maitmus.pcgspring.card.v1.service;

import com.github.maitmus.pcgspring.card.v1.dto.*;
import com.github.maitmus.pcgspring.card.v1.entity.Card;
import com.github.maitmus.pcgspring.card.v1.repository.CardRepository;
import com.github.maitmus.pcgspring.common.constant.EntityStatus;
import com.github.maitmus.pcgspring.common.dto.CommonResponse;
import com.github.maitmus.pcgspring.encryptor.AesEncryptor;
import com.github.maitmus.pcgspring.excpetion.BadRequestException;
import com.github.maitmus.pcgspring.excpetion.NotFoundException;
import com.github.maitmus.pcgspring.portOne.service.PortOneService;
import com.github.maitmus.pcgspring.user.v1.dto.UserDetails;
import com.github.maitmus.pcgspring.user.v1.entity.User;
import com.github.maitmus.pcgspring.user.v1.service.UserService;
import com.github.maitmus.pcgspring.webclient.service.WebclientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final WebclientService webclientService;
    private final AesEncryptor aesEncryptor;
    private final PortOneService portOneService;
    private final UserService userService;

    @Value("${portone.api.url}")
    private String billingApiUrl;

    @Transactional
    public CommonResponse<?> createCard(@Valid CreateCardRequest request, UserDetails userDetails) {
        User user = userService.findByIdOrElseThrow(userDetails.getId());

        String formattedCardNumber = formatCardNumber(request.getCardNumber());

        String accessToken = portOneService.getAccessToken();

        UUID customerUid = UUID.randomUUID();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("card_number", formattedCardNumber);
        formData.add("pg", "ksnet");
        formData.add("expiry", String.format("%s-%s", request.getExpiryYear(), request.getExpiryMonth()));
        formData.add("birth", user.getBirth().format(formatter));
        formData.add("pwd_2digit", request.getPasswordFirstTwoDigits());

        PortOneGeneralResponse<?> response = webclientService.sendFormDataRequest(
                HttpMethod.POST,
                billingApiUrl + "/subscribe/customers/" + customerUid,
                formData,
                PortOneGeneralResponse.class,
                accessToken
        );

        if (response.getCode() != 0) {
            throw new RuntimeException("Error subscribing to billing api: " + response.getMessage());
        }

        try {
            String encryptedCustomerUid = aesEncryptor.encrypt(customerUid.toString());

            Card card = new Card(
                    encryptedCustomerUid,
                    request.getIsRepresentative(),
                    request.getNickname(),
                    user
            );

            if (request.getIsRepresentative()) {
                Card existedCard = cardRepository.findByIsRepresentativeAndUserAndStatus(
                        true,
                        user,
                        EntityStatus.ACTIVE
                ).orElse(null);

                if (existedCard != null) {
                    existedCard.unsetRepresentative();
                    cardRepository.save(existedCard);
                }
            }

            cardRepository.save(card);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new CommonResponse<>();
    }

    @Transactional(readOnly = true)
    public CommonResponse<CardDetails> getCards(UserDetails userDetails) {
        User user = userService.findByIdOrElseThrow(userDetails.getId());

        List<Card> cards = cardRepository.findByUserAndStatusOrderByIsRepresentativeDesc(user, EntityStatus.ACTIVE);

        cards.forEach(card -> {
            try {
                card.setDecryptedCustomerId(aesEncryptor.decrypt(card.getCustomerId()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        List<CardDetail> cardDetails = cards.stream().map(CardDetail::new).toList();
        return new CommonResponse<>(new CardDetails(cardDetails));
    }

    private String formatCardNumber(String rawCardNumber) {
        return rawCardNumber.replaceAll("(\\d{4})(?=\\d)", "$1-").replaceAll("-$", "");
    }

    @Transactional
    public CommonResponse<?> updateCardRepresentative(@Valid UpdateCardRepresentativeRequest request, UserDetails userDetails) {
        User user = userService.findByIdOrElseThrow(userDetails.getId());

        List<Card> cards = cardRepository.findByUserAndStatus(user, EntityStatus.ACTIVE);

        cards.forEach(card -> {
            try {
                card.setDecryptedCustomerId(aesEncryptor.decrypt(card.getCustomerId()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Optional<Card> targetCard = cards.stream().filter(card -> card.getCustomerId().equals(request.getCustomerId())).findFirst();

        if (targetCard.isEmpty()) {
            throw new BadRequestException("Card not found, id: " + request.getCustomerId());
        }

        cards.forEach(card -> {
            if (!card.getCustomerId().equals(targetCard.get().getCustomerId())) {
                card.unsetRepresentative();
            }
            else {
                card.setRepresentative();
            }
        });

        cards.forEach(card -> {
            try {
                card.setDecryptedCustomerId(aesEncryptor.encrypt(card.getCustomerId()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        cardRepository.saveAll(cards);
        return new CommonResponse<>();
    }

    @Transactional
    public CommonResponse<DeleteCardResponse> deleteCard(Long id, UserDetails userDetails) {
        User user = userService.findByIdOrElseThrow(userDetails.getId());
        Card card = cardRepository.findByIdAndUserAndStatus(id, user, EntityStatus.ACTIVE)
                .orElseThrow(() -> new NotFoundException("Card not found, id: " + id));

        card.delete();
        cardRepository.save(card);

        return new CommonResponse<>(new DeleteCardResponse(card.getId()));
    }
}
