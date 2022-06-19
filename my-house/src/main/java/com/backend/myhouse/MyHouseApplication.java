package com.backend.myhouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class MyHouseApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyHouseApplication.class, args);
	}

}
