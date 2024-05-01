package album.car.test.albumcar12.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import album.car.test.albumcar12.dto.diaryDto.DiaryDtoCreateInput;
import album.car.test.albumcar12.dto.diaryDto.DiaryDtoDeleteImageInput;
import album.car.test.albumcar12.dto.diaryDto.DiaryDtoInsertImageInput;
import album.car.test.albumcar12.dto.diaryDto.DiaryDtoInsertYoutubeVideo;
import album.car.test.albumcar12.dto.diaryDto.DiaryDtoOutput;
import album.car.test.albumcar12.dto.diaryDto.DiaryDtoUpdateTextInput;
import album.car.test.albumcar12.exception.ImageExistException;
import album.car.test.albumcar12.exception.MaxListSizeException;
import album.car.test.albumcar12.exception.YoutubeVideoNotExistException;
import album.car.test.albumcar12.model.AlbumModel;
import album.car.test.albumcar12.model.DiaryModel;
import album.car.test.albumcar12.model.UserModel;
import album.car.test.albumcar12.repository.AlbumRepository;
import album.car.test.albumcar12.repository.DiaryRepository;
import album.car.test.albumcar12.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class DiaryService {
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private DiaryRepository diaryRepository;

    @Transactional
    public DiaryDtoOutput createDiary(JwtAuthenticationToken token, UUID idAlbum, DiaryDtoCreateInput diaryDto){
        UserModel userLogged = userLogged(token);

        if(!albumRepository.existsById(idAlbum)){
            throw new EntityNotFoundException("Não foi possível criar o diary! O album não existe");
        }

        AlbumModel albumUser = userLogged.getAlbum().stream()
        .filter(album -> album.getId().equals(idAlbum))
        .findFirst()
        .orElseThrow(() -> new NoSuchElementException("Não foi possível encontrar o album"));
        DiaryModel diaryModel = modelMapper.map(diaryDto, DiaryModel.class);

        diaryModel.setUser(userLogged); 
        diaryModel.setAlbum(albumUser);
        albumUser.getDiary().add(diaryModel);
        albumRepository.save(albumUser);
        userRepository.save(userLogged);
        DiaryDtoOutput diaryConvertDto = modelMapper.map(diaryModel, DiaryDtoOutput.class);
        
        return diaryConvertDto;
    }

    @Transactional
    public DiaryDtoOutput insertYoutubeVideo(JwtAuthenticationToken token, UUID idAlbum, UUID idDiary, DiaryDtoInsertYoutubeVideo youtubeVideo){
        UserModel userLogged = userLogged(token);

        if(!albumRepository.existsById(idAlbum)){
            throw new EntityNotFoundException("Não foi possível inserir o video! O album não existe");
        }
        if(!diaryRepository.existsById(idDiary)){
            throw new EntityNotFoundException("Não foi possível inserir o video! O diary não existe");
        }

        AlbumModel albumUser = userLogged.getAlbum().stream()
        .filter(album -> album.getId().equals(idAlbum))
        .findFirst()
        .orElseThrow(() -> new NoSuchElementException("Não foi possível encontrar o album"));
        DiaryModel diaryModel = albumUser.getDiary().stream()
        .filter(diary -> diary.getId().equals(idDiary))
        .findFirst()
        .orElseThrow(() -> new NoSuchElementException("Não foi possível encontrar o diary"));

        diaryModel.setYoutubeVideo(youtubeVideo.getYoutubeVideo());
        diaryRepository.save(diaryModel);
        DiaryDtoOutput diaryDtoOutput = modelMapper.map(diaryModel, DiaryDtoOutput.class);
        
        return diaryDtoOutput; 
    }

    @Transactional
    public DiaryDtoOutput insertImageDiary(JwtAuthenticationToken token, UUID idAlbum, UUID idDiary, DiaryDtoInsertImageInput diaryDto){
        UserModel userLogged = userLogged(token);

        if(!albumRepository.existsById(idAlbum)){
            throw new EntityNotFoundException("Não foi possível inserir a imagem! O album não existe");
        }
        if(!diaryRepository.existsById(idDiary)){
            throw new EntityNotFoundException("Não foi possível inserir a imagem! O diary não existe");
        }

        AlbumModel albumUser = userLogged.getAlbum().stream()
        .filter(album -> album.getId().equals(idAlbum))
        .findFirst()
        .orElseThrow(() -> new NoSuchElementException("Não foi possível encontrar o album"));
        DiaryModel diaryModel = albumUser.getDiary().stream()
        .filter(diary -> diary.getId().equals(idDiary))
        .findFirst()
        .orElseThrow(() -> new NoSuchElementException("Não foi possível encontrar o diary"));

        if(diaryModel.getImages().size() > 5){
            throw new MaxListSizeException("Não foi possível adicionar a imagem! Máximo de imagens: 4");
        }

        boolean existImage = diaryModel.getImages().stream()
        .anyMatch(image -> image.equals(diaryDto.getImages()));

        if(existImage){
            throw new ImageExistException("Não foi possível adicionar a imagem! Ela já existe");
        }

        diaryModel.getImages().add(diaryDto.getImages());
        diaryRepository.save(diaryModel);
        DiaryDtoOutput diaryDtoOutput = modelMapper.map(diaryModel, DiaryDtoOutput.class);

        return diaryDtoOutput;
    }

    @Transactional
    public DiaryDtoOutput updateTextDiary(JwtAuthenticationToken token, UUID idAlbum, UUID idDiary, DiaryDtoUpdateTextInput diaryDto){
        UserModel userLogged = userLogged(token);

        if(!albumRepository.existsById(idAlbum)){
            throw new EntityNotFoundException("Não foi possível atualizar o diary! O album não existe");
        }
        if(!diaryRepository.existsById(idDiary)){
            throw new EntityNotFoundException("Não foi possível atualizar o diary! O diary não existe");
        }

        AlbumModel albumUser = userLogged.getAlbum().stream()
        .filter(album -> album.getId().equals(idAlbum))
        .findFirst()
        .orElseThrow(() -> new NoSuchElementException("Não foi possível encontrar o album"));
        DiaryModel diaryModel = albumUser.getDiary().stream()
        .filter(diary -> diary.getId().equals(idDiary))
        .findFirst()
        .orElseThrow(() -> new NoSuchElementException("Não foi possível encontrar o diary"));

        diaryModel.setText(diaryDto.getText());
        diaryRepository.save(diaryModel);
        DiaryDtoOutput diaryDtoOutput = modelMapper.map(diaryModel, DiaryDtoOutput.class);
        
        return diaryDtoOutput;
    }

    public List<DiaryDtoOutput> listDiaryAlbum(JwtAuthenticationToken token, UUID idAlbum){
        UserModel userLogged = userLogged(token);

        if(!albumRepository.existsById(idAlbum)){
            throw new EntityNotFoundException("Não foi possível listar os diary! O album não existe");
        }

        AlbumModel album = albumRepository.findAlbumById(idAlbum);
        List<DiaryModel> listModel = album.getDiary();

        return DiaryDtoOutput.convert(listModel);
    }

    @Transactional
    public void deleteImageDiary(JwtAuthenticationToken token, UUID idAlbum, UUID idDiary, DiaryDtoDeleteImageInput diaryDto){
        UserModel userLogged = userLogged(token);

        if(!albumRepository.existsById(idAlbum)){
            throw new EntityNotFoundException("Não foi possível deletar a imagem! O album não existe");
        }
        if(!diaryRepository.existsById(idDiary)){
            throw new EntityNotFoundException("Não foi possível deletar a imagem! O diary não existe");
        }
        
        AlbumModel albumUser = userLogged.getAlbum().stream()
        .filter(album -> album.getId().equals(idAlbum))
        .findFirst()
        .orElseThrow(() -> new NoSuchElementException("Não foi possível encontrar o album"));
        DiaryModel diaryModel = albumUser.getDiary().stream()
        .filter(diary -> diary.getId().equals(idDiary))
        .findFirst()
        .orElseThrow(() -> new NoSuchElementException("Não foi possível encontrar o diary"));

        boolean existImage = diaryModel.getImages().stream()
        .anyMatch(image -> image.equals(diaryDto.getImages()));

        if(!existImage){
            throw new ImageExistException("Não foi possível deletar a imagem! Ela não existe");
        }

        diaryModel.getImages().remove(diaryDto.getImages());
        diaryRepository.save(diaryModel);
    }

    @Transactional
    public void deleteYoutubeVideo(JwtAuthenticationToken token, UUID idAlbum, UUID idDiary){
        UserModel userLogged = userLogged(token);

        if(!albumRepository.existsById(idAlbum)){
            throw new EntityNotFoundException("Não foi possível deletar o video! O album não existe");
        }
        if(!diaryRepository.existsById(idDiary)){
            throw new EntityNotFoundException("Não foi possível deletar o video! O diary não existe");
        }

        AlbumModel albumUser = userLogged.getAlbum().stream()
        .filter(album -> album.getId().equals(idAlbum))
        .findFirst()
        .orElseThrow(() -> new NoSuchElementException("Não foi possível encontrar o album"));
        DiaryModel diaryModel = albumUser.getDiary().stream()
        .filter(diary -> diary.getId().equals(idDiary))
        .findFirst()
        .orElseThrow(() -> new NoSuchElementException("Não foi possível encontrar o diary"));

        if(diaryModel.getYoutubeVideo() == null){
            throw new YoutubeVideoNotExistException("Não foi possível deletar o video! Nenhum video foi adicionado");
        }

        diaryModel.setYoutubeVideo(null);
        diaryRepository.save(diaryModel);
    }

    @Transactional 
    public void deleteDiary(JwtAuthenticationToken token, UUID idAlbum, UUID idDiary){
        UserModel userLogged = userLogged(token);

        if(!albumRepository.existsById(idAlbum)){
            throw new EntityNotFoundException("Não foi possível deletar o diary! O album não existe");
        }
        if(!diaryRepository.existsById(idDiary)){
            throw new EntityNotFoundException("Não foi possível deletar o diary! O diary não existe");
        }

        AlbumModel albumUser = userLogged.getAlbum().stream()
        .filter(album -> album.getId().equals(idAlbum))
        .findFirst()
        .orElseThrow(() -> new NoSuchElementException("Não foi possível encontrar o album"));
        DiaryModel diaryModel = albumUser.getDiary().stream()
        .filter(diary -> diary.getId().equals(idDiary))
        .findFirst()
        .orElseThrow(() -> new NoSuchElementException("Não foi possível encontrar o diary"));

        albumUser.getDiary().remove(diaryModel);
        albumRepository.save(albumUser);
    }

    private UserModel userLogged(JwtAuthenticationToken token){        
        return userRepository.findById(UUID.fromString(token.getName()))
        .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    }
}
