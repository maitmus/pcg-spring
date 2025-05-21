package com.github.maitmus.pcgspring.park.v1.repository;

import com.github.maitmus.pcgspring.common.constant.EntityStatus;
import com.github.maitmus.pcgspring.park.v1.entity.Park;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ParkRepository extends JpaRepository<Park, Long> {
    Optional<Park> findByManageCodeAndStatus(String manageCode, EntityStatus status);

    @Query(value = """
        SELECT * from parks p WHERE ST_DWithin(
        geography(ST_MakePoint(p.lon, p.lat)),
        geography(ST_MakePoint(:lon, :lat)),
        5000
        ) AND p.status = :status
        """, nativeQuery = true)
    List<Park> findByCoordinatesAndStatus(Double lat, Double lon, String status);
}
