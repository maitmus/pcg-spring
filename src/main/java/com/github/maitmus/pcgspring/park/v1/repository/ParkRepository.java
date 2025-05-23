package com.github.maitmus.pcgspring.park.v1.repository;

import com.github.maitmus.pcgspring.common.constant.EntityStatus;
import com.github.maitmus.pcgspring.park.v1.dto.ParkDetail;
import com.github.maitmus.pcgspring.park.v1.entity.Park;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ParkRepository extends JpaRepository<Park, Long> {
    Optional<Park> findByManageCodeAndStatus(String manageCode, EntityStatus status);

    @Query(value = """
        SELECT * FROM parks p WHERE
        (:searchKeyword IS NULL OR
        p.name LIKE %:searchKeyword% OR
        p.phone LIKE %:searchKeyword% OR
        p.address LIKE %:searchKeyword%) AND
        ST_DWithin(
        geography(ST_MakePoint(p.lon, p.lat)),
        geography(ST_MakePoint(:lon, :lat)),
        5000
        ) AND p.status = :status
        """, nativeQuery = true)
    List<Park> findParkByCoordinatesAndStatus(@Param("lat") Double lat, @Param("lon") Double lon,
                                              @Param("searchKeyword") String searchKeyword,
                                              @Param("status") String status);

    @Query("""
        SELECT p from Park p WHERE
        (:searchKeyword IS NULL OR
        p.name LIKE %:searchKeyword% OR
        p.phone LIKE %:searchKeyword% OR
        p.address LIKE %:searchKeyword%)
        AND p.status = :status
        """)
    List<ParkDetail> findAllParks(@Param("searchKeyword") String searchKeyword, @Param("status") EntityStatus status);

    List<Park> findByStatus(EntityStatus status);
}
