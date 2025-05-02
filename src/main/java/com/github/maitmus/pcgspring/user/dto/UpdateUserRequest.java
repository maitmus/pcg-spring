package com.github.maitmus.pcgspring.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateUserRequest {
    String nickname;
    String email;
}
