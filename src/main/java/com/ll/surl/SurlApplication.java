package com.ll.surl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SurlApplication {

	public static void main(String[] args) {
		SpringApplication.run(SurlApplication.class, args);
	}

}
