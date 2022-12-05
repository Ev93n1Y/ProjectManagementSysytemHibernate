package entities.dto;

import entities.SkillLevel;
import lombok.*;

@NoArgsConstructor
@EqualsAndHashCode
@Data
@ToString
public class SkillDto {
    private Integer id;
    private String department;
    private SkillLevel level;

    public SkillDto(Integer id, String department, SkillLevel level) {
        this.id = id;
        this.department = department;
        this.level = level;
    }
}
