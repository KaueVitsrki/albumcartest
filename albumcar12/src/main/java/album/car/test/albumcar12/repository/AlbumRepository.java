package album.car.test.albumcar12.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import album.car.test.albumcar12.model.AlbumModel;


public interface AlbumRepository extends JpaRepository<AlbumModel, UUID>{
    AlbumModel findAlbumById(UUID id);
}
