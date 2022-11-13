package entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
@ToString
public class SkillDto {
    private Integer id;
    private String department;
    private String level;
}
