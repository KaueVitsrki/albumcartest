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

import album.car.test.albumcar12.dto.albumDto.AlbumDtoCreateInput;
import album.car.test.albumcar12.dto.albumDto.AlbumDtoDeleteImageInput;
import album.car.test.albumcar12.dto.albumDto.AlbumDtoImageInput;
import album.car.test.albumcar12.dto.albumDto.AlbumDtoOutput;
import album.car.test.albumcar12.dto.albumDto.AlbumDtoUpdateDescriptionInput;
import album.car.test.albumcar12.dto.albumDto.AlbumDtoUpdateNameInput;
import album.car.test.albumcar12.service.AlbumService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/albums")
public class AlbumController {
    @Autowired
    private AlbumService albumService;

    @PostMapping("/{idUser}") 
    public ResponseEntity<AlbumDtoOutput> createAlbum(@PathVariable UUID idUser, @RequestBody @Valid AlbumDtoCreateInput albumDto){
        AlbumDtoOutput createAlbum = albumService.createAlbum(idUser, albumDto);
        return new ResponseEntity<AlbumDtoOutput>(createAlbum, HttpStatus.CREATED);
    }

    @PostMapping("/image/{idUser}/{idAlbum}")
    public ResponseEntity<AlbumDtoOutput> insertImageAlbum(@PathVariable UUID idUser, @PathVariable UUID idAlbum, @RequestBody AlbumDtoImageInput imageDto){
        AlbumDtoOutput albumOutput = albumService.insertImageAlbum(imageDto, idUser, idAlbum);
        return ResponseEntity.ok(albumOutput);
    }

    @PatchMapping("/name/{idUser}/{idAlbum}")
    public ResponseEntity<AlbumDtoOutput> updateNameAlbum(@PathVariable UUID idUser, @PathVariable UUID idAlbum, @RequestBody @Valid AlbumDtoUpdateNameInput albumDto){
        AlbumDtoOutput albumDtoOutput = albumService.updateNameAlbum(idUser, idAlbum, albumDto);
        return ResponseEntity.ok(albumDtoOutput);
    }

    @PatchMapping("/description/{idUser}/{idAlbum}")
    public ResponseEntity<AlbumDtoOutput> updateDescriptionAlbum(@PathVariable UUID idUser, @PathVariable UUID idAlbum, @RequestBody @Valid AlbumDtoUpdateDescriptionInput albumDto){
        AlbumDtoOutput albumDtoOutput = albumService.updateDescriptionAlbum(idUser, idAlbum, albumDto);
        return ResponseEntity.ok(albumDtoOutput);
    }

    @GetMapping("/{idUser}")
    public ResponseEntity<List<AlbumDtoOutput>> listAlbumUser(@PathVariable UUID idUser){
        List<AlbumDtoOutput> listAlbumUser = albumService.listAlbum(idUser);
        return ResponseEntity.ok(listAlbumUser);
    } 

    @DeleteMapping("/image/{idUser}/{idAlbum}")
    public ResponseEntity<AlbumDtoOutput> deleteImageAlbum(@PathVariable UUID idUser, @PathVariable UUID idAlbum, @RequestBody @Valid AlbumDtoDeleteImageInput imageDto){
        albumService.deleteImageAlbum(idUser, idAlbum, imageDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{idUser}/{idAlbum}")
    public ResponseEntity deleteAlbum(@PathVariable UUID idUser, @PathVariable UUID idAlbum){
        albumService.deleteAlbum(idUser, idAlbum);
        return ResponseEntity.noContent().build();
    }
}
