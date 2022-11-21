package entities.dao;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = {"id", "name", "cost", "creation_date"})
@ToString(of = {"id", "name", "cost", "creation_date"})
@Entity
@Table(name = "projects")
public class ProjectDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name", length = 100)
    private String name;
    @Column(name = "cost")
    private Integer cost;

    //@Column(name = "customer_id")
    //private Integer customerId;
    //@Column(name = "company_id")
    //private Integer companyId;

    @Column(name = "creation_date")
    private Date creation_date;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST}
    )
    @JoinColumn(name = "customer_id")
    private CustomerDao customer;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST}
    )
    @JoinColumn(name = "company_id")
    private CompanyDao company;

    @ManyToMany(mappedBy = "developerProjects")
    private Set<DeveloperDao> developers = new HashSet<>();

    public ProjectDao(Integer id, String name, Integer cost, Date creation_date) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.creation_date = creation_date;
    }

    public CustomerDao getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDao customerDao) {
        customer = customerDao;
    }

    public CompanyDao getCompany() {
        return company;
    }

    public void setCompany(CompanyDao companyDao) {
        company = companyDao;
    }

    public Set<DeveloperDao> getDevelopers() {
        return Collections.unmodifiableSet(developers);
    }

    public void addProjectDeveloper(DeveloperDao developerDao) {
        developers.add(developerDao);
    }

    public void removeProjectDeveloper(DeveloperDao developerDao) {
        developers.remove(developerDao);
    }
}
