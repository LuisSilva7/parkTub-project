package org.parkTub.carcountproducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CarCountProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarCountProducerApplication.class, args);
	}

}
