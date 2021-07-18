package com.benhession.attendance_web_service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.interval.PostgreSQLIntervalType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "class")
@TypeDef(typeClass = PostgreSQLIntervalType.class, defaultForType = Duration.class)
public class UniversityClass {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "class_id")
    private String classId;

    @Column(name = "class_name")
    private String name;

    private String location;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Column(name = "duration", columnDefinition = "interval")
    private Duration duration;

    @Column(name = "class_type")
    private String classType;

    @Column(name = "qr_string")
    @JsonIgnore
    private String qrString;

    @ManyToOne
    @JoinColumns({@JoinColumn(name = "module_code", referencedColumnName = "module_code"),
                @JoinColumn(name = "module_year", referencedColumnName = "module_year")})
    private UniversityModule module;

    @ManyToOne
    @JoinColumn(name = "tutor_id")
    private Tutor tutor;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "universityClass")
    private Set<StudentUniversityClass> students;


}
