package org.acme.work_order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestWorkOrderApplication {

	public static void main(String[] args) {
		SpringApplication.from(WorkOrderApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
