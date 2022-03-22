package org.alberto.papRec2022;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class PapRecuperacionApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(PapRecuperacionApplication.class, args);
	}

}
