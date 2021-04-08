package com.benhession.attendance_web_service.representational_models;

import com.benhession.attendance_web_service.model.StudentUniversityClass;
import com.benhession.attendance_web_service.model.Tutor;
import com.benhession.attendance_web_service.model.UniversityClass;
import com.benhession.attendance_web_service.model.UniversityModule;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Duration;
import java.time.LocalDateTime;


@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class StudentUniversityClassModel {

    @EqualsAndHashCode.Include
    private String classId;

    private String name;

    private String location;

    private LocalDateTime dateTime;

    private Duration duration;

    private String classType;

    private UniversityModule module;

    private Tutor tutor;

    private Boolean attended;

    StudentUniversityClassModel(StudentUniversityClass studentUniversityClass) {

        UniversityClass universityClass = studentUniversityClass.getUniversityClass();

        classId = universityClass.getClassId();
        name = universityClass.getName();
        location = universityClass.getLocation();
        dateTime = universityClass.getDateTime();
        duration = universityClass.getDuration();
        classType = universityClass.getClassType();
        module = universityClass.getModule();
        tutor = universityClass.getTutor();
        attended = studentUniversityClass.getAttended();
    }
}
