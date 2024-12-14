package org.parkTub.parking.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.parkTub.parking.parkingLot.ParkingLotService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CarCountConsumer {

    private final ParkingLotService parkingLotService;

    @KafkaListener(topics = "car-count-topic")
    public void consumeCarCount(CarCountConfirmation carCountConfirmation) throws MessagingException {
        log.info("Consuming the message from car-count-topic. Topic: {}", carCountConfirmation);
        parkingLotService.updateAvailableSpots(carCountConfirmation);
    }
}