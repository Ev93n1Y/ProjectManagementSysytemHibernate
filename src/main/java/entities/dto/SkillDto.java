package entities.dto;

import entities.Department;
import entities.SkillLevel;
import lombok.*;

@NoArgsConstructor
@EqualsAndHashCode
@Data
@ToString
public class SkillDto {
    private Integer id;
    private Department department;
    private SkillLevel level;

    public SkillDto(Integer id, Department department, SkillLevel level) {
        this.id = id;
        this.department = department;
        this.level = level;
    }
}
