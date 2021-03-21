package com.benhession.attendance_web_service.data;

import com.benhession.attendance_web_service.model.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, String> {

}
