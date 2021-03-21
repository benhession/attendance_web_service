package com.benhession.attendance_web_service.controllers;

import com.benhession.attendance_web_service.data.StudentService;
import com.benhession.attendance_web_service.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;


@RestController
@RequestMapping(path = "/student", produces = "application/json")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public Set<Student> findAllStudents() {

        return studentService.findAllStudents();
    }


}
