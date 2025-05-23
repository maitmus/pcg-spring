package com.github.maitmus.pcgspring.park.v1.service;

import com.github.maitmus.pcgspring.common.constant.EntityStatus;
import com.github.maitmus.pcgspring.common.dto.CommonResponse;
import com.github.maitmus.pcgspring.excpetion.BadRequestException;
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

    public CommonResponse<ParkDetails> getParksByCoordinate(Double lat, Double lon, String searchKeyword) {
        if ((lat != null && lon == null) || (lat == null && lon != null)) {
            throw new BadRequestException(
                "Latitude and longitude must be both null or both not null. Latitude: " + lat + ", Longitude: " + lon);
        }

        if (lat == null) {
            List<ParkDetail> entireParks =
                parkRepository.findAllParks(searchKeyword, EntityStatus.ACTIVE);

            return new CommonResponse<>(new ParkDetails(entireParks));
        }

        List<ParkDetail> parks =
            parkRepository.findParkByCoordinatesAndStatus(lat, lon, searchKeyword, EntityStatus.ACTIVE.name())
                .stream().map(ParkDetail::new).toList();

        return new CommonResponse<>(new ParkDetails(parks));
    }
}
