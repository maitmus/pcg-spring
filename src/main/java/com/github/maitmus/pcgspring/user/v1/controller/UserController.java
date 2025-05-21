package com.github.maitmus.pcgspring.user.v1.controller;

import com.github.maitmus.pcgspring.common.dto.CommonResponse;
import com.github.maitmus.pcgspring.user.v1.dto.UpdateUserRequest;
import com.github.maitmus.pcgspring.user.v1.dto.UpdateUserResult;
import com.github.maitmus.pcgspring.user.v1.dto.UserDetails;
import com.github.maitmus.pcgspring.user.v1.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "사용자")
@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    @Operation(summary = "사용자 정보 조회")
    public CommonResponse<UserDetails> getUser(@AuthenticationPrincipal UserDetails userDetails) {
        return new CommonResponse<>(userDetails);
    }

    @PatchMapping
    @Operation(summary = "사용자 정보 수정")
    public CommonResponse<UpdateUserResult> updateUser(
        @RequestBody UpdateUserRequest request,
        @AuthenticationPrincipal UserDetails userDetails
    ) {
        return new CommonResponse<>(userService.updateUser(userDetails.getId(), request));
    }

}
