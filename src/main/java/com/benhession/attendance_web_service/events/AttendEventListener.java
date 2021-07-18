package com.benhession.attendance_web_service.events;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class AttendEventListener implements ApplicationListener<AttendEvent> {

    private final Logger logger = Logger.getLogger("Class: Attend Event Listener");

    @Override
    public void onApplicationEvent(AttendEvent attendEvent) {
        logger.info("Received Attend event: " + attendEvent.getMessage());
    }
}
