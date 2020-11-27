package com.mata.taskforce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.mata.taskforce.filters.AuthFilter;

@SpringBootApplication
public class TaskforceApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskforceApiApplication.class, args);
	}
	
	@Bean
	public FilterRegistrationBean<AuthFilter> filterRegistrationBean() {
		FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
		AuthFilter authFilter = new AuthFilter();
		registrationBean.setFilter(authFilter);
		registrationBean.addUrlPatterns("/api/tasks/*");
		return registrationBean;
	}
}