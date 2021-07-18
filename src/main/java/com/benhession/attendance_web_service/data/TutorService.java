package com.benhession.attendance_web_service.data;

import com.benhession.attendance_web_service.model.Tutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TutorService {

    private final TutorRepository repository;

    @Autowired
    public TutorService(TutorRepository repository) {
        this.repository = repository;
    }

    public Optional<Tutor> findById(String tutorId) {
        return repository.findById(tutorId);
    }
}
