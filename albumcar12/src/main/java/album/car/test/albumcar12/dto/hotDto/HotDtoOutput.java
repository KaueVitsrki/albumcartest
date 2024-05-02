package album.car.test.albumcar12.dto.hotDto;

import java.util.UUID;

import album.car.test.albumcar12.model.AlbumModel;
import album.car.test.albumcar12.model.UserModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotDtoOutput {
    private UUID id;
    private UserModel user;
    private AlbumModel album;
}
