package album.car.test.albumcar12.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import album.car.test.albumcar12.dto.userDto.UserDtoCreateInput;
import album.car.test.albumcar12.dto.userDto.UserDtoDeleteInput;
import album.car.test.albumcar12.dto.userDto.UserDtoInserImageUserInput;
import album.car.test.albumcar12.dto.userDto.UserDtoInsertDescriptionInput;
import album.car.test.albumcar12.dto.userDto.UserDtoInsertLocationUserInput;
import album.car.test.albumcar12.dto.userDto.UserDtoInsertWallpaperUserInput;
import album.car.test.albumcar12.dto.userDto.UserDtoOutput;
import album.car.test.albumcar12.dto.userDto.UserDtoUpdateEmailUserInput;
import album.car.test.albumcar12.dto.userDto.UserDtoUpdateLocationUserInput;
import album.car.test.albumcar12.dto.userDto.UserDtoUpdateNameUserInput;
import album.car.test.albumcar12.dto.userDto.UserDtoUpdatePasswordUserInput;
import album.car.test.albumcar12.model.UserModel;
import album.car.test.albumcar12.repository.UserRepository;
import album.car.test.albumcar12.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<UserDtoOutput> createUser(@RequestBody @Valid UserDtoCreateInput userDto){
        UserDtoOutput userDtoOutput = userService.createUser(userDto);
        return new ResponseEntity<UserDtoOutput>(userDtoOutput, HttpStatus.CREATED);
    }

    @PostMapping("/description")
    public ResponseEntity<UserDtoOutput> insertDescriptionUser(JwtAuthenticationToken token, @RequestBody @Valid UserDtoInsertDescriptionInput userDto){
        UserDtoOutput userDtoOutput = userService.insertDescriptionUser(token, userDto);
        return new ResponseEntity<UserDtoOutput>(userDtoOutput, HttpStatus.CREATED);
    }

    @PostMapping("/location")
    public ResponseEntity<UserDtoOutput> insertLocationUser(JwtAuthenticationToken token, @RequestBody @Valid UserDtoInsertLocationUserInput userDto){
        UserDtoOutput userDtoOutput = userService.insertLocationUser(token, userDto);
        return new ResponseEntity<UserDtoOutput>(userDtoOutput, HttpStatus.CREATED);
    }

    @PostMapping("/wallpaper")
    public ResponseEntity<UserDtoOutput> insertWallpaperUser(JwtAuthenticationToken token, @RequestBody @Valid UserDtoInsertWallpaperUserInput userDto){
        UserDtoOutput userDtoOutput = userService.insertWallpaperUser(token, userDto);
        return new ResponseEntity<UserDtoOutput>(userDtoOutput, HttpStatus.CREATED);
    }

    @PostMapping("/image")
    public ResponseEntity<UserDtoOutput> insertImageUser(JwtAuthenticationToken token, @RequestBody @Valid UserDtoInserImageUserInput userDto){
        UserDtoOutput userDtoOutput = userService.insertImageUser(token, userDto);
        return new ResponseEntity<UserDtoOutput>(userDtoOutput, HttpStatus.CREATED);
    }

    @PatchMapping("/name")
    public ResponseEntity<UserDtoOutput> updateNameUser(JwtAuthenticationToken token, @RequestBody @Valid UserDtoUpdateNameUserInput userDto){
        UserDtoOutput userDtoOutput = userService.updateNameUser(token, userDto);
        return ResponseEntity.ok(userDtoOutput);
    }

    @PatchMapping("/email")
    public ResponseEntity<UserDtoOutput> updateEmailUser(JwtAuthenticationToken token, @RequestBody @Valid UserDtoUpdateEmailUserInput userDto){
        UserDtoOutput userDtoOutput = userService.updateEmailUser(token, userDto);
        return ResponseEntity.ok(userDtoOutput);
    }

    @PatchMapping("/password")
    public ResponseEntity<UserDtoOutput> updatePasswordUser(JwtAuthenticationToken token, @RequestBody @Valid UserDtoUpdatePasswordUserInput userDto){
        UserDtoOutput userDtoOutput = userService.updatePasswordUser(token, userDto);
        return ResponseEntity.ok(userDtoOutput);
    }

    @PatchMapping("/location")
    public ResponseEntity<UserDtoOutput> updateLocationUser(JwtAuthenticationToken token, @RequestBody UserDtoUpdateLocationUserInput userDto){
        UserDtoOutput userDtoOutput = userService.updateLocationUser(token, userDto);
        return ResponseEntity.ok(userDtoOutput);
    }

    @PatchMapping("/description")
    public ResponseEntity<UserDtoOutput> updateDescriptionUser(JwtAuthenticationToken token, @RequestBody @Valid UserDtoInsertDescriptionInput userDto){
        UserDtoOutput userDtoOutput = userService.updateDescriptionUser(token, userDto);
        return ResponseEntity.ok(userDtoOutput);
    }

    @PatchMapping("/image")
    public ResponseEntity<UserDtoOutput> updateImageUser(JwtAuthenticationToken token, @RequestBody @Valid UserDtoInserImageUserInput userDto){
        UserDtoOutput userDtoOutput = userService.updateImageUser(token, userDto);
        return ResponseEntity.ok(userDtoOutput);
    }

    @PatchMapping("/wallpaper")
    public ResponseEntity<UserDtoOutput> updateWallpaperUser(JwtAuthenticationToken token, @RequestBody @Valid UserDtoInsertWallpaperUserInput userDto){
        UserDtoOutput userDtoOutput = userService.updateWallpaperUser(token, userDto);
        return ResponseEntity.ok(userDtoOutput);
    }

    @GetMapping 
    public ResponseEntity<List<UserDtoOutput>> listUsers(){
        List<UserDtoOutput> listUsers = userService.listUsers();
        return ResponseEntity.ok(listUsers);
    }

    @GetMapping("/pass") 
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<UserModel>> listUsersModel(){
        List<UserModel> listUsers = userRepository.findAll();
        return ResponseEntity.ok(listUsers);
    }

    @DeleteMapping("/location")
    public ResponseEntity deleteLocationUser(JwtAuthenticationToken token){
        userService.deleteLocationUser(token);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/wallpaper")
    public ResponseEntity deleteWallpaperUser(JwtAuthenticationToken token){
        userService.deleteWallpaperUser(token);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/image")
    public ResponseEntity deleteImageUser(JwtAuthenticationToken token){ 
        userService.deleteImageUser(token);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity deleteUser(JwtAuthenticationToken token, @RequestBody UserDtoDeleteInput userDto){
        userService.deleteUser(token, userDto);
        return ResponseEntity.noContent().build();
    }
    
}
