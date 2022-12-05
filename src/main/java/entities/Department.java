package entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Department {
    Java("Java"),
    C_PLUS("C++"),
    C_SHARP("C#"),
    JS("JS");

    @Getter
    private final String description;
}
