package entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.sql.Date;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
@ToString
public class ProjectDto {
    private Integer id;
    private String name;
    private Integer company_id;
    private Integer customer_id;
    private Integer cost;
    private Date creation_date;
}
