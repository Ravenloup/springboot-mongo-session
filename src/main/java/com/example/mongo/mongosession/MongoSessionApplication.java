package com.example.mongo.mongosession;

import java.time.Duration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.session.data.mongo.JdkMongoSessionConverter;
import org.springframework.session.data.mongo.config.annotation.web.http.EnableMongoHttpSession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableMongoHttpSession
public class MongoSessionApplication {

	@Value("${timeout}")
	private int timeout;
	
	public static void main(String[] args) {
		SpringApplication.run(MongoSessionApplication.class, args);
	}

	@Bean
	public JdkMongoSessionConverter jdkMongoSessionConverter() {
		return new JdkMongoSessionConverter(Duration.ofMinutes(timeout));
	}
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String testSession(HttpServletRequest request) {
		return request.getSession(true).getId();
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public boolean logout(HttpSession session) {
		if (!session.isNew()) {
			session.invalidate();
			return true;
		}
		return false;
	}
}
