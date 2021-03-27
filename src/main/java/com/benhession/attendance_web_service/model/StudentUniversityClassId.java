package com.benhession.attendance_web_service.model;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
public class StudentUniversityClassId implements Serializable {

    private Student student;
    private UniversityClass universityClass;
}
