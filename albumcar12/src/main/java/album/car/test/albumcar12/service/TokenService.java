package album.car.test.albumcar12.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import album.car.test.albumcar12.dto.loginDto.LoginDtoInput;
import album.car.test.albumcar12.dto.loginDto.LoginDtoResponse;
import album.car.test.albumcar12.repository.UserRepository;

@Service
public class TokenService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtEncoder jwtEncoder;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public LoginDtoResponse login(LoginDtoInput loginDto){
        var user = userRepository.findUserByEmail(loginDto.getEmail());

        if(user.isEmpty() || !user.get().isLoginCorrect(loginDto, passwordEncoder)){
            throw new BadCredentialsException("Usuário ou senha inválidos");
        }

        var now = Instant.now();
        var expiresIn = 300L;

        var claims = JwtClaimsSet.builder()
                .issuer("albumcar12")
                .subject(user.get().getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        LoginDtoResponse loginResponse = new LoginDtoResponse(jwtValue, expiresIn);

        return loginResponse;
    }
}
