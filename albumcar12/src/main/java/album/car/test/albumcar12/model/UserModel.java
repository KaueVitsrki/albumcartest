package album.car.test.albumcar12.model;

import java.util.List;
import java.util.UUID;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;

import album.car.test.albumcar12.dto.loginDto.LoginDtoInput;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class UserModel{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true)
    private UUID id;
    
    @Email 
    @NotBlank 
    @Column(unique = true)
    private String email;
    
    @NotBlank
    private String password;
    
    @Length(min = 3) 
    @NotBlank 
    @Column(unique = true)
    private String name;

    private String country;
    private String state;
    private String city;
    private String description;
    private String image;
    private String wallpaper;
    private UserRole role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonIgnore
    private List<DiaryModel> diary;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonIgnore
    private List<AlbumModel> album;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonIgnore
    private List<HotModel> hot;

    public boolean isLoginCorrect(LoginDtoInput loginDto, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(loginDto.getPassword(), this.password);
    }
}
