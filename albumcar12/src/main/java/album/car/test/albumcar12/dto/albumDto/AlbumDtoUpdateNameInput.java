package album.car.test.albumcar12.dto.albumDto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlbumDtoUpdateNameInput {
    @NotBlank
    private String name;
}
