package org.acme.simulator;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.modulith.core.ApplicationModules;

@SpringBootTest
class SimulatorApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void createApplicationModuleModel() {
		ApplicationModules modules = ApplicationModules.of(SimulatorApplication.class);
		modules.forEach(System.out::println);
	}


}
