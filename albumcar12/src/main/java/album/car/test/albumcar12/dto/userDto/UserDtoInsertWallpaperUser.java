package album.car.test.albumcar12.dto.userDto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoInsertWallpaperUser {
    @NotBlank
    private String wallpaper;
}
