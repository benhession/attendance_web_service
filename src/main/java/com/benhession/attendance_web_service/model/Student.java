package com.benhession.attendance_web_service.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "student")
public class Student {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "student_id")
    private String studentId;

    @NonNull
    private String forename;

    @NonNull
    private String surname;

    @OneToMany(mappedBy = "student")
    private Set<StudentUniversityClass> classes;
}
