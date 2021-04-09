package com.benhession.attendance_web_service.controllers;

import com.benhession.attendance_web_service.data.StudentService;
import com.benhession.attendance_web_service.model.Student;
import com.benhession.attendance_web_service.model.StudentUniversityClass;
import com.benhession.attendance_web_service.representational_models.StudentUniversityClassModel;
import com.benhession.attendance_web_service.representational_models.StudentUniversityClassModelAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;
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

    @GetMapping("/classes")
    public ResponseEntity<Set<StudentUniversityClassModel>> getClassesOfStudent(Principal principal) {

        String studentId = principal.getName();

        if (studentId == null) {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }

        Optional<Set<StudentUniversityClass>> studentClasses =
                Optional.ofNullable(studentService.universityClassesOfRequester(studentId));

        return studentClasses.map(
                studentUniversityClasses ->
                        new ResponseEntity<>(StudentUniversityClassModelAssembler
                                .toCollection(studentUniversityClasses) , HttpStatus.OK)
        ).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NO_CONTENT));



    }


}
