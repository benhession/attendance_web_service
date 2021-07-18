package com.benhession.attendance_web_service.representational_models;

import com.benhession.attendance_web_service.model.UniversityModule;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TutorModuleModel {

    @EqualsAndHashCode.Include
    private String moduleCode;

    @EqualsAndHashCode.Include
    private String moduleYear;

    private String moduleName;

    private Set<TutorUniversityClassModel> classes;

    TutorModuleModel(UniversityModule module) {
        this.moduleCode = module.getModuleCode();
        this.moduleYear = module.getModuleYear();
        this.moduleName = module.getModuleName();
        this.classes = TutorUniversityClassModelAssembler.toCollection(module.getClasses());
    }

}
