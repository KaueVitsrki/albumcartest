package album.car.test.albumcar12.service;

import java.util.NoSuchElementException;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import album.car.test.albumcar12.dto.hotDto.HotDto;
import album.car.test.albumcar12.exception.UserGiveHotException;
import album.car.test.albumcar12.model.AlbumModel;
import album.car.test.albumcar12.model.HotModel;
import album.car.test.albumcar12.model.UserModel;
import album.car.test.albumcar12.repository.AlbumRepository;
import album.car.test.albumcar12.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class HotService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public HotDto createHot(UUID idUser, UUID idAlbum){
        if(!userRepository.existsById(idUser)){
            throw new EntityNotFoundException("Não foi possível criar o diary! O usuário não existe");
        }
        if(!albumRepository.existsById(idAlbum)){
            throw new EntityNotFoundException("Não foi possível criar o diary! O album não existe");
        }

        UserModel user = userRepository.findUserById(idUser);
        AlbumModel albumModel = albumRepository.findAlbumById(idAlbum);
        boolean userAlreadyRatedAlbum  = user.getHot().stream().anyMatch(hot -> hot.getAlbum().getId().equals(albumModel.getId()));
        boolean albumAlreadyRatedByUser  = albumModel.getHot().stream().anyMatch(hot -> hot.getUser().getId().equals(user.getId()));

        if(userAlreadyRatedAlbum || albumAlreadyRatedByUser){
            throw new UserGiveHotException("Não foi possível avaliar o album, você já o avaliou");
        }

        HotModel hotModel = new HotModel();
        hotModel.setAlbum(albumModel);
        hotModel.setUser(user);
        user.getHot().add(hotModel);
        albumModel.getHot().add(hotModel);
        userRepository.save(user);
        albumRepository.save(albumModel);
        HotDto convertHot = modelMapper.map(hotModel, HotDto.class);
        
        return convertHot;
    }

    @Transactional
    public void deleteHot(UUID idUser, UUID idAlbum, UUID idHot){
        if(!userRepository.existsById(idUser)){
            throw new EntityNotFoundException("Não foi possível remover o hot! O usuário não existe");
        }
        if(!albumRepository.existsById(idAlbum)){
            throw new EntityNotFoundException("Não foi possível remover o hot! O album não existe");
        }

        UserModel user = userRepository.findUserById(idUser);
        AlbumModel albumModel = albumRepository.findAlbumById(idAlbum);
        boolean userAlreadyRatedAlbum  = user.getHot().stream().anyMatch(hot -> hot.getAlbum().getId().equals(albumModel.getId()));
        boolean albumAlreadyRatedByUser  = albumModel.getHot().stream().anyMatch(hot -> hot.getUser().getId().equals(user.getId()));

        if(!userAlreadyRatedAlbum || !albumAlreadyRatedByUser){
            throw new UserGiveHotException("Não foi possível remover o hot, você não o avaliou");
        }

        HotModel removeHot = user.getHot().stream()
        .filter(hot -> hot.getId().equals(idHot))
        .findFirst()
        .orElseThrow(() -> new NoSuchElementException("Não foi possível encontrar o hot"));

        user.getHot().remove(removeHot);
        albumModel.getHot().remove(removeHot);
        userRepository.save(user);
        albumRepository.save(albumModel);
    }

}
