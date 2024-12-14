package org.parkTub.carcountproducer.parkImageInfo;

import lombok.RequiredArgsConstructor;
import org.parkTub.carcountproducer.parking.CarCountProducer;
import org.parkTub.carcountproducer.parking.CarCountRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParkImageInfoService {

    private final ParkImageInfoRepository parkImageInfoRepository;
    private final CarCountProducer carCountProducer;

    public void registerParkImageInfo(ParkImageInfoRequest request) {
        Long parkImageInfoId = parkImageInfoRepository.save(
                ParkImageInfo.builder()
                        .carCount(request.carCount())
                        .timestamp(request.timestamp())
                        .parkingLotId(request.parkingLotId())
                        .build()
        ).getId();

        carCountProducer.sendCarCount(
                new CarCountRequest(
                        parkImageInfoId,
                        request.carCount(),
                        request.timestamp(),
                        request.parkingLotId()
                )
        );
    }
}
