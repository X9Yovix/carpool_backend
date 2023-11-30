package com.tekup.carpool_backend.repository.ride;

import com.tekup.carpool_backend.model.ride.Ride;
import com.tekup.carpool_backend.model.ride.RideStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RideRepository extends JpaRepository<Ride, Long> {
    List<Ride> findByDriverId(Long driverId);

    @Query("SELECT r FROM Ride r " +
            "WHERE (COALESCE(:departure, '') = '' OR LOWER(r.departureLocation) LIKE LOWER(CONCAT('%', :departure, '%'))) " +
            "AND (COALESCE(:destination, '') = '' OR LOWER(r.destinationLocation) LIKE LOWER(CONCAT('%', :destination, '%'))) " +
            "AND (:status IS NULL OR r.status = :status) " +
            "AND (:minPrice IS NULL OR r.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR r.price <= :maxPrice)")
    Page<Ride> findByFilters(@Param("departure") String departure,
                             @Param("destination") String destination,
                             @Param("status") RideStatus status,
                             @Param("minPrice") Double minPrice,
                             @Param("maxPrice") Double maxPrice,
                             Pageable pageable);

    Page<Ride> findAllByOrderByDepartureDateDesc(Pageable pageable);
}
