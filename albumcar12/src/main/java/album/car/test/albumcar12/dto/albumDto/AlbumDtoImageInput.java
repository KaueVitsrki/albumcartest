package album.car.test.albumcar12.dto.albumDto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlbumDtoImageInput {
    private List<String> image;
}
