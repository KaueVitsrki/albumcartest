package album.car.test.albumcar12.dto.albumDto;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import album.car.test.albumcar12.model.AlbumModel;
import album.car.test.albumcar12.model.DiaryModel;
import album.car.test.albumcar12.model.HotModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlbumDtoOutput {
    private UUID id;
    private String name;
    private String description;
    private List<String> image;
    private List<DiaryModel> diary;
    private List<HotModel> hot;
    
    public AlbumDtoOutput(AlbumModel albumModel) {
        this.id = albumModel.getId();
        this.name = albumModel.getName();
        this.description = albumModel.getDescription();
        this.image = albumModel.getImage();
        this.diary = albumModel.getDiary();
        this.hot = albumModel.getHot();
    }  

    public static List<AlbumDtoOutput> convert(List<AlbumModel> albumModel){
        return albumModel.stream().map(AlbumDtoOutput::new).collect(Collectors.toList());
    }
}
