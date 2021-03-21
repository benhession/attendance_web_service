package com.benhession.attendance_web_service.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "tutor")
public class Tutor {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "tutor_id")
    private String tutorId;

    @NonNull
    private String forename;

    @NonNull
    private String surname;
}
