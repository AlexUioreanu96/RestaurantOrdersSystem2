package com.example.RestaurantOrdersSystem2;

import com.example.RestaurantOrdersSystem2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class RestaurantOrdersSystem2Application {

	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		ConfigurableApplicationContext configurableApplicationContext =
				SpringApplication.run(RestaurantOrdersSystem2Application.class, args);
		UserService userService = configurableApplicationContext.getBean(UserService.class);
		userService.createDefaultUser();
	}

}
