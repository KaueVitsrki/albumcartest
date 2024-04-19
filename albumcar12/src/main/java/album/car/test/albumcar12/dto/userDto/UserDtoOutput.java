package album.car.test.albumcar12.dto.userDto;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import album.car.test.albumcar12.model.UserModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoOutput {
    private UUID id;
    private String email;
    private String name;
    private String description;
    private String country;
    private String state;
    private String city;
    private String imagePerfil;
    private String wallpaper;

    public UserDtoOutput(UserModel userModel) {
        this.id = userModel.getId();
        this.email = userModel.getEmail();
        this.country = userModel.getCountry();
        this.name = userModel.getName();
        this.state = userModel.getState();
        this.city = userModel.getCity();
        this.description = userModel.getDescription();
        this.imagePerfil = userModel.getImagePerfil();
        this.wallpaper = userModel.getWallpaper();
    }

    public static List<UserDtoOutput> convert(List<UserModel> userModel){
        return userModel.stream().map(UserDtoOutput::new).collect(Collectors.toList());
    }
}
