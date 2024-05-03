package album.car.test.albumcar12.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import album.car.test.albumcar12.model.UserModel;
import album.car.test.albumcar12.model.UserRole;
import album.car.test.albumcar12.repository.UserRepository;
import jakarta.transaction.Transactional;

@Configuration
public class AdminConfig implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Value("${prv.pass.ad}")
    private String password;
    @Value("${prv.ema.ad}")
    private String email;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        boolean existAdmin = userRepository.existsByRole(UserRole.ADMIN);

        if(!existAdmin){
            UserModel userAdmin = new UserModel();
            userAdmin.setEmail(email);
            userAdmin.setName("admin");
            userAdmin.setPassword(passwordEncoder.encode(password));
            userAdmin.setRole(UserRole.ADMIN);
            userRepository.save(userAdmin);
        }

        System.out.println("Já existe um usuário admin");
    }
}
