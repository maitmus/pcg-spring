package com.github.maitmus.pcgspring.parkingTransaction.v1.controller;

import com.github.maitmus.pcgspring.common.dto.CommonResponse;
import com.github.maitmus.pcgspring.parkingTransaction.v1.dto.CurrentParkingTransactionDetails;
import com.github.maitmus.pcgspring.parkingTransaction.v1.dto.ParkingTransactionDetails;
import com.github.maitmus.pcgspring.parkingTransaction.v1.service.ParkingTransactionService;
import com.github.maitmus.pcgspring.user.v1.dto.UserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user/parking-transaction")
@RequiredArgsConstructor
public class UserParkingTransactionController {
    private final ParkingTransactionService parkingTransactionService;

    @GetMapping
    public CommonResponse<ParkingTransactionDetails> getParkingTransactions(@AuthenticationPrincipal
                                                                            UserDetails userDetails,
                                                                            @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "createdAt"));
        return parkingTransactionService.getParkingTransactions(userDetails, pageable);
    }

    @GetMapping("/current")
    public CommonResponse<CurrentParkingTransactionDetails> getCurrentParkingTransaction(@AuthenticationPrincipal UserDetails userDetails) {
        return parkingTransactionService.getCurrentParkingTransaction(userDetails);
    }
}
