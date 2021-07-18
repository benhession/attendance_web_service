package com.benhession.attendance_web_service.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class AttendPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    private final Logger logger = Logger.getLogger("Class: AttendPublisher");

    @Autowired
    public AttendPublisher(ApplicationEventPublisher eventPublisher) {
        this.applicationEventPublisher = eventPublisher;
    }

    public void publishAttendEvent(final String classId) {
        logger.info("Publishing Attend Event");
        AttendEvent attendEvent = new AttendEvent(this, classId);
        applicationEventPublisher.publishEvent(attendEvent);
    }

}
