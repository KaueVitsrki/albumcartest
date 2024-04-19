package album.car.test.albumcar12.dto.userDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoUpdateLocationUserInput {
    private String country;
    private String state;
    private String city;
}
