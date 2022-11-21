package entities.dao;

import lombok.*;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = {"id", "name", "email"})
@ToString(of = {"id", "name", "email"})
@Entity
@Table(name = "customers")
public class CustomerDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @OneToMany(mappedBy = "customer")
    private Set<ProjectDao> customerProjects = new HashSet<>();

    public CustomerDao(Integer id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Set<ProjectDao> getCustomerProjects() {
        return Collections.unmodifiableSet(customerProjects);
    }

    public void addCustomerProject(ProjectDao projectDao) {
        customerProjects.add(projectDao);
    }

    public void removeCustomerProject(ProjectDao projectDao) {
        customerProjects.remove(projectDao);
    }
}
