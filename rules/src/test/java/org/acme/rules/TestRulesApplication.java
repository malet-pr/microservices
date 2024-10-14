package org.acme.rules;

import org.springframework.boot.SpringApplication;

public class TestRulesApplication {

	public static void main(String[] args) {
		SpringApplication.from(RulesApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
