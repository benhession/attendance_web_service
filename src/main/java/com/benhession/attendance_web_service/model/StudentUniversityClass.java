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
    @Column(name = "student_id")
    @JsonIgnore
    private String studentId;

    @Id
    @Column(name = "class_id")
    @JsonIgnore
    private String universityClassId;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "student_id", insertable = false, updatable = false)
    @JsonIgnore
    private Student student;

    @ManyToOne
    @JoinColumn(name = "class_id", referencedColumnName = "class_id", insertable = false, updatable = false)
    private UniversityClass universityClass;

    @Column(name = "attended")
    @EqualsAndHashCode.Exclude
    private Boolean attended;
}
