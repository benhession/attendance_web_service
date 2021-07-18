package com.benhession.attendance_web_service.representational_models;

import com.benhession.attendance_web_service.model.StudentUniversityClass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class StudentAttendedModel {

    @EqualsAndHashCode.Include
    private String studentId;

    private String forename;

    private String surname;

    private Boolean attended;

    StudentAttendedModel(StudentUniversityClass studentUniversityClass) {
        this.studentId = studentUniversityClass.getStudentId();
        this.forename = studentUniversityClass.getStudent().getForename();
        this.surname = studentUniversityClass.getStudent().getSurname();
        this.attended = studentUniversityClass.getAttended();
    }

}
