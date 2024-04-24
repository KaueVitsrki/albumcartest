package album.car.test.albumcar12.dto.albumDto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlbumDtoDeleteImageInput {
    @NotBlank
    private String image;
}
