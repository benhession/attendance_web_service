package com.benhession.attendance_web_service.controllers;

import com.benhession.attendance_web_service.data.UniversityClassService;
import com.benhession.attendance_web_service.model.QRGenerator;
import com.benhession.attendance_web_service.model.UniversityClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;
import java.util.Optional;

@RestController
@RequestMapping("/class")
@CrossOrigin("*")
public class UniversityClassController {

    private final UniversityClassService classService;

    @Autowired
    public UniversityClassController(UniversityClassService classService) {
        this.classService = classService;
    }

    @GetMapping(path = "/qrcode", produces = "image/png")
    public ResponseEntity<BufferedImage> getUniversityClassQRCode(@RequestParam String classId) {
        Optional<UniversityClass> classById = classService.findUniversityClassById(classId);

        return classById.map(aClass -> {
            try {
                return ResponseEntity.ok(QRGenerator.generateQRCodeImage(aClass.getQrString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NO_CONTENT));
    }
}
