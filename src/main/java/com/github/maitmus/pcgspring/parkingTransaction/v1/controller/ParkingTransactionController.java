package com.github.maitmus.pcgspring.parkingTransaction.v1.controller;

import com.github.maitmus.pcgspring.common.dto.CommonResponse;
import com.github.maitmus.pcgspring.parkingTransaction.v1.dto.*;
import com.github.maitmus.pcgspring.parkingTransaction.v1.service.ParkingTransactionService;
import com.github.maitmus.pcgspring.user.dto.UserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/parking-transaction")
@RequiredArgsConstructor
public class ParkingTransactionController {
    private final ParkingTransactionService parkingTransactionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponse<CreateParkingTransactionResponse> createParkingTransaction(@RequestBody @Valid ParkingTransactionRequest request,
                                                   @RequestHeader(name = "manage-code") String manageCode) {
        return parkingTransactionService.createParkingTransaction(request, manageCode);
    }

    @PostMapping("/exit")
    public CommonResponse<?> exitParkingTransaction(@RequestBody @Valid ParkingTransactionRequest request, @RequestHeader(name = "manage-code") String manageCode) {
        return parkingTransactionService.exitParkingTransaction(request, manageCode);
    }

    @PatchMapping("/charge-start")
    public CommonResponse<?> startCharge(@RequestBody @Valid ParkingTransactionRequest request,
                                         @RequestHeader(name = "manage-code") String manageCode) {
        return parkingTransactionService.startCharge(request, manageCode);
    }

    @PatchMapping("/charge-finish")
    public CommonResponse<?> finishCharge(@RequestBody @Valid ParkingTransactionRequest request, @RequestHeader(name = "manage-code") String manageCode) {
        return parkingTransactionService.finishCharge(request, manageCode);
    }

    @GetMapping
    public CommonResponse<ParkingTransactionDetails> getParkingTransactions(@AuthenticationPrincipal UserDetails userDetails,
                                                                            @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "createdAt"));
        return parkingTransactionService.getParkingTransactions(userDetails, pageable);
    }

    @GetMapping("/current")
    public CommonResponse<CurrentParkingTransactionDetails> getCurrentParkingTransaction(@AuthenticationPrincipal UserDetails userDetails) {
        return parkingTransactionService.getCurrentParkingTransaction(userDetails);
    }
}
