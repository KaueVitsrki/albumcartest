package album.car.test.albumcar12.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import album.car.test.albumcar12.dto.HotDto;
import album.car.test.albumcar12.exception.UserGiveHotException;
import album.car.test.albumcar12.model.AlbumModel;
import album.car.test.albumcar12.model.HotModel;
import album.car.test.albumcar12.model.UserModel;
import album.car.test.albumcar12.repository.AlbumRepository;
import album.car.test.albumcar12.repository.HotRepository;
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
    @Autowired
    private HotRepository hotRepository;

    @Transactional
    public HotDto createHot(UUID idUser, UUID idAlbum){
        if(!userRepository.existsById(idUser)){
            throw new EntityNotFoundException("Não foi possível criar o diary! O usuário não existe");
        }
        if(!albumRepository.existsById(idAlbum)){
            throw new EntityNotFoundException("Não foi possível criar o diary! O album não existe");
        }

        UserModel user = userRepository.findUserById(idUser);
        List<HotModel> userHot = user.getHot();
        boolean giveHot = userHot.stream().anyMatch(hot -> hot.getUser().getId().equals(user.getId()));

        if(giveHot){
            throw new UserGiveHotException("Não foi possível avaliar o album, você já o avaliou");
        }

        AlbumModel albumModel = albumRepository.findAlbumById(idAlbum);
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
            throw new EntityNotFoundException("Não foi possível criar o diary! O usuário não existe");
        }
        if(!albumRepository.existsById(idAlbum)){
            throw new EntityNotFoundException("Não foi possível criar o diary! O album não existe");
        }

        UserModel user = userRepository.findUserById(idUser);
        List<HotModel> userHot = user.getHot();
        boolean giveHot = userHot.stream().anyMatch(hot -> hot.getUser().getId().equals(user.getId()));

        if(!giveHot){
            throw new UserGiveHotException("Não foi possível retirar o hot, você não avaliou o album");
        }

        HotModel hotModel = hotRepository.findHotById(idHot);
        HotModel removeHot = userHot.stream()
        .filter(hot -> hot.getId().equals(hotModel.getId()))
        .findFirst()
        .orElseThrow(() -> new NoSuchElementException("Não foi possível encontrar o hot"));
        AlbumModel albumModel = albumRepository.findAlbumById(idAlbum);
        List<HotModel> hotAlbum = albumModel.getHot();

        userHot.remove(removeHot);
        hotAlbum.remove(removeHot);
        userRepository.save(user);
        albumRepository.save(albumModel);
    }

}
