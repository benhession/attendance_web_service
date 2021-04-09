package com.benhession.attendance_web_service;

import com.benhession.attendance_web_service.controllers.HomeController;
import com.benhession.attendance_web_service.controllers.StudentController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SmokeTest {

	@Autowired
	private HomeController homeController;

	@Autowired
	private StudentController studentController;

	@Test
	void contextLoads() {
		assertThat(homeController).isNotNull();
		assertThat(studentController).isNotNull();
	}

}
