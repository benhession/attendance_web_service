package com.benhession.attendance_web_service.controllers;

import com.benhession.attendance_web_service.data.TutorService;
import com.benhession.attendance_web_service.data.UniversityClassService;
import com.benhession.attendance_web_service.data.UniversityModuleService;
import com.benhession.attendance_web_service.events.AttendEvent;
import com.benhession.attendance_web_service.model.Tutor;
import com.benhession.attendance_web_service.model.UniversityClass;
import com.benhession.attendance_web_service.model.UniversityModule;
import com.benhession.attendance_web_service.representational_models.TutorModuleModel;
import com.benhession.attendance_web_service.representational_models.TutorModuleModelAssembler;
import com.benhession.attendance_web_service.representational_models.TutorUniversityClassModel;
import com.benhession.attendance_web_service.representational_models.TutorUniversityClassModelAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

@RestController
@RequestMapping("/tutor")
@CrossOrigin("*")
public class TutorController {

    private final TutorService tutorService;
    private final UniversityModuleService moduleService;
    private final UniversityClassService classService;

    private final Map<String, SseEmitter> sses = new ConcurrentHashMap<>();
    private final Random random = new Random();

    private final Logger logger = Logger.getLogger("Tutor Controller");

    @Autowired
    public TutorController(TutorService tutorService, UniversityModuleService moduleService,
                           UniversityClassService classService) {
        this.tutorService = tutorService;
        this.moduleService = moduleService;
        this.classService = classService;
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

    @GetMapping("/attendance-for/{classId}")
    public SseEmitter getUpdatesForClass(Principal principal, @PathVariable String classId) {
        if (classService.classHasTutor(classId, principal.getName())) {
            // timeout set for 30 minutes
            SseEmitter sseEmitter = new SseEmitter(30 * 60 * 1000L);
            String sseKey = classId
                    .concat("_")
                    .concat(Integer.toString(random.nextInt()));

            sses.put(sseKey, sseEmitter);
            sseEmitter.onCompletion(() -> {
                sses.remove(classId);
                logger.info(String.format("SSE Emitter \"%s\" timed out and it's reference was removed", sseKey));
            });
            return sseEmitter;
        } else {
            return null;
        }
    }

    @EventListener
    public void attendEventListener(AttendEvent attendEvent) {

        logger.info("Received Attend event: " + attendEvent.getMessage());

        String classId = attendEvent.getMessage();
        System.out.println(classId);

        for (String key: sses.keySet()) {
            if (key.split("_")[0].equals(classId)) {

                Optional<UniversityClass> universityClass = classService.findUniversityClassById(classId);

                TutorUniversityClassModel theClass = universityClass
                        .map(TutorUniversityClassModelAssembler::toResource)
                        .orElseThrow(() -> new RuntimeException("Unable to get UniversityClass"));

                try {
                    sses.get(key).send(theClass);
                } catch (IOException e) {
                    throw new RuntimeException("Unable to send sse");
                }
            }
        }
    }
}
