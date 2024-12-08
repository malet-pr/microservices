package java.org.acme.orders;

import org.springframework.boot.SpringApplication;

public class TestWorkOrderApplication {

	public static void main(String[] args) {
		SpringApplication.from(WorkOrderApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
