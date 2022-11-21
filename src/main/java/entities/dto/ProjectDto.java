package entities.dto;

import entities.dao.CompanyDao;
import entities.dao.CustomerDao;
import entities.dao.DeveloperDao;
import lombok.*;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
//@AllArgsConstructor
@EqualsAndHashCode
@Data
@ToString
public class ProjectDto {
    private Integer id;
    private String name;
    private CompanyDto company;
    private CustomerDto customer;
    //private Integer company_id;
    //private Integer customer_id;
    private Integer cost;
    private Date creation_date;
    //private CustomerDto customer;
    //private CompanyDto company;
    private Set<DeveloperDto> developers = new HashSet<>();

    public ProjectDto(Integer id, String name, CompanyDto company, CustomerDto customer, Integer cost, Date creation_date) {
        this.id = id;
        this.name = name;
        this.company = company;
        this.customer = customer;
        this.cost = cost;
        this.creation_date = creation_date;
    }
}
