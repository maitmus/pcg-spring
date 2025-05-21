package com.github.maitmus.pcgspring.parkingTransaction.v1.controller;

import com.github.maitmus.pcgspring.common.dto.CommonResponse;
import com.github.maitmus.pcgspring.parkingTransaction.v1.dto.CreateParkingTransactionResponse;
import com.github.maitmus.pcgspring.parkingTransaction.v1.dto.ParkingTransactionDetail;
import com.github.maitmus.pcgspring.parkingTransaction.v1.dto.ParkingTransactionRequest;
import com.github.maitmus.pcgspring.parkingTransaction.v1.service.ParkingTransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "주차 내역")
@RestController
@RequestMapping("/v1/parking-transaction")
@RequiredArgsConstructor
public class ParkingTransactionController {
    private final ParkingTransactionService parkingTransactionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "차량 입차")
    public CommonResponse<CreateParkingTransactionResponse> createParkingTransaction(
        @RequestBody @Valid ParkingTransactionRequest request,
        @RequestHeader(name = "manage-code") String manageCode) {
        return parkingTransactionService.createParkingTransaction(request, manageCode);
    }

    @PostMapping("/exit")
    @Operation(summary = "차량 출차")
    public CommonResponse<?> exitParkingTransaction(@RequestBody @Valid ParkingTransactionRequest request,
                                                    @RequestHeader(name = "manage-code") String manageCode) {
        return parkingTransactionService.exitParkingTransaction(request, manageCode);
    }

    @PatchMapping("/charge-start")
    @Operation(summary = "충전 시작")
    public CommonResponse<?> startCharge(@RequestBody @Valid ParkingTransactionRequest request,
                                         @RequestHeader(name = "manage-code") String manageCode) {
        return parkingTransactionService.startCharge(request, manageCode);
    }

    @PatchMapping("/charge-finish")
    @Operation(summary = "충전 완료")
    public CommonResponse<?> finishCharge(@RequestBody @Valid ParkingTransactionRequest request,
                                          @RequestHeader(name = "manage-code") String manageCode) {
        return parkingTransactionService.finishCharge(request, manageCode);
    }

    @GetMapping("/unpaid")
    @Operation(summary = "결제 미납건 조회(주차장)")
    public CommonResponse<ParkingTransactionDetail> getUnpaidParkingTransaction(
        @RequestHeader(name = "manage-code") String manageCode,
        @RequestParam String carNumber) {
        return parkingTransactionService.getUnpaidParkingTransaction(manageCode, carNumber);
    }
}
