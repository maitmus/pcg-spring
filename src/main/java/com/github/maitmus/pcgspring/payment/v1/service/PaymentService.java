package com.github.maitmus.pcgspring.payment.v1.service;

import com.github.maitmus.pcgspring.card.v1.dto.PortOneGeneralResponse;
import com.github.maitmus.pcgspring.card.v1.entity.Card;
import com.github.maitmus.pcgspring.card.v1.repository.CardRepository;
import com.github.maitmus.pcgspring.common.constant.EntityStatus;
import com.github.maitmus.pcgspring.common.dto.CommonResponse;
import com.github.maitmus.pcgspring.encryptor.AesEncryptor;
import com.github.maitmus.pcgspring.excpetion.BadRequestException;
import com.github.maitmus.pcgspring.excpetion.NotFoundException;
import com.github.maitmus.pcgspring.parkingTransaction.v1.entity.ParkingTransaction;
import com.github.maitmus.pcgspring.parkingTransaction.v1.repository.ParkingTransactionRepository;
import com.github.maitmus.pcgspring.payment.v1.dto.PayRequest;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final UserService userService;
    private final CardRepository cardRepository;
    private final WebclientService webclientService;
    private final PortOneService portOneService;
    private final AesEncryptor aesEncryptor;
    private final ParkingTransactionRepository parkingTransactionRepository;

    @Value("${portone.api.url}")
    private String billingApiUrl;

    @Value("${parking.fee.per.minute}")
    private int parkingFeePerMinute;

    @Value("${charging.fee.per.second}")
    private int chargingFeePerSecond;

    public CommonResponse<?> pay(UserDetails userDetails, @Valid PayRequest request) {
        User user = userService.findByIdOrElseThrow(userDetails.getId());

        Card representativeCard = cardRepository.findOneByUserAndStatusAndIsRepresentativeIsTrue(user, EntityStatus.ACTIVE)
                .orElseThrow(() -> new NotFoundException("Representative Card not found"));

        String decryptedRepresentativeCustomerId;

        try {
            decryptedRepresentativeCustomerId = aesEncryptor.decrypt(representativeCard.getCustomerId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String accessToken = portOneService.getAccessToken();

        ParkingTransaction transaction = parkingTransactionRepository.findByPaymentIdAndStatusAndIsPaidIsFalse(
                request.getPaymentId(),
                EntityStatus.ACTIVE
        ).orElseThrow(() -> new BadRequestException("Parking Transaction not found, paymentId: " + request.getPaymentId()));

        int parkingAmount = transaction.getCurrentParkingAmount(parkingFeePerMinute);
        int totalAmount = transaction.getCurrentChargeAmount(chargingFeePerSecond) + parkingAmount;

        if (totalAmount < 100) {
            transaction.setBypassTransaction();
            parkingTransactionRepository.save(transaction);

            return new CommonResponse<>();
        }

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("customer_uid", decryptedRepresentativeCustomerId);
        formData.add("merchant_uid", transaction.getPaymentId().toString());
        formData.add("currency", "KRW");
        formData.add("amount", String.valueOf(totalAmount));
        formData.add("name", "Test");
        formData.add("buyer_name", user.getName());
        formData.add("product_type", "digital");

        PortOneGeneralResponse<?> response =  webclientService.sendFormDataRequest(
                HttpMethod.POST,
                billingApiUrl + "/subscribe/payments/again",
                formData,
                PortOneGeneralResponse.class,
                accessToken
        );

        if (response.getCode() != 0) {
            throw new RuntimeException("Payment failed, message: " + response.getMessage());
        }

        transaction.completePayment(totalAmount, parkingAmount);
        parkingTransactionRepository.save(transaction);

        return new CommonResponse<>();
    }
}
