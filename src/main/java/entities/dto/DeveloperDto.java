package entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
@ToString
public class DeveloperDto {
    Integer id;
    String name;
    Integer age;
    String gender;
    Integer salary;
}
