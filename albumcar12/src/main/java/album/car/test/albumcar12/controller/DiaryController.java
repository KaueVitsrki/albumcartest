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

import album.car.test.albumcar12.dto.diaryDto.DiaryDtoCreateInput;
import album.car.test.albumcar12.dto.diaryDto.DiaryDtoDeleteImageInput;
import album.car.test.albumcar12.dto.diaryDto.DiaryDtoInsertImageInput;
import album.car.test.albumcar12.dto.diaryDto.DiaryDtoInsertYoutubeVideo;
import album.car.test.albumcar12.dto.diaryDto.DiaryDtoOutput;
import album.car.test.albumcar12.dto.diaryDto.DiaryDtoUpdateTextInput;
import album.car.test.albumcar12.service.DiaryService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/diaries")
public class DiaryController {
    @Autowired
    private DiaryService diaryService;

    @PostMapping("{idAlbum}")
    public ResponseEntity<DiaryDtoOutput> createDiary(JwtAuthenticationToken token, @PathVariable UUID idAlbum, @RequestBody @Valid DiaryDtoCreateInput diaryDto){
        DiaryDtoOutput createDiary = diaryService.createDiary(token, idAlbum, diaryDto);
        return new ResponseEntity<DiaryDtoOutput>(createDiary, HttpStatus.CREATED);
    }

    @PostMapping("/image/{idAlbum}/{idDiary}")
    public ResponseEntity<DiaryDtoOutput> insertImageDiary(JwtAuthenticationToken token, @PathVariable UUID idAlbum, @PathVariable UUID idDiary, @RequestBody @Valid DiaryDtoInsertImageInput diaryDto){
        DiaryDtoOutput diaryDtoOutput = diaryService.insertImageDiary(token, idAlbum, idDiary, diaryDto);
        return ResponseEntity.ok(diaryDtoOutput);
    }

    @PostMapping("/video/{idAlbum}/{idDiary}")
    public ResponseEntity<DiaryDtoOutput> insertYoutubeVideo(JwtAuthenticationToken token, @PathVariable UUID idAlbum, @PathVariable UUID idDiary, @RequestBody @Valid DiaryDtoInsertYoutubeVideo diaryDto){
        DiaryDtoOutput diaryDtoOutput = diaryService.insertYoutubeVideo(token, idAlbum, idDiary, diaryDto);
        return ResponseEntity.ok(diaryDtoOutput);
    }

    @PatchMapping("/text/{idAlbum}/{idDiary}")
    public ResponseEntity<DiaryDtoOutput> updateTextDiary(JwtAuthenticationToken token, @PathVariable UUID idAlbum, @PathVariable UUID idDiary, @RequestBody @Valid DiaryDtoUpdateTextInput diaryDto){
        DiaryDtoOutput diaryDtoOutput = diaryService.updateTextDiary(token, idAlbum, idDiary, diaryDto);
        return ResponseEntity.ok(diaryDtoOutput);
    }

    @GetMapping("/{idAlbum}")
    public ResponseEntity<List<DiaryDtoOutput>> listDiaryAlbum(JwtAuthenticationToken token, @PathVariable UUID idAlbum){
        List<DiaryDtoOutput> listDiaryAlbum = diaryService.listDiaryAlbum(token, idAlbum);
        return ResponseEntity.ok(listDiaryAlbum);
    }

    @DeleteMapping("/video/{idAlbum}/{idDiary}")
    public ResponseEntity deleteYoutubeVideo(JwtAuthenticationToken token, @PathVariable UUID idAlbum, @PathVariable UUID idDiary){
        diaryService.deleteYoutubeVideo(token, idAlbum, idDiary);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/image/{idAlbum}/{idDiary}")
    public ResponseEntity deleteImageDiary(JwtAuthenticationToken token, @PathVariable UUID idAlbum, @PathVariable UUID idDiary, @RequestBody @Valid DiaryDtoDeleteImageInput diaryDto){
        diaryService.deleteImageDiary(token, idAlbum, idDiary, diaryDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{idAlbum}/{idDiary}")
    public ResponseEntity deleteDiary(JwtAuthenticationToken token, @PathVariable UUID idAlbum, @PathVariable UUID idDiary){
        diaryService.deleteDiary(token, idAlbum, idDiary);
        return ResponseEntity.noContent().build();
    }
}
