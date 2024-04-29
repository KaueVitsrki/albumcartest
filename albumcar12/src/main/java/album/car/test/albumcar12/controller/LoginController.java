package album.car.test.albumcar12.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import album.car.test.albumcar12.dto.loginDto.LoginDtoInput;
import album.car.test.albumcar12.dto.loginDto.LoginDtoResponse;
import album.car.test.albumcar12.service.TokenService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class LoginController {
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginDtoResponse> login(@RequestBody @Valid LoginDtoInput loginDto){
        LoginDtoResponse loginDtoResponse = tokenService.login(loginDto);
        return ResponseEntity.ok(loginDtoResponse);
    }
}
