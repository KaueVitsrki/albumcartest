package album.car.test.albumcar12.dto.userDto;


import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoCreateInput {
    @Email @NotBlank @Column(unique = true)
    private String email;
    
    @NotBlank
    private String password;
    
    @Length(min = 3) @NotBlank
    private String name;
//     private String imagePerfil;
//     private String wallpaper;
}
