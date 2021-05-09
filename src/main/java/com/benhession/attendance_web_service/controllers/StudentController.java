package com.benhession.attendance_web_service.controllers;

import com.benhession.attendance_web_service.data.StudentService;
import com.benhession.attendance_web_service.data.StudentUniversityClassService;
import com.benhession.attendance_web_service.model.StudentUniversityClass;
import com.benhession.attendance_web_service.representational_models.StudentUniversityClassModel;
import com.benhession.attendance_web_service.representational_models.StudentUniversityClassModelAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.Set;


@RestController
@RequestMapping(path = "/student")
@CrossOrigin("*")
public class StudentController {

    private final StudentService studentService;
    private final StudentUniversityClassService classService;

    @Autowired
    public StudentController(StudentService studentService, StudentUniversityClassService classService) {
        this.studentService = studentService;
        this.classService = classService;
    }

    @GetMapping(value = "/classes")
    public ResponseEntity<Set<StudentUniversityClassModel>> getClassesOfStudent(Principal principal) {

        String studentId = principal.getName();

        if (studentId == null  || !studentService.existsById(studentId)) {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }

        Optional<Set<StudentUniversityClass>> studentClasses =
                Optional.ofNullable(studentService.studentClassesOfRequester(studentId));

        return studentClasses.map(
                studentUniversityClasses ->
                        new ResponseEntity<>(StudentUniversityClassModelAssembler
                                .toCollection(studentUniversityClasses) , HttpStatus.OK)
        ).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NO_CONTENT));

    }

    @GetMapping(path = "/attend")
    public ResponseEntity<Boolean> attendClass(Principal principal, @RequestParam String qrString) {

        Optional<StudentUniversityClass> theClass = studentService.studentClassByQRString(qrString, principal.getName());

        if(theClass.isPresent()) {
            StudentUniversityClass c = theClass.get();
            ZonedDateTime startTime = ZonedDateTime.of(c.getUniversityClass().getDateTime(), ZoneId.of("UTC"));
            // can take attendance up to 15 minutes after class start time
            ZonedDateTime endTime = startTime.plusMinutes(15);
            ZonedDateTime currentTime = ZonedDateTime.now(ZoneId.of("UTC"));

            if (startTime.isBefore(currentTime) && endTime.isAfter(currentTime)) {
                if (!c.getAttended()) {
                    c.setAttended(true);
                    return ResponseEntity.ok(classService.save(c).getAttended());
                } else {
                    return ResponseEntity.ok(false);
                }
            } else {
                return new ResponseEntity<>(null, HttpStatus.PRECONDITION_FAILED);
            }

        } else {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }

    }
}
