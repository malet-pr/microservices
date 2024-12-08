package org.acme.orders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class OrdersApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrdersApplication.class, args);
	}

}
