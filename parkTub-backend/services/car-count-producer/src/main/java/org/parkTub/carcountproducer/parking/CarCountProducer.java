package org.parkTub.carcountproducer.parking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import static org.springframework.kafka.support.KafkaHeaders.TOPIC;

@Service
@RequiredArgsConstructor
@Slf4j
public class CarCountProducer {

    private final KafkaTemplate<String, CarCountRequest> kafkaTemplate;

    public void sendCarCount(CarCountRequest request) {
        log.info("Sending car count with body = < {} >", request);
        Message<CarCountRequest> message = MessageBuilder
                .withPayload(request)
                .setHeader(TOPIC, "car-count-topic")
                .build();

        kafkaTemplate.send(message);
    }
}