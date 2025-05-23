package com.github.maitmus.pcgspring.park.v1.service;

import com.github.maitmus.pcgspring.common.constant.EntityStatus;
import com.github.maitmus.pcgspring.common.dto.CommonResponse;
import com.github.maitmus.pcgspring.park.v1.dto.ParkDetail;
import com.github.maitmus.pcgspring.park.v1.repository.ParkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminParkService {
    private final ParkRepository parkRepository;

    public CommonResponse<?> getParkRegistrationRequests() {
        List<ParkDetail> parkRegistrationRequests = parkRepository.findByStatus(EntityStatus.INACTIVE)
            .stream().map(ParkDetail::new).toList();

        return new CommonResponse<>(parkRegistrationRequests);
    }
}
