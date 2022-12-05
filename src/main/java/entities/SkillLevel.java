package entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum SkillLevel {
    Junior("Junior"),
    Middle("Middle"),
    Senior("Senior");

    @Getter
    private final String description;
}
