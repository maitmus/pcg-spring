package com.github.maitmus.pcgspring.parkingTransaction.v1.controller;

import com.github.maitmus.pcgspring.common.dto.CommonResponse;
import com.github.maitmus.pcgspring.parkingTransaction.v1.dto.CurrentParkingTransactionDetails;
import com.github.maitmus.pcgspring.parkingTransaction.v1.dto.ParkingTransactionDetails;
import com.github.maitmus.pcgspring.parkingTransaction.v1.service.ParkingTransactionService;
import com.github.maitmus.pcgspring.user.v1.dto.UserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "주차 내역")
@RestController
@RequestMapping("/v1/user/parking-transaction")
@RequiredArgsConstructor
public class UserParkingTransactionController {
    private final ParkingTransactionService parkingTransactionService;

    @GetMapping
    @Operation(summary = "주차 내역 조회")
    public CommonResponse<ParkingTransactionDetails> getParkingTransactions(@AuthenticationPrincipal
                                                                            UserDetails userDetails,
                                                                            @RequestParam(defaultValue = "0")
                                                                            int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "createdAt"));
        return parkingTransactionService.getParkingTransactions(userDetails, pageable);
    }

    @GetMapping("/current")
    @Operation(summary = "현재 입차중인 주차건 조회")
    public CommonResponse<CurrentParkingTransactionDetails> getCurrentParkingTransaction(
        @AuthenticationPrincipal UserDetails userDetails) {
        return parkingTransactionService.getCurrentParkingTransaction(userDetails);
    }
}
