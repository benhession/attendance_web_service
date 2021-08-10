package com.benhession.attendance_web_service.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;


@Component
public class AttendPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    private final Logger logger = LoggerFactory.getLogger("SSE");

    @Autowired
    public AttendPublisher(ApplicationEventPublisher eventPublisher) {
        this.applicationEventPublisher = eventPublisher;
    }

    public void publishAttendEvent(final String classId) {
        logger.debug("Publishing Attend Event");
        AttendEvent attendEvent = new AttendEvent(this, classId);
        applicationEventPublisher.publishEvent(attendEvent);
    }

}
