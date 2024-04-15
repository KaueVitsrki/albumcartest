package album.car.test.albumcar12.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import album.car.test.albumcar12.dto.DiaryDto;
import album.car.test.albumcar12.model.AlbumModel;
import album.car.test.albumcar12.model.DiaryModel;
import album.car.test.albumcar12.model.UserModel;
import album.car.test.albumcar12.repository.AlbumRepository;
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

    @Transactional
    public DiaryDto createDiary(UUID idUser, UUID idAlbum, DiaryDto diaryDto){
        if(!userRepository.existsById(idUser)){
            throw new EntityNotFoundException("Não foi possível criar o diary! O usuário não existe");
        }
        if(!albumRepository.existsById(idAlbum)){
            throw new EntityNotFoundException("Não foi possível criar o diary! O album não existe");
        }

        UserModel user = userRepository.findUserByid(idUser);

        AlbumModel albumUser = user.getAlbum().stream()
        .filter(album -> album.getId().equals(idAlbum))
        .findFirst()
        .orElseThrow(() -> new NoSuchElementException("Não foi possível encontrar o album"));

        DiaryModel diaryModel = modelMapper.map(diaryDto, DiaryModel.class);

        diaryModel.setUser(user); 
        diaryModel.setAlbum(albumUser);
        albumUser.getDiary().add(diaryModel);
        albumRepository.save(albumUser);
        userRepository.save(user);

        DiaryDto diaryConvertDto = modelMapper.map(diaryModel, DiaryDto.class);
        
        return diaryConvertDto;
    }

    public List<DiaryDto> listDiaryAlbum(UUID idUser, UUID idAlbum){
        if(!userRepository.existsById(idUser)){
            throw new EntityNotFoundException("Não foi possível criar o diary! O usuário não existe");
        }
        if(!albumRepository.existsById(idAlbum)){
            throw new EntityNotFoundException("Não foi possível criar o diary! O album não existe");
        }

        AlbumModel album = albumRepository.findAlbumById(idAlbum);
        List<DiaryModel> listModel = album.getDiary();

        return DiaryDto.convert(listModel);
    }
}
