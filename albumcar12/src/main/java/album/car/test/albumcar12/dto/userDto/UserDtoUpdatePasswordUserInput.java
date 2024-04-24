package album.car.test.albumcar12.dto.userDto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoUpdatePasswordUserInput {
    @NotBlank
    private String currentPassword;
    @NotBlank
    private String newPassword;
    @NotBlank
    private String newPasswordConfirmation;
}
