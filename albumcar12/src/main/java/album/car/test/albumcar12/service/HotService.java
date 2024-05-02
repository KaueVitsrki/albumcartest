package album.car.test.albumcar12.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import album.car.test.albumcar12.dto.hotDto.HotDtoOutput;
import album.car.test.albumcar12.exception.ListIsEmptyException;
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
    public HotDtoOutput createHot(JwtAuthenticationToken token, UUID idAlbum){
        UserModel userLogged = userLogged(token);

        if(!albumRepository.existsById(idAlbum)){
            throw new EntityNotFoundException("Não foi possível criar o diary! O album não existe");
        }

        AlbumModel albumModel = albumRepository.findAlbumById(idAlbum);
        boolean userAlreadyRatedAlbum  = userLogged.getHot().stream().anyMatch(hot -> hot.getAlbum().getId().equals(albumModel.getId()));
        boolean albumAlreadyRatedByUser  = albumModel.getHot().stream().anyMatch(hot -> hot.getUser().getId().equals(userLogged.getId()));

        if(userAlreadyRatedAlbum || albumAlreadyRatedByUser){
            throw new UserGiveHotException("Não foi possível avaliar o album, você já o avaliou");
        }

        boolean userIsOwnerOfAlbum = albumModel.getUser().getId().equals(userLogged.getId());

        if(userIsOwnerOfAlbum){
            HotModel hotModel = new HotModel();
            hotModel.setAlbum(albumModel);
            hotModel.setUser(userLogged);
            userLogged.getHot().add(hotModel);
            albumModel.getHot().add(hotModel);
            albumModel.updateCountHot();
            userRepository.save(userLogged);
            albumRepository.save(albumModel);
            HotDtoOutput convertHot = modelMapper.map(hotModel, HotDtoOutput.class);
            
            return convertHot;
        }

        HotModel hotModel = new HotModel();
        hotModel.setAlbum(albumModel);
        hotModel.setUser(userLogged);
        albumModel.getHot().add(hotModel);
        albumModel.updateCountHot();
        albumRepository.save(albumModel);
        HotDtoOutput convertHot = modelMapper.map(hotModel, HotDtoOutput.class);

        return convertHot;
    }

    public List<String> nameAlbumHotUser(JwtAuthenticationToken token){
        UserModel userLogged = userLogged(token);

        List<String> userNameAlbumList = userLogged.getAlbum().stream()
        .map(AlbumModel::getName)
        .collect(Collectors.toList());

        if(userNameAlbumList.isEmpty()){
            throw new ListIsEmptyException("Você não avaliou nenhum album");
        }

        return userNameAlbumList;
    }

    @Transactional
    public void deleteHot(JwtAuthenticationToken token, UUID idAlbum){
        UserModel userLogged = userLogged(token);

        if(!albumRepository.existsById(idAlbum)){
            throw new EntityNotFoundException("Não foi possível remover o hot! O album não existe");
        }

        AlbumModel albumModel = albumRepository.findAlbumById(idAlbum);
        boolean userAlreadyRatedAlbum  = userLogged.getHot().stream().anyMatch(hot -> hot.getAlbum().getId().equals(albumModel.getId()));
        boolean albumAlreadyRatedByUser  = albumModel.getHot().stream().anyMatch(hot -> hot.getUser().getId().equals(userLogged.getId()));

        if(!userAlreadyRatedAlbum || !albumAlreadyRatedByUser){
            throw new UserGiveHotException("Não foi possível remover o hot, você não o avaliou");
        }

        HotModel removeHot = userLogged.getHot().stream()
        .filter(hot -> hot.getUser().getId().equals(userLogged.getId()) && hot.getAlbum().getId().equals(albumModel.getId()))
        .findFirst()
        .orElseThrow(() -> new NoSuchElementException("Não foi possível encontrar o hot"));

        userLogged.getHot().remove(removeHot);
        albumModel.getHot().remove(removeHot);
        albumModel.updateCountHot();
        userRepository.save(userLogged);
        albumRepository.save(albumModel);
    }

       private UserModel userLogged(JwtAuthenticationToken token){        
        return userRepository.findById(UUID.fromString(token.getName()))
        .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    }
}
