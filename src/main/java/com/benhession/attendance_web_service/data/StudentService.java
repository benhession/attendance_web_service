package com.benhession.attendance_web_service.data;

import com.benhession.attendance_web_service.model.Student;
import com.benhession.attendance_web_service.model.StudentUniversityClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentUniversityClassRepository universityClassRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository, StudentUniversityClassRepository universityClassRepository) {
        this.studentRepository = studentRepository;
        this.universityClassRepository = universityClassRepository;
    }

    public Set<StudentUniversityClass> studentClassesOfRequester(String studentId) {
        Set<StudentUniversityClass> classes =
                universityClassRepository.findStudentUniversityClassByStudent_StudentId(studentId);

        return classes.isEmpty() ? null : classes;
    }

    public Optional<StudentUniversityClass> studentClassByQRString(String qrString, String studentId) {
        return universityClassRepository.findStudentUniversityClassByUniversityClass_QrStringAndStudent_StudentId(
                qrString, studentId);
    }

    public Boolean existsById(String studentId) {
        return studentRepository.existsById(studentId);
    }

    public Set<Student> findAllStudents() {

        Set<Student> students = new HashSet<>();

        Iterable<Student> studentIterable = studentRepository.findAll();

        studentIterable.forEach(students::add);

        return students;
    }
}
