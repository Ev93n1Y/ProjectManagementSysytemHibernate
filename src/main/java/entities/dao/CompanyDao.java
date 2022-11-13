package entities.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
@ToString
public class CompanyDao {
    private Integer id;
    private String name;
    private String location;
}
