package com.github.maitmus.pcgspring.user.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserResult {
    private Long id;
    private String accessToken;
}
