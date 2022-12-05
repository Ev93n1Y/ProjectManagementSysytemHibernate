package entities.dao;

import entities.SkillLevel;
import lombok.*;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = {"id", "department", "level"})
@ToString(of = {"id", "department", "level"})
@Entity
@Table(name = "skills")
public class SkillDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "department")
    private String department;
    @Enumerated(EnumType.STRING)
    @Column(name = "level")
    private SkillLevel level;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST}
    )
    @JoinTable(
            name = "developers_skills",
            inverseJoinColumns = {@JoinColumn(name = "developer_id")},
            joinColumns = {@JoinColumn(name = "skill_id")}
    )
    private Set<DeveloperDao> developers = new HashSet<>();

    public SkillDao(Integer id, String department, SkillLevel level) {
        this.id = id;
        this.department = department;
        this.level = level;
    }

    public Set<DeveloperDao> getDevelopers() {
        return Collections.unmodifiableSet(developers);
    }

    public void addDeveloper(DeveloperDao developerDao) {
        developers.add(developerDao);
    }

    public void removeDeveloper(DeveloperDao developerDao) {
        developers.remove(developerDao);
    }
}
