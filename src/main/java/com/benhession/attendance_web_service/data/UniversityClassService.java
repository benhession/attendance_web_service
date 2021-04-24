package com.benhession.attendance_web_service.data;

import com.benhession.attendance_web_service.model.UniversityClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UniversityClassService {

    private final UniversityClassRepository classRepository;

    @Autowired
    public UniversityClassService(UniversityClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    public Optional<UniversityClass> findUniversityClassById(String classId) {

        return classRepository.findById(classId);
    }
}
