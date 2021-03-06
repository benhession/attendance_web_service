package com.benhession.attendance_web_service.data;

import com.benhession.attendance_web_service.model.StudentUniversityClass;
import com.benhession.attendance_web_service.model.StudentUniversityClassId;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface StudentUniversityClassRepository extends
        CrudRepository<StudentUniversityClass, StudentUniversityClassId> {

    Set<StudentUniversityClass> findStudentUniversityClassByStudent_StudentId(String studentId);

    Optional<StudentUniversityClass> findStudentUniversityClassByUniversityClass_QrStringAndStudent_StudentId
            (String qrString, String studentId);

    Optional<StudentUniversityClass> findStudentUniversityClassByStudent_StudentIdAndUniversityClass_ClassId
            (String studentId, String classId);
}
