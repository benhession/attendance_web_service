package com.benhession.attendance_web_service.representational_models;

import com.benhession.attendance_web_service.model.UniversityClass;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.DurationDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TutorUniversityClassModel {

    @EqualsAndHashCode.Include
    private String classId;

    private String name;

    private String location;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dateTime;

    @JsonDeserialize(using = DurationDeserializer.class)
    private Duration duration;

    private String classType;

    private Set<StudentAttendedModel> students;

    TutorUniversityClassModel(UniversityClass universityClass) {
        this.classId = universityClass.getClassId();
        this.name = universityClass.getName();
        this.location = universityClass.getLocation();
        this.dateTime = universityClass.getDateTime();
        this.duration = universityClass.getDuration();
        this.classType = universityClass.getClassType();
        this.students = StudentAttendedModelAssembler.toCollection(universityClass.getStudents());
    }
}
