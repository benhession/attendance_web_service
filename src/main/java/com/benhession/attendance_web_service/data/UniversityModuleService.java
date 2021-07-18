package com.benhession.attendance_web_service.data;

import com.benhession.attendance_web_service.model.Tutor;
import com.benhession.attendance_web_service.model.UniversityModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UniversityModuleService {

    private final UniversityModuleRepository moduleRepository;

    @Autowired
    public UniversityModuleService(UniversityModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    public Set<UniversityModule> getUniversityModuleByTutor(Tutor tutor) {
        return moduleRepository.findUniversityModulesByClassesContaining(tutor.getTutorId());
    }
}
