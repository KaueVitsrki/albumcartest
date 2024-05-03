package album.car.test.albumcar12.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import album.car.test.albumcar12.model.UserModel;
import album.car.test.albumcar12.model.UserRole;

public interface UserRepository extends JpaRepository<UserModel, UUID>{
    UserModel findUserById(UUID id);
    Optional<UserModel> findUserByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByRole(UserRole role);
}
