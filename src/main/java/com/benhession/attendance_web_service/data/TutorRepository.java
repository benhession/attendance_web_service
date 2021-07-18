package com.benhession.attendance_web_service.data;

import com.benhession.attendance_web_service.model.Tutor;
import org.springframework.data.repository.CrudRepository;

public interface TutorRepository extends CrudRepository<Tutor, String> {
}
