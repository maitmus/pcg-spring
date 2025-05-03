package com.github.maitmus.pcgspring.user.v1.controller;

import com.github.maitmus.pcgspring.common.dto.CommonResponse;
import com.github.maitmus.pcgspring.user.v1.dto.UpdateUserRequest;
import com.github.maitmus.pcgspring.user.v1.dto.UpdateUserResult;
import com.github.maitmus.pcgspring.user.v1.dto.UserDetails;
import com.github.maitmus.pcgspring.user.v1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public CommonResponse<UserDetails> getUser(@AuthenticationPrincipal UserDetails userDetails) {
        return new CommonResponse<>(userDetails);
    }

    @PatchMapping
    public CommonResponse<UpdateUserResult> updateUser(
            @RequestBody UpdateUserRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return new CommonResponse<>(userService.updateUser(userDetails.getId(), request));
    }

}
