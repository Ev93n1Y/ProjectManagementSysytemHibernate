package entities.dao;

import entities.Gender;
import lombok.*;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = {"id", "name", "age", "gender", "salary"})
@ToString(of = {"id", "name", "age", "gender", "salary"})
@Entity
@Table(name = "developers")
public class DeveloperDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "salary", nullable = false)
    private Integer salary;

    @ManyToMany(mappedBy = "companyDevelopers")
    private Set<CompanyDao> developerCompanies = new HashSet<>();

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST}
    )
    @JoinTable(
            name = "developers_projects",
            joinColumns = {@JoinColumn(name = "developer_id")},
            inverseJoinColumns = {@JoinColumn(name = "project_id")}
    )
    private Set<ProjectDao> developerProjects = new HashSet<>();

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST}
    )
    @JoinTable(
            name = "developers_skills",
            joinColumns = {@JoinColumn(name = "developer_id")},
            inverseJoinColumns = {@JoinColumn(name = "skill_id")}
    )
    private Set<SkillDao> developerSkills = new HashSet<>();

    public DeveloperDao(Integer id, String name, Integer age, Gender gender, Integer salary) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
    }

    public Set<CompanyDao> getDeveloperCompanies() {
        return Collections.unmodifiableSet(developerCompanies);
    }

    public void addDeveloperCompany(CompanyDao companyDao) {
        developerCompanies.add(companyDao);
    }

    public void removeDeveloperCompany(CompanyDao companyDao) {
        developerCompanies.remove(companyDao);
    }

    public Set<ProjectDao> getDeveloperProjects() {
        return Collections.unmodifiableSet(developerProjects);
    }

    public void addDeveloperCompany(ProjectDao projectDao) {
        developerProjects.add(projectDao);
    }

    public void removeDeveloperCompany(ProjectDao projectDao) {
        developerProjects.remove(projectDao);
    }

    public Set<SkillDao> getDeveloperSkills() {
        return Collections.unmodifiableSet(developerSkills);
    }

    public void addDeveloperSkill(SkillDao skillDao) {
        developerSkills.add(skillDao);
    }

    public void removeDeveloperSkill(SkillDao skillDao) {
        developerSkills.remove(skillDao);
    }
}
