package entities.dto;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@EqualsAndHashCode
@Data
@ToString
public class CustomerDto {
    private Integer id;
    private String name;
    private String email;
    public CustomerDto(Integer id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}

