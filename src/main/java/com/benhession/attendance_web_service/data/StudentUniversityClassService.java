package com.benhession.attendance_web_service.data;

import com.benhession.attendance_web_service.model.StudentUniversityClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentUniversityClassService {
    private final StudentUniversityClassRepository repository;

    @Autowired
    public StudentUniversityClassService(StudentUniversityClassRepository repository) {
        this.repository = repository;
    }

    public boolean save(StudentUniversityClass theClass) {
        return repository.save(theClass).getAttended();
    }
}
