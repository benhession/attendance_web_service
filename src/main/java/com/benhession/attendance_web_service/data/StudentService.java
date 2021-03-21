package com.benhession.attendance_web_service.data;

import com.benhession.attendance_web_service.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class StudentService {

    final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Set<Student> findAllStudents() {

        Set<Student> students = new HashSet<>();

        Iterable<Student> studentIterable = studentRepository.findAll();

        studentIterable.forEach(students::add);

        return students;
    }
}
