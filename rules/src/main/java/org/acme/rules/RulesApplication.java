package org.acme.rules;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories(basePackages = {"org.acme.rules.drools.internal.repository",
        "org.acme.rules.workorder.internal.repository"})
@Configuration
public class RulesApplication {
	public static void main(String[] args) {
		SpringApplication.run(RulesApplication.class, args);
	}
}

