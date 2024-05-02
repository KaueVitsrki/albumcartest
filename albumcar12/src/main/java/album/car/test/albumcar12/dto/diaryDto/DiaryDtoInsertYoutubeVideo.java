package album.car.test.albumcar12.dto.diaryDto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiaryDtoInsertYoutubeVideo {
    @NotBlank
    private String youtubeVideo;
}
