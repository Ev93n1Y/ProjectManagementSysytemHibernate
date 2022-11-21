package entities.dto;

import entities.dao.DeveloperDao;
import entities.dao.ProjectDao;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
//@AllArgsConstructor
@EqualsAndHashCode
@Data
@ToString
public class CompanyDto {
    private Integer id;
    private String name;
    private String location;
    //private Set<DeveloperDto> companyDevelopers;
    //private Set<ProjectDto> companyProjects;

    public CompanyDto(Integer id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }
}
