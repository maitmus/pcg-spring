package com.github.maitmus.pcgspring.user.service;

import com.github.maitmus.pcgspring.common.constant.EntityStatus;
import com.github.maitmus.pcgspring.common.constant.TokenType;
import com.github.maitmus.pcgspring.excpetion.NotFoundException;
import com.github.maitmus.pcgspring.token.service.TokenService;
import com.github.maitmus.pcgspring.user.dto.UpdateUserRequest;
import com.github.maitmus.pcgspring.user.dto.UpdateUserResult;
import com.github.maitmus.pcgspring.user.entity.User;
import com.github.maitmus.pcgspring.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final TokenService tokenService;

    @Transactional
    public UpdateUserResult updateUser(Long id, UpdateUserRequest request) {
        User user = findByIdOrElseThrow(id);

        user.update(request.getNickname(), request.getEmail());
        User updatedUser = userRepository.save(user);

        String accessToken = tokenService.generateToken(updatedUser, TokenType.ACCESS);

        return new UpdateUserResult(updatedUser.getId(), accessToken);
    }

    @Transactional(readOnly = true)
    public User findByIdOrElseThrow(Long id) {
        return userRepository.findByIdAndStatus(id, EntityStatus.ACTIVE)
                .orElseThrow(() -> new NotFoundException("User not found, id: " + id));
    }
}
