package com.example.aidemo.config;

import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import com.example.aidemo.service.RectangleAreaService;

@Configuration
public class Functions {
	
	@Bean
	@Description("Calculate the area of a rectangle from its base and height")
	public Function<RectangleAreaService.Request, RectangleAreaService.Response> rectangeleAreaFunction() {
		return new RectangleAreaService();
	}
	

}