package album.car.test.albumcar12.dto.loginDto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDtoInput {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
