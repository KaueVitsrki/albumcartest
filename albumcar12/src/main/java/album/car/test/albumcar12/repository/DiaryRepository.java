package album.car.test.albumcar12.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import album.car.test.albumcar12.model.DiaryModel;

public interface DiaryRepository extends JpaRepository<DiaryModel, UUID> {
    
}
