package com.github.maitmus.pcgspring.user.v1.dto;


import com.github.maitmus.pcgspring.common.constant.EntityStatus;
import com.github.maitmus.pcgspring.common.constant.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails {
    private Long id;
    @Schema(description = "실명")
    private String name;
    @Schema(description = "닉네임")
    private String nickname;
    @Schema(description = "Email")
    private String email;
    @Schema(description = "유저 아이디")
    private String username;
    @Schema(description = "생년월일")
    private LocalDate birth;
    @Schema(description = "역할")
    private List<Role> roles;
    @Schema(description = "계정 활성화 여부")
    private EntityStatus status;
    @Schema(description = "가입일자")
    private LocalDateTime createdAt;
    @Schema(description = "수정일자")
    private LocalDateTime updatedAt;

//    public UserDetails(User user) {
//        this.id = user.getId();
//        this.name = user.getName();
//        this.nickname = user.getNickname();
//        this.email = user.getEmail();
//        this.username = user.getUsername();
//        this.roles = user.getRoles();
//        this.status = user.getStatus();
//        this.createdAt = user.getCreatedAt();
//        this.updatedAt = user.getUpdatedAt();
//    }
}
