package album.car.test.albumcar12.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import album.car.test.albumcar12.dto.DiaryDto;
import album.car.test.albumcar12.service.DiaryService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/diaries")
public class DiaryController {
    @Autowired
    private DiaryService diaryService;

    @PostMapping("{idUser}/{idAlbum}")
    public ResponseEntity<DiaryDto> createDiary(@PathVariable UUID idUser, @PathVariable UUID idAlbum, @RequestBody @Valid DiaryDto diaryDto){
        DiaryDto createDiary = diaryService.createDiary(idUser, idAlbum, diaryDto);
        return new ResponseEntity<DiaryDto>(createDiary, HttpStatus.CREATED);
    }

    @GetMapping("/{idUser}/{idAlbum}")
    public ResponseEntity<List<DiaryDto>> listDiaryAlbum(@PathVariable UUID idUser, @PathVariable UUID idAlbum){
        List<DiaryDto> listDiaryAlbum = diaryService.listDiaryAlbum(idUser, idAlbum);
        return ResponseEntity.ok(listDiaryAlbum);
    }
}
