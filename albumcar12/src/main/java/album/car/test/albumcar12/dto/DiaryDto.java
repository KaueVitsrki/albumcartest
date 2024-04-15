package album.car.test.albumcar12.dto;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import album.car.test.albumcar12.model.DiaryModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiaryDto {
    private UUID id;
    private String text;
    private List<String> youtubeLinks;
    private Timestamp data;
    private List<String> images;
    
    public DiaryDto(DiaryModel diaryModel) {
        this.id = diaryModel.getId();
        this.text = diaryModel.getText();
        this.youtubeLinks = diaryModel.getYoutubeLinks();
        this.data = diaryModel.getData();
        this.images = diaryModel.getImages();
    }

    public static List<DiaryDto> convert(List<DiaryModel> diaryModel){
        return diaryModel.stream().map(DiaryDto::new).collect(Collectors.toList());
    }
}