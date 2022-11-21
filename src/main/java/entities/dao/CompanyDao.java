package entities.dao;

import lombok.*;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = {"id", "name", "location"})
@ToString(of = {"id", "name", "location"})
@Entity
@Table(name = "companies")
public class CompanyDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "location", length = 100, nullable = false)
    private String location;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST}
    )
    @JoinTable(
            name = "companies_developers",
            joinColumns = {@JoinColumn(name = "company_id")},
            inverseJoinColumns = {@JoinColumn(name = "developer_id")},
            foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT),
            inverseForeignKey = @ForeignKey(ConstraintMode.CONSTRAINT)
    )
    private Set<DeveloperDao> companyDevelopers = new HashSet<>();

    @OneToMany(mappedBy = "company")
    private Set<ProjectDao> companyProjects = new HashSet<>();

    public CompanyDao(Integer id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public Set<DeveloperDao> getCompanyDevelopers() {
        return Collections.unmodifiableSet(companyDevelopers);
    }

    public void addCompanyDeveloper(DeveloperDao developerDao) {
        companyDevelopers.add(developerDao);
    }

    public void removeCompanyDeveloper(DeveloperDao developerDao) {
        companyDevelopers.remove(developerDao);
    }

    public Set<ProjectDao> getCompanyProjects() {
        return Collections.unmodifiableSet(companyProjects);
    }

    public void addCompanyProject(ProjectDao projectDao) {
        companyProjects.add(projectDao);
    }

    public void removeCompanyProject(ProjectDao projectDao) {
        companyProjects.remove(projectDao);
    }
}
