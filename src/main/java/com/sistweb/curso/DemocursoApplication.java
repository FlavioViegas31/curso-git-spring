package com.sistweb.curso;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemocursoApplication implements CommandLineRunner {
	
	
	public static void main(String[] args) {
		SpringApplication.run(DemocursoApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		
	}
	
	@PostConstruct
    void started() {
      TimeZone.setDefault(TimeZone.getTimeZone("TimeZone"));
    }

}
