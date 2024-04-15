package album.car.test.albumcar12.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import album.car.test.albumcar12.dto.UserDto;
import album.car.test.albumcar12.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto userDto){
        UserDto createUser = userService.createUser(userDto);
        return new ResponseEntity<UserDto>(createUser, HttpStatus.CREATED);
    }

    @GetMapping 
    public ResponseEntity<List<UserDto>> listUsers(){
        List<UserDto> listUsers = userService.listUsers();
        return ResponseEntity.ok(listUsers);
    }
    
}
