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
    public void saveAll(Set<UniversityClass> classSet) {
         classRepository.saveAll(classSet);
    }

    public boolean classHasTutor(String classId, String tutorId) {
        Optional<UniversityClass> theClass = classRepository.findById(classId);

        if (theClass.isEmpty()) {
            return false;
        } else {
            return theClass.get().getTutor().getTutorId().equals(tutorId);
        }
    }

}
