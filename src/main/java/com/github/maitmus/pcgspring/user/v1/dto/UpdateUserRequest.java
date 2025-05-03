package com.github.maitmus.pcgspring.user.v1.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateUserRequest {
    String nickname;
    String email;
}
