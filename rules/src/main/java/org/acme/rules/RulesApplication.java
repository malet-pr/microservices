package org.acme.rules;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories(basePackages = "org.acme.rules.drools.internal.repository")
@EntityScan(basePackages = "org.acme.rules.drools.internal.model")
public class RulesApplication {
	public static void main(String[] args) {
		SpringApplication.run(RulesApplication.class, args);
	}
}
