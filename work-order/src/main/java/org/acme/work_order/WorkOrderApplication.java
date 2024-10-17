package org.acme.work_order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class WorkOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkOrderApplication.class, args);
	}

}
