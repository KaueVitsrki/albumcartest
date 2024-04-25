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

import album.car.test.albumcar12.dto.diaryDto.DiaryDtoCreateInput;
import album.car.test.albumcar12.dto.diaryDto.DiaryDtoDeleteImageInput;
import album.car.test.albumcar12.dto.diaryDto.DiaryDtoInsertImageInput;
import album.car.test.albumcar12.dto.diaryDto.DiaryDtoOutput;
import album.car.test.albumcar12.dto.diaryDto.DiaryDtoUpdateTextInput;
import album.car.test.albumcar12.service.DiaryService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/diaries")
public class DiaryController {
    @Autowired
    private DiaryService diaryService;

    @PostMapping("{idUser}/{idAlbum}")
    public ResponseEntity<DiaryDtoOutput> createDiary(@PathVariable UUID idUser, @PathVariable UUID idAlbum, @RequestBody @Valid DiaryDtoCreateInput diaryDto){
        DiaryDtoOutput createDiary = diaryService.createDiary(idUser, idAlbum, diaryDto);
        return new ResponseEntity<DiaryDtoOutput>(createDiary, HttpStatus.CREATED);
    }

    @PostMapping("/image/{idUser}/{idAlbum}/{idDiary}")
    public ResponseEntity<DiaryDtoOutput> insertImageDiary(@PathVariable UUID idUser, @PathVariable UUID idAlbum, @PathVariable UUID idDiary, @RequestBody @Valid DiaryDtoInsertImageInput diaryDto){
        DiaryDtoOutput diaryDtoOutput = diaryService.insertImageDiary(idUser, idAlbum, idDiary, diaryDto);
        return ResponseEntity.ok(diaryDtoOutput);
    }

    @PatchMapping("/text/{idUser}/{idAlbum}/{idDiary}")
    public ResponseEntity<DiaryDtoOutput> updateTextDiary(@PathVariable UUID idUser, @PathVariable UUID idAlbum, @PathVariable UUID idDiary, @RequestBody @Valid DiaryDtoUpdateTextInput diaryDto){
        DiaryDtoOutput diaryDtoOutput = diaryService.updateTextDiary(idUser, idAlbum, idDiary, diaryDto);
        return ResponseEntity.ok(diaryDtoOutput);
    }

    @GetMapping("/{idUser}/{idAlbum}")
    public ResponseEntity<List<DiaryDtoOutput>> listDiaryAlbum(@PathVariable UUID idUser, @PathVariable UUID idAlbum){
        List<DiaryDtoOutput> listDiaryAlbum = diaryService.listDiaryAlbum(idUser, idAlbum);
        return ResponseEntity.ok(listDiaryAlbum);
    }

    @DeleteMapping("/image/{idUser}/{idAlbum}/{idDiary}")
    public ResponseEntity deleteImageDiary(@PathVariable UUID idUser, @PathVariable UUID idAlbum, @PathVariable UUID idDiary, @RequestBody @Valid DiaryDtoDeleteImageInput diaryDto){
        diaryService.deleteImageDiary(idUser, idAlbum, idDiary, diaryDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{idUser}/{idAlbum}/{idDiary}")
    public ResponseEntity deleteDiary(@PathVariable UUID idUser, @PathVariable UUID idAlbum, @PathVariable UUID idDiary){
        diaryService.deleteDiary(idUser, idAlbum, idDiary);
        return ResponseEntity.noContent().build();
    }
}
