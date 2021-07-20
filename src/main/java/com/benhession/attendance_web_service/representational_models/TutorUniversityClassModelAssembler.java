package com.benhession.attendance_web_service.representational_models;

import com.benhession.attendance_web_service.model.UniversityClass;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class TutorUniversityClassModelAssembler {

    public static Set<TutorUniversityClassModel> toCollection(Collection<UniversityClass> universityClasses) {

        Set<TutorUniversityClassModel> modelSet = new HashSet<>();

        universityClasses.forEach(uniClass -> modelSet.add(new TutorUniversityClassModel(uniClass)));

        return modelSet;
    }

    public static TutorUniversityClassModel toResource(UniversityClass theClass) {
        return new TutorUniversityClassModel(theClass);
    }
}
