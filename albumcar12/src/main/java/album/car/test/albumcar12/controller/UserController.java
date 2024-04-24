package album.car.test.albumcar12.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @PostMapping("/description/{idUser}")
    public ResponseEntity<UserDtoOutput> insertDescriptionUser(@PathVariable UUID idUser, @RequestBody @Valid UserDtoInsertDescriptionInput userDto){
        UserDtoOutput userDtoOutput = userService.insertDescriptionUser(idUser, userDto);
        return new ResponseEntity<UserDtoOutput>(userDtoOutput, HttpStatus.CREATED);
    }

    @PostMapping("/location/{idUser}")
    public ResponseEntity<UserDtoOutput> insertLocationUser(@PathVariable UUID idUser, @RequestBody @Valid UserDtoInsertLocationUserInput userDto){
        UserDtoOutput userDtoOutput = userService.insertLocationUser(idUser, userDto);
        return new ResponseEntity<UserDtoOutput>(userDtoOutput, HttpStatus.CREATED);
    }

    @PostMapping("/wallpaper/{idUser}")
    public ResponseEntity<UserDtoOutput> insertWallpaperUser(@PathVariable UUID idUser, @RequestBody @Valid UserDtoInsertWallpaperUserInput userDto){
        UserDtoOutput userDtoOutput = userService.insertWallpaperUser(idUser, userDto);
        return new ResponseEntity<UserDtoOutput>(userDtoOutput, HttpStatus.CREATED);
    }

    @PostMapping("/image/{idUser}")
    public ResponseEntity<UserDtoOutput> insertImageUser(@PathVariable UUID idUser, @RequestBody @Valid UserDtoInserImageUserInput userDto){
        UserDtoOutput userDtoOutput = userService.insertImageUser(idUser, userDto);
        return new ResponseEntity<UserDtoOutput>(userDtoOutput, HttpStatus.CREATED);
    }

    @PatchMapping("/name/{idUser}")
    public ResponseEntity<UserDtoOutput> updateNameUser(@PathVariable UUID idUser, @RequestBody @Valid UserDtoUpdateNameUserInput userDto){
        UserDtoOutput userDtoOutput = userService.updateNameUser(idUser, userDto);
        return ResponseEntity.ok(userDtoOutput);
    }

    @PatchMapping("/email/{idUser}")
    public ResponseEntity<UserDtoOutput> updateEmailUser(@PathVariable UUID idUser, @RequestBody @Valid UserDtoUpdateEmailUserInput userDto){
        UserDtoOutput userDtoOutput = userService.updateEmailUser(idUser, userDto);
        return ResponseEntity.ok(userDtoOutput);
    }

    @PatchMapping("/password/{idUser}")
    public ResponseEntity<UserDtoOutput> updatePasswordUser(@PathVariable UUID idUser, @RequestBody @Valid UserDtoUpdatePasswordUserInput userDto){
        UserDtoOutput userDtoOutput = userService.updatePasswordUser(idUser, userDto);
        return ResponseEntity.ok(userDtoOutput);
    }

    @PatchMapping("/location/{idUser}")
    public ResponseEntity<UserDtoOutput> updateLocationUser(@PathVariable UUID idUser, @RequestBody UserDtoUpdateLocationUserInput userDto){
        UserDtoOutput userDtoOutput = userService.updateLocationUser(idUser, userDto);
        return ResponseEntity.ok(userDtoOutput);
    }

    @PatchMapping("/description/{idUser}")
    public ResponseEntity<UserDtoOutput> updateDescriptionUser(@PathVariable UUID idUser, @RequestBody @Valid UserDtoInsertDescriptionInput userDto){
        UserDtoOutput userDtoOutput = userService.updateDescriptionUser(idUser, userDto);
        return ResponseEntity.ok(userDtoOutput);
    }

    @PatchMapping("/image/{idUser}")
    public ResponseEntity<UserDtoOutput> updateImageUser(@PathVariable UUID idUser, @RequestBody @Valid UserDtoInserImageUserInput userDto){
        UserDtoOutput userDtoOutput = userService.updateImageUser(idUser, userDto);
        return ResponseEntity.ok(userDtoOutput);
    }

    @PatchMapping("/wallpaper/{idUser}")
    public ResponseEntity<UserDtoOutput> updateWallpaperUser(@PathVariable UUID idUser, @RequestBody @Valid UserDtoInsertWallpaperUserInput userDto){
        UserDtoOutput userDtoOutput = userService.updateWallpaperUser(idUser, userDto);
        return ResponseEntity.ok(userDtoOutput);
    }

    @GetMapping 
    public ResponseEntity<List<UserDtoOutput>> listUsers(){
        List<UserDtoOutput> listUsers = userService.listUsers();
        return ResponseEntity.ok(listUsers);
    }

    @GetMapping("/pass") 
    public ResponseEntity<List<UserModel>> listUsersModel(){
        List<UserModel> listUsers = userRepository.findAll();
        return ResponseEntity.ok(listUsers);
    }

    @DeleteMapping("/location/{idUser}")
    public ResponseEntity deleteLocationUser(@PathVariable UUID idUser){
        userService.deleteLocationUser(idUser);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/wallpaper/{idUser}")
    public ResponseEntity deleteWallpaperUser(@PathVariable UUID idUser){
        userService.deleteWallpaperUser(idUser);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/image/{idUser}")
    public ResponseEntity deleteImageUser(@PathVariable UUID idUser){ 
        userService.deleteImageUser(idUser);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{idUser}")
    public ResponseEntity deleteUser(@PathVariable UUID idUser, @RequestBody UserDtoDeleteInput userDto){
        userService.deleteUser(idUser, userDto);
        return ResponseEntity.noContent().build();
    }
    
}
