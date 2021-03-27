package com.benhession.attendance_web_service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@IdClass(UniversityModuleId.class)
@Table(name = "module")
public class UniversityModule {

    @Id
    @Column(name = "module_code")
    private String moduleCode;

    @Id
    @Column(name = "module_year")
    private String moduleYear;

    @Column(name = "module_name")
    private String moduleName;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "module")
    private Set<UniversityClass> classes;


}
