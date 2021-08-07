package com.benhession.attendance_web_service.data;

import com.benhession.attendance_web_service.model.StudentUniversityClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentUniversityClassService {
    private final StudentUniversityClassRepository repository;

    @Autowired
    public StudentUniversityClassService(StudentUniversityClassRepository repository) {
        this.repository = repository;
    }

    public StudentUniversityClass save(StudentUniversityClass theClass) {
        return repository.save(theClass);
    }

    public Optional<StudentUniversityClass> findStudentClassByIds(String studentId, String classId) {
        return repository.findStudentUniversityClassByStudent_StudentIdAndUniversityClass_ClassId(studentId, classId);
    }
}
