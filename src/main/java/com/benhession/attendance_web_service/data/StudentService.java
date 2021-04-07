package com.benhession.attendance_web_service.data;

import com.benhession.attendance_web_service.model.Student;
import com.benhession.attendance_web_service.model.StudentUniversityClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class StudentService {

    final StudentRepository studentRepository;
    final StudentUniversityClassRepository universityClassRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository, StudentUniversityClassRepository universityClassRepository) {
        this.studentRepository = studentRepository;
        this.universityClassRepository = universityClassRepository;
    }

    public Set<StudentUniversityClass> universityClassesOfRequester(String studentId) {
        Set<StudentUniversityClass> classes =
                universityClassRepository.findStudentUniversityClassByStudent_StudentId(studentId);

        return classes.isEmpty() ? null : classes;
    }

    public Set<Student> findAllStudents() {

        Set<Student> students = new HashSet<>();

        Iterable<Student> studentIterable = studentRepository.findAll();

        studentIterable.forEach(students::add);

        return students;
    }
}
