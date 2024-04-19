package album.car.test.albumcar12.dto.albumDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlbumDtoUpdateInput {
    private String name;
    private String description;
}
