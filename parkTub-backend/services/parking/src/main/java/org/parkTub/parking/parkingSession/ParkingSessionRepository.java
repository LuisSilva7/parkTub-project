package org.parkTub.parking.parkingSession;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ParkingSessionRepository extends JpaRepository<ParkingSession, Long> {

    @Query("""
            SELECT ps
            FROM ParkingSession ps
            WHERE ps.isActive = true
            AND ps.customerId = :customerId
            """)
    Optional<ParkingSession> findActiveParkingSession(Long customerId);

    @Query("""
            SELECT ps
            FROM ParkingSession ps
            WHERE ps.customerId = :customerId
            """)
    List<ParkingSession> findAllByCustomerId(Long customerId);
}
