package album.car.test.albumcar12.dto.userDto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoUpdateNameUser {
    @Length(min = 3) @NotBlank
    private String name;
}
