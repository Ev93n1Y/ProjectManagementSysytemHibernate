package entities.dto;

import entities.dao.CompanyDao;
import entities.dao.CustomerDao;
import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@EqualsAndHashCode
@Data
@ToString
public class ProjectDto {
    private Integer id;
    private String name;
    private CompanyDao company;
    private CustomerDao customer;
    private Integer cost;
    private Date creation_date;

    public ProjectDto(Integer id, String name, CompanyDao company, CustomerDao customer, Integer cost, Date creation_date) {
        this.id = id;
        this.name = name;
        this.company = company;
        this.customer = customer;
        this.cost = cost;
        this.creation_date = creation_date;
    }
}
