package com.caffeincache.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class HibernateTest1Application {

	public static void main(String[] args) {
		SpringApplication.run(HibernateTest1Application.class, args);
	}
}
