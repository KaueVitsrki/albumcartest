package album.car.test.albumcar12.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
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

    @PostMapping 
    public ResponseEntity<AlbumDtoOutput> createAlbum(JwtAuthenticationToken token, @RequestBody @Valid AlbumDtoCreateInput albumDto){
        AlbumDtoOutput createAlbum = albumService.createAlbum(token, albumDto);
        return new ResponseEntity<AlbumDtoOutput>(createAlbum, HttpStatus.CREATED);
    }

    @PostMapping("/image/{idAlbum}")
    public ResponseEntity<AlbumDtoOutput> insertImageAlbum(JwtAuthenticationToken token, @PathVariable UUID idAlbum, @RequestBody AlbumDtoImageInput imageDto){
        AlbumDtoOutput albumOutput = albumService.insertImageAlbum(token, imageDto, idAlbum);
        return ResponseEntity.ok(albumOutput);
    }

    @PatchMapping("/name/{idAlbum}")
    public ResponseEntity<AlbumDtoOutput> updateNameAlbum(JwtAuthenticationToken token, @PathVariable UUID idAlbum, @RequestBody @Valid AlbumDtoUpdateNameInput albumDto){
        AlbumDtoOutput albumDtoOutput = albumService.updateNameAlbum(token, idAlbum, albumDto);
        return ResponseEntity.ok(albumDtoOutput);
    }

    @PatchMapping("/description/{idAlbum}")
    public ResponseEntity<AlbumDtoOutput> updateDescriptionAlbum(JwtAuthenticationToken token, @PathVariable UUID idAlbum, @RequestBody @Valid AlbumDtoUpdateDescriptionInput albumDto){
        AlbumDtoOutput albumDtoOutput = albumService.updateDescriptionAlbum(token, idAlbum, albumDto);
        return ResponseEntity.ok(albumDtoOutput);
    }

    @GetMapping
    public ResponseEntity<List<AlbumDtoOutput>> listAlbumUser(JwtAuthenticationToken token){
        List<AlbumDtoOutput> listAlbumUser = albumService.listAlbum(token);
        return ResponseEntity.ok(listAlbumUser);
    } 

    @DeleteMapping("/image/{idAlbum}")
    public ResponseEntity deleteImageAlbum(JwtAuthenticationToken token, @PathVariable UUID idAlbum, @RequestBody @Valid AlbumDtoDeleteImageInput imageDto){
        albumService.deleteImageAlbum(token, idAlbum, imageDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{idAlbum}")
    public ResponseEntity deleteAlbum(JwtAuthenticationToken token, @PathVariable UUID idAlbum){
        albumService.deleteAlbum(token, idAlbum);
        return ResponseEntity.noContent().build();
    }
}
