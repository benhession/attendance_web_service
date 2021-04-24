package com.benhession.attendance_web_service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Data
@Table(name = "student_class")
@IdClass(StudentUniversityClassId.class)
public class StudentUniversityClass {

    @Id
    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "student_id")
    @JsonIgnore
    private Student student;

    @Id
    @ManyToOne
    @JoinColumn(name = "class_id", referencedColumnName = "class_id")
    private UniversityClass universityClass;

    @Column(name = "attended")
    @EqualsAndHashCode.Exclude
    private Boolean attended;
}
