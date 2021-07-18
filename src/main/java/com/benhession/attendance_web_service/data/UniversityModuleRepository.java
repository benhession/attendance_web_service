package com.benhession.attendance_web_service.data;

import com.benhession.attendance_web_service.model.UniversityModule;
import com.benhession.attendance_web_service.model.UniversityModuleId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface UniversityModuleRepository extends CrudRepository<UniversityModule, UniversityModuleId> {

    @Query(value = "SELECT c.module " +
            "FROM UniversityClass c "  +
            "WHERE c.tutor.tutorId = :tutorId ")
    Set<UniversityModule> findUniversityModulesByClassesContaining(@Param("tutorId") String tutorId);
}
