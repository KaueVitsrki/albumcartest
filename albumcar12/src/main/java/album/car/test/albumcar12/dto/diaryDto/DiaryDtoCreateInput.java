package album.car.test.albumcar12.dto.diaryDto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiaryDtoCreateInput {
    @NotBlank
    private String text;
    private String youtubeLink;
    @Size(max=4)
    private List<String> images;
}
