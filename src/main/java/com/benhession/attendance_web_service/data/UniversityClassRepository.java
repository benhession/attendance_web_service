package com.benhession.attendance_web_service.data;

import com.benhession.attendance_web_service.model.UniversityClass;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UniversityClassRepository extends CrudRepository<UniversityClass, String> {
    
}
