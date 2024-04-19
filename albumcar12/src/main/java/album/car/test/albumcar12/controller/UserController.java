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
import album.car.test.albumcar12.dto.userDto.UserDtoInsertDescriptionInput;
import album.car.test.albumcar12.dto.userDto.UserDtoInsertLocationUserInput;
import album.car.test.albumcar12.dto.userDto.UserDtoOutput;
import album.car.test.albumcar12.dto.userDto.UserDtoUpdateLocationUserInput;
import album.car.test.albumcar12.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserDtoOutput> createUser(@RequestBody @Valid UserDtoCreateInput userDto){
        UserDtoOutput userDtoOutput = userService.createUser(userDto);
        return new ResponseEntity<UserDtoOutput>(userDtoOutput, HttpStatus.CREATED);
    }

    @PostMapping("/description/{idUser}")
    public ResponseEntity<UserDtoOutput> insertDescriptionUser(@PathVariable UUID idUser, @RequestBody UserDtoInsertDescriptionInput userDto){
        UserDtoOutput userDtoOutput = userService.insertDescriptionUser(idUser, userDto);
        return new ResponseEntity<UserDtoOutput>(userDtoOutput, HttpStatus.CREATED);
    }

    @PostMapping("/location/{idUser}")
    public ResponseEntity<UserDtoOutput> insertLocationUser(@RequestBody @Valid UserDtoInsertLocationUserInput userDto, @PathVariable UUID idUser){
        UserDtoOutput userDtoOutput = userService.insertLocationUser(idUser, userDto);
        return new ResponseEntity<UserDtoOutput>(userDtoOutput, HttpStatus.CREATED);
    }

    @PatchMapping("/location/{idUser}")
    public ResponseEntity<UserDtoOutput> updateLocationUser(@RequestBody UserDtoUpdateLocationUserInput userDto, @PathVariable UUID idUser){
        UserDtoOutput userDtoOutput = userService.updateLocationUser(idUser, userDto);
        return new ResponseEntity<UserDtoOutput>(userDtoOutput, HttpStatus.CREATED);
    }

    @PatchMapping("/description/{idUser}")
    public ResponseEntity<UserDtoOutput> descriptionUpdate(@PathVariable UUID idUser, @RequestBody UserDtoInsertDescriptionInput userDto){
        UserDtoOutput userDtoOutput = userService.updateDescriptionUser(idUser, userDto);
        return new ResponseEntity<UserDtoOutput>(userDtoOutput, HttpStatus.CREATED);
    }

    @GetMapping 
    public ResponseEntity<List<UserDtoOutput>> listUsers(){
        List<UserDtoOutput> listUsers = userService.listUsers();
        return ResponseEntity.ok(listUsers);
    }

    @DeleteMapping("/{idUser}")
    public ResponseEntity deleteUser(@PathVariable UUID idUser, @RequestBody UserDtoDeleteInput userDto){
        userService.deleteUser(idUser, userDto);
        return ResponseEntity.noContent().build();
    }
    
}
