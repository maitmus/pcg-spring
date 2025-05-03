package com.github.maitmus.pcgspring.park.v1.service;

import com.github.maitmus.pcgspring.common.dto.CommonResponse;
import com.github.maitmus.pcgspring.park.v1.dto.CreateParkRequest;
import com.github.maitmus.pcgspring.park.v1.entity.Park;
import com.github.maitmus.pcgspring.park.v1.repository.ParkRepository;
import com.github.maitmus.pcgspring.user.v1.dto.UserDetails;
import com.github.maitmus.pcgspring.user.v1.entity.User;
import com.github.maitmus.pcgspring.user.v1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ParkService {
    private final UserService userService;
    private final ParkRepository parkRepository;

    @Transactional
    public CommonResponse<?> createPark(CreateParkRequest request, UserDetails userDetails) {
        User owner = userService.findByIdOrElseThrow(userDetails.getId());

        Park newPark = new Park(
                request.getName(),
                request.getPhone(),
                request.getAddress(),
                request.getDisabilitySpace(),
                request.getManageCode(),
                request.getLat(),
                request.getLon(),
                request.getIp(),
                owner
        );

        parkRepository.save(newPark);

        return new CommonResponse<>(HttpStatus.ACCEPTED, null);
    }
}
