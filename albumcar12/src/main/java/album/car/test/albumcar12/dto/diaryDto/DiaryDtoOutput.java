package album.car.test.albumcar12.dto.diaryDto;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import album.car.test.albumcar12.model.DiaryModel;
import album.car.test.albumcar12.model.UserModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiaryDtoOutput {
    private UUID id;
    private String text;
    private String youtubeVideo;
    private List<String> images;
    private UserModel user;

    public DiaryDtoOutput(DiaryModel diaryModel) {
        this.id = diaryModel.getId();
        this.text = diaryModel.getText();
        this.youtubeVideo = diaryModel.getYoutubeVideo();
        this.images = diaryModel.getImages();
        this.user = diaryModel.getUser();
    }

    public static List<DiaryDtoOutput> convert(List<DiaryModel> diaryModel){
        return diaryModel.stream().map(DiaryDtoOutput::new).collect(Collectors.toList());
    }
}
