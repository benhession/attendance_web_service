package com.benhession.attendance_web_service.representational_models;

import com.benhession.attendance_web_service.model.UniversityModule;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class TutorModuleModelAssembler {

    public static Set<TutorModuleModel> toCollection(Collection<UniversityModule> universityModules) {

        Set<TutorModuleModel> modelSet = new HashSet<>();

        universityModules.forEach(uniModule -> modelSet.add(new TutorModuleModel(uniModule)));

        return modelSet;
    }
}
