package org.parkTub.parking.parkingLot;

import lombok.RequiredArgsConstructor;
import org.parkTub.parking.exception.custom.ParkingLotNotFoundException;
import org.parkTub.parking.kafka.CarCountConfirmation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParkingLotService {

    private final ParkingLotRepository parkingLotRepository;
    private final ParkingLotMapper parkingLotMapper;

    public boolean isParkingLotRepositoryEmpty() {
        return parkingLotRepository.count() == 0;
    }

    public void createDefaultParkingLot(ParkingLot parkingLot) {
        parkingLotRepository.save(parkingLot);
    }

    public List<ParkingLotResponse> findAll() {
        List<ParkingLot> parkingLots = parkingLotRepository.findAll();

        if (parkingLots.isEmpty()) {
            throw new ParkingLotNotFoundException("No parking lots were found!");
        }

        return parkingLots.stream()
                .map(parkingLotMapper::toParkingLotResponse)
                .toList();
    }

    public ParkingLot findByName(String name) {
        return parkingLotRepository.findByName(name).orElseThrow(() -> new ParkingLotNotFoundException(
                "Parking lot with name: " + name + " was not found!"));
    }

    public void updateAvailableSpots(CarCountConfirmation carCountConfirmation) {
        ParkingLot parkingLot = parkingLotRepository.findById(carCountConfirmation.parkingLotId())
                .orElseThrow(() -> new ParkingLotNotFoundException(
                        "Parking lot with ID: " + carCountConfirmation.parkingLotId() + " was not found!"));

        parkingLot.setAvailableSpots(parkingLot.getTotalSpots() - carCountConfirmation.carCount());
        parkingLotRepository.save(parkingLot);
    }
}
