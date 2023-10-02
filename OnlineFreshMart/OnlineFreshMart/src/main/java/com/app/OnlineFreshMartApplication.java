package com.app;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication //main entry point,application start
public class OnlineFreshMartApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineFreshMartApplication.class, args);//code tells spring boot to run the application
		//class contains the main method
	}

	//labeling something as special so that spring knows to take care of it.
	@Bean //to define a spring bean,its an object spring manages,used throughout application
	public ModelMapper mapper() {
		ModelMapper modelMapper = new ModelMapper(); //Its a library used for mapping data from one object to another
		//creating a tool that helps you convert information from one form to another.
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);//modelmapper to be very strict and precise when its changing information from one shape to another.
		//no mistake allowed.
		return modelMapper;
	}
}
// this code sets up a Spring Boot application, defines a ModelMapper Bean with strict property matching configuration, and starts the application when you run it. 
//It's a simple starting point for a Java application that can map data between different parts of your application with a high degree of precision.
