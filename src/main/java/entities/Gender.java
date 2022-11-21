package entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Gender{
    male("male"),
    female("female");

    @Getter
    private final String description;
}
