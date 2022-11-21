package entities.dto;

import entities.Gender;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
//@AllArgsConstructor
@EqualsAndHashCode
@Data
@ToString
public class DeveloperDto {
    private Integer id;
    private String name;
    private Integer age;
    private Gender gender;
    private Integer salary;
    private Set<CompanyDto> developerCompanies  = new HashSet<>();
    private Set<ProjectDto> developerProjects = new HashSet<>();
    private Set<SkillDto> developerSkills = new HashSet<>();

    public DeveloperDto(Integer id, String name, Integer age, Gender gender, Integer salary) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
    }
}
