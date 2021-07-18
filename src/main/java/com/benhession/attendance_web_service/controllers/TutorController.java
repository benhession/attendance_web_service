package com.benhession.attendance_web_service.controllers;

import com.benhession.attendance_web_service.data.TutorService;
import com.benhession.attendance_web_service.data.UniversityModuleService;
import com.benhession.attendance_web_service.model.Tutor;
import com.benhession.attendance_web_service.model.UniversityModule;
import com.benhession.attendance_web_service.representational_models.TutorModuleModel;
import com.benhession.attendance_web_service.representational_models.TutorModuleModelAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/tutor")
@CrossOrigin("*")
public class TutorController {

    private final TutorService tutorService;
    private final UniversityModuleService moduleService;

    @Autowired
    public TutorController(TutorService tutorService, UniversityModuleService moduleService) {
        this.tutorService = tutorService;
        this.moduleService = moduleService;
    }

    @GetMapping(path = "/classes")
    public ResponseEntity<Set<TutorModuleModel>> getClassesOfTutor(Principal principal) {
        Optional<Tutor> tutor = tutorService.findById(principal.getName());
        Optional<Set<UniversityModule>> moduleSet;

        if (tutor.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        } else {
            moduleSet = Optional.ofNullable(moduleService.getUniversityModuleByTutor(tutor.get()));
        }

        return moduleSet.map(modules -> new ResponseEntity<>(
                TutorModuleModelAssembler.toCollection(modules), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NO_CONTENT));
    }
}
