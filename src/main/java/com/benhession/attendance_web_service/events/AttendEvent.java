package com.benhession.attendance_web_service.events;

import org.springframework.context.ApplicationEvent;

public class AttendEvent extends ApplicationEvent {

    String classId;

    public AttendEvent(Object source, String message) {
        super(source);
        this.classId = message;
    }

    public String getMessage() {
        return classId;
    }
}
