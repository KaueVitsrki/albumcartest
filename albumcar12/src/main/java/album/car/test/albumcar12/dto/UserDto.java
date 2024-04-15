package album.car.test.albumcar12.dto;

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
public class UserDto {
    private UUID id;
    private String email;
    private String password;
    private String country;
    private String name;
    private String state;
    private String city;
    private String description;
    private String imagePerfil;
    private String wallpaper;

    public UserDto(UserModel userModel) {
        this.id = userModel.getId();
        this.email = userModel.getEmail();
        this.password = userModel.getPassword();
        this.country = userModel.getCountry();
        this.name = userModel.getName();
        this.state = userModel.getState();
        this.city = userModel.getCity();
        this.description = userModel.getDescription();
        this.imagePerfil = userModel.getImagePerfil();
        this.wallpaper = userModel.getWallpaper();
    }

    public static List<UserDto> convert(List<UserModel> userModel){
        return userModel.stream().map(UserDto::new).collect(Collectors.toList());
    }
}
