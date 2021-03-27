package com.benhession.attendance_web_service.model;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
public class UniversityModuleId implements Serializable {

    private String moduleCode;
    private String moduleYear;
}
