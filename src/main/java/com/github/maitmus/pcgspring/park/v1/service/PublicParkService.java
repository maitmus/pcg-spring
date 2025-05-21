package com.github.maitmus.pcgspring.park.v1.service;

import com.github.maitmus.pcgspring.common.constant.EntityStatus;
import com.github.maitmus.pcgspring.common.dto.CommonResponse;
import com.github.maitmus.pcgspring.park.v1.dto.ParkDetail;
import com.github.maitmus.pcgspring.park.v1.dto.ParkDetails;
import com.github.maitmus.pcgspring.park.v1.repository.ParkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicParkService {
    private final ParkRepository parkRepository;

    public CommonResponse<ParkDetails> getParksByCoordinate(Double lat, Double lon) {
        List<ParkDetail> parks = parkRepository.findByCoordinatesAndStatus(lat, lon, EntityStatus.ACTIVE.name())
            .stream().map(ParkDetail::new).toList();

        return new CommonResponse<>(new ParkDetails(parks));
    }
}
