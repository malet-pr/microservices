package acme.example.work_order;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class WorkOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkOrderApplication.class, args);
	}

}
