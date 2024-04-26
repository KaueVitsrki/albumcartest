package album.car.test.albumcar12.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import album.car.test.albumcar12.model.HotModel;

public interface HotRepository extends JpaRepository<HotModel, UUID>{
}
