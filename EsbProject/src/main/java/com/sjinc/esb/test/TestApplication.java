package com.sjinc.esb.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({ "classpath:spring/camel-context.xml" })
public class TestApplication {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(TestApplication.class, args) ;
	}

}
