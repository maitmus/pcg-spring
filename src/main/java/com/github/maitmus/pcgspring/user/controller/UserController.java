package com.github.maitmus.pcgspring.user.controller;

import com.github.maitmus.pcgspring.common.dto.CommonResponse;
import com.github.maitmus.pcgspring.user.dto.UpdateUserRequest;
import com.github.maitmus.pcgspring.user.dto.UpdateUserResult;
import com.github.maitmus.pcgspring.user.dto.UserDetails;
import com.github.maitmus.pcgspring.user.service.UserService;
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
