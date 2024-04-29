package album.car.test.albumcar12.dto.hotDto;

import java.util.UUID;

import album.car.test.albumcar12.model.AlbumModel;
import album.car.test.albumcar12.model.HotModel;
import album.car.test.albumcar12.model.UserModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotDto {
    private UUID id;
    private UserModel user;
    private AlbumModel album;

    public HotDto(HotModel hotModel){
        this.id = hotModel.getId();
        this.user = hotModel.getUser();
        this.album = hotModel.getAlbum();
    }

}
