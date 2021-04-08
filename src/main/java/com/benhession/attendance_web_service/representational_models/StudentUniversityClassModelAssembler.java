package com.benhession.attendance_web_service.representational_models;

import com.benhession.attendance_web_service.model.StudentUniversityClass;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class StudentUniversityClassModelAssembler {

    public static Set<StudentUniversityClassModel> toCollection(Collection<StudentUniversityClass> universityClasses) {

        Set<StudentUniversityClassModel> modelSet = new HashSet<>();

        universityClasses.forEach(uniClass -> modelSet.add(new StudentUniversityClassModel(uniClass)));

        return modelSet;
    }
}
