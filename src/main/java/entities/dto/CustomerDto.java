package entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
@ToString
public class CustomerDto {
    private Integer id;
    private String name;
    private String email;
}
