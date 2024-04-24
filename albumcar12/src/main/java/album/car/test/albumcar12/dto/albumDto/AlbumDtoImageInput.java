package album.car.test.albumcar12.dto.albumDto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlbumDtoImageInput {
    @NotBlank
    private List<String> image;
}
