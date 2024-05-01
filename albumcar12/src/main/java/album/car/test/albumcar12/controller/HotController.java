package album.car.test.albumcar12.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import album.car.test.albumcar12.dto.hotDto.HotDto;
import album.car.test.albumcar12.service.HotService;

@RestController
@RequestMapping("/hot")
public class HotController {
    @Autowired
    private HotService hotService;
    
    @PostMapping("/{idAlbum}")
    public ResponseEntity<HotDto> createHot(JwtAuthenticationToken token, @PathVariable UUID idAlbum){
        HotDto createHot = hotService.createHot(token, idAlbum);
        return new ResponseEntity<HotDto>(createHot, HttpStatus.CREATED);
    }

    @DeleteMapping("/{idAlbum}")
    public ResponseEntity deleteHot(JwtAuthenticationToken token, @PathVariable UUID idAlbum){
        hotService.deleteHot(token, idAlbum);
        return ResponseEntity.noContent().build();
    }
}
