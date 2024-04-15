package album.car.test.albumcar12.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import album.car.test.albumcar12.model.UserModel;

public interface UserRepository extends JpaRepository<UserModel, UUID>{
    UserModel findUserByid(UUID id);
}
