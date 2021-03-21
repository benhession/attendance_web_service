package com.benhession.attendance_web_service.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "class")
public class UniversityClass {

    @Id
    @EqualsAndHashCode.Include
    private String classId;

    @Column(name = "class_name")
    private String name;

    private String location;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    private Duration duration;

    @Column(name = "class_type")
    private String classType;

    @Column(name = "qr_string")
    private String qrString;

    @ManyToOne
    @JoinColumns({@JoinColumn(name = "module_code", referencedColumnName = "module_code"),
                @JoinColumn(name = "module_year", referencedColumnName = "module_year")})
    private UniversityModule module;

    @ManyToOne
    @JoinColumn(name = "tutor_id")
    private Tutor tutor;

    @ManyToMany(mappedBy = "classes")
    private Set<Student> students;


}
