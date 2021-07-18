package com.benhession.attendance_web_service.representational_models;

import com.benhession.attendance_web_service.model.StudentUniversityClass;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class StudentAttendedModelAssembler {

    public static Set<StudentAttendedModel> toCollection(Collection<StudentUniversityClass> universityClasses) {

        Set<StudentAttendedModel> modelSet = new HashSet<>();

        universityClasses.forEach(uniClass -> modelSet.add(new StudentAttendedModel(uniClass)));

        return modelSet;
    }
}
