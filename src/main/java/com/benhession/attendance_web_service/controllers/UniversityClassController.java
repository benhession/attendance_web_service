package com.benhession.attendance_web_service.controllers;

import com.benhession.attendance_web_service.data.TutorService;
import com.benhession.attendance_web_service.data.UniversityClassService;
import com.benhession.attendance_web_service.model.QRGenerator;
import com.benhession.attendance_web_service.model.Tutor;
import com.benhession.attendance_web_service.model.UniversityClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@RestController
@RequestMapping("/class")
@CrossOrigin("*")
public class UniversityClassController {

    private final UniversityClassService classService;
    private final TutorService tutorService;

    @Autowired
    public UniversityClassController(UniversityClassService classService, TutorService tutorService) {
        this.classService = classService;
        this.tutorService = tutorService;
    }

    @GetMapping(path = "/qrcode", produces = "image/png")
    public ResponseEntity<BufferedImage> getUniversityClassQRCode(@RequestParam String classId, Principal principal) {

        Optional<Tutor> theTutor = tutorService.findById(principal.getName());

        // check if tutor is in database
        if (theTutor.isEmpty()) { return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);}

        Optional<UniversityClass> classById = classService.findUniversityClassById(classId);

        // check the tutor is assigned to the class and the class has not finished
        if (classById.isPresent()) {

            UniversityClass aClass = classById.get();

            LocalDateTime endTime = aClass.getDateTime().plus(aClass.getDuration());

            if(!aClass.getTutor().equals(theTutor.get()) || endTime.isBefore(LocalDateTime.now(ZoneId.of("UTC")))) {
                return new ResponseEntity<>(null, HttpStatus.PRECONDITION_FAILED);
            }
        }

        return classById.map(aClass -> {
                try {
                    return ResponseEntity.ok(QRGenerator.generateQRCodeImage(aClass.getQrString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            // if the image cannot be generated return null
            return null;

        }).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NO_CONTENT));
    }

}
