package com.benhession.attendance_web_service.data;

import com.benhession.attendance_web_service.model.UniversityClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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

    // util method for testing
    public Set<UniversityClass> findAll() {

        Set<UniversityClass> universityClasses = new HashSet<>();
        Iterable<UniversityClass> classIterable = classRepository.findAll();

        classIterable.forEach(universityClasses::add);

        return universityClasses;
    }

    // util method for testing
    public boolean saveAll(Set<UniversityClass> classSet) {
        Iterable<UniversityClass> classIterable = classRepository.saveAll(classSet);
        Set<UniversityClass> newClassSet = new HashSet<>();

        classIterable.forEach(newClassSet::add);

        return newClassSet.equals(classSet);
    }

}
