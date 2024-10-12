package acme.example.work_order;

import org.springframework.boot.SpringApplication;

public class TestWorkOrderApplication {

	public static void main(String[] args) {
		SpringApplication.from(WorkOrderApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
