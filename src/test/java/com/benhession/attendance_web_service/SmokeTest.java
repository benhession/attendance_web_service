package com.benhession.attendance_web_service;

import com.benhession.attendance_web_service.controllers.StudentController;
import com.benhession.attendance_web_service.controllers.UniversityClassController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SmokeTest {

	@Autowired
	private StudentController studentController;

	@Autowired
	private UniversityClassController universityClassController;

	@Test
	void contextLoads() {
		assertThat(studentController).isNotNull();
		assertThat(universityClassController).isNotNull();
	}

}
