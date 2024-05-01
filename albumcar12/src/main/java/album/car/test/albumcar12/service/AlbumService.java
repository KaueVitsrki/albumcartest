package album.car.test.albumcar12.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import album.car.test.albumcar12.dto.albumDto.AlbumDtoCreateInput;
import album.car.test.albumcar12.dto.albumDto.AlbumDtoDeleteImageInput;
import album.car.test.albumcar12.dto.albumDto.AlbumDtoImageInput;
import album.car.test.albumcar12.dto.albumDto.AlbumDtoOutput;
import album.car.test.albumcar12.dto.albumDto.AlbumDtoUpdateDescriptionInput;
import album.car.test.albumcar12.dto.albumDto.AlbumDtoUpdateNameInput;
import album.car.test.albumcar12.exception.ImageExistException;
import album.car.test.albumcar12.exception.ImageNotExistException;
import album.car.test.albumcar12.exception.MaxListSizeException;
import album.car.test.albumcar12.exception.NameAlbumAlreadyExistsException;
import album.car.test.albumcar12.model.AlbumModel;
import album.car.test.albumcar12.model.UserModel;
import album.car.test.albumcar12.repository.AlbumRepository;
import album.car.test.albumcar12.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class AlbumService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public AlbumDtoOutput createAlbum(JwtAuthenticationToken token, AlbumDtoCreateInput albumDto){
        UserModel userLogged = userLogged(token);

        boolean nameAlbumExist = userLogged.getAlbum().stream()
        .anyMatch(album -> album.getName().equals(albumDto.getName()));

        if(nameAlbumExist){
            throw new NameAlbumAlreadyExistsException("Não foi possível criar o album! Já existe um album com o mesmo nome.");
        }

        AlbumModel albumModel = modelMapper.map(albumDto, AlbumModel.class);
        albumModel.setImage(new ArrayList<>());
        albumModel.setDiary(new ArrayList<>());
        albumModel.setHot(new ArrayList<>());
        albumModel.setUser(userLogged);
        userLogged.getAlbum().add(albumModel);
        userRepository.save(userLogged);
        AlbumDtoOutput albumOutput = modelMapper.map(albumModel, AlbumDtoOutput.class);

        return albumOutput;
    }

    @Transactional
    public AlbumDtoOutput insertImageAlbum(JwtAuthenticationToken token, AlbumDtoImageInput imageDto, UUID idAlbum){
        UserModel userLogged = userLogged(token);

        if(!albumRepository.existsById(idAlbum)){
            throw new EntityNotFoundException("Não foi possível adicionar a imagem! O album não existente");
        }

        AlbumModel albumModel = userLogged.getAlbum().stream()
        .filter(album -> album.getId().equals(idAlbum))
        .findFirst()
        .orElseThrow(() -> new NoSuchElementException("O usuário não possui nenhum album com o id fornecido"));

        if(albumModel.getImage().size() > 4){
            throw new MaxListSizeException("Não foi possível adicionar a imagem! Máximo de imagens: 4");
        }

        boolean imageExist = albumModel.getImage().stream()
        .anyMatch(image -> image.equals(imageDto.getImage()));

        if(imageExist){
            throw new ImageExistException("Não foi possível adicionar a imagem! Já existe outra imagem com o mesmo nome.");
        }
        
        albumModel.getImage().add(imageDto.getImage());
        albumRepository.save(albumModel);
        AlbumDtoOutput albumOutput = modelMapper.map(albumModel, AlbumDtoOutput.class);

        return albumOutput;
    }

    @Transactional
    public AlbumDtoOutput updateNameAlbum(JwtAuthenticationToken token, UUID idAlbum, AlbumDtoUpdateNameInput albumDto){
        UserModel userLogged = userLogged(token);

        if(!albumRepository.existsById(idAlbum)){
            throw new EntityNotFoundException("Não foi possível atualizar o campo! O album não existente");
        }

        boolean nameAlbumExist = userLogged.getAlbum().stream()
        .anyMatch(album -> album.getName().equals(albumDto.getName()));

        if(nameAlbumExist){
            throw new NameAlbumAlreadyExistsException("Não foi possível atualizar o album! Já existe um album com o mesmo nome.");
        }

        AlbumModel albumModel = userLogged.getAlbum().stream()
        .filter(album -> album.getId().equals(idAlbum))
        .findFirst()
        .orElseThrow(() -> new NoSuchElementException("Não foi possível encontrar o album"));

        albumModel.setName(albumDto.getName());
        albumRepository.save(albumModel);
        AlbumDtoOutput albumDtoOutput = modelMapper.map(albumModel, AlbumDtoOutput.class);

        return albumDtoOutput;
    }

    @Transactional
    public AlbumDtoOutput updateDescriptionAlbum(JwtAuthenticationToken token, UUID idAlbum, AlbumDtoUpdateDescriptionInput albumDto){
        UserModel userLogged = userLogged(token);

        if(!albumRepository.existsById(idAlbum)){
            throw new EntityNotFoundException("Não foi possível atualizar o campo! O album não existente");
        }

        AlbumModel albumModel = userLogged.getAlbum().stream()
        .filter(album -> album.getId().equals(idAlbum))
        .findFirst()
        .orElseThrow(() -> new NoSuchElementException("Não foi possível encontrar o album"));

        albumModel.setDescription(albumDto.getDescription());
        albumRepository.save(albumModel);
        AlbumDtoOutput albumDtoOutput = modelMapper.map(albumModel, AlbumDtoOutput.class);

        return albumDtoOutput;
    }

    public List<AlbumDtoOutput> listAlbum(JwtAuthenticationToken token){
        UserModel userLogged = userLogged(token);
        List<AlbumModel> listAlbum = userLogged.getAlbum();

        return AlbumDtoOutput.convert(listAlbum);
    }

    @Transactional
    public void deleteImageAlbum(JwtAuthenticationToken token, UUID idAlbum, AlbumDtoDeleteImageInput imageDto){
        UserModel userLogged = userLogged(token);

        if(!albumRepository.existsById(idAlbum)){
            throw new EntityNotFoundException("Não foi possível deletar a imagem! O album não existente");
        }

        AlbumModel albumModel = userLogged.getAlbum().stream()
        .filter(album -> album.getId().equals(idAlbum))
        .findFirst()
        .orElseThrow(() -> new NoSuchElementException("Não foi possível encontrar o album"));

        boolean imageExist = albumModel.getImage().stream()
        .anyMatch(image -> image.equals(imageDto.getImage()));

        if(!imageExist){
            throw new ImageNotExistException("Não foi possível remover a imagem! Nenhuma imagem foi encontrada");
        }

        albumModel.getImage().remove(imageDto.getImage());
        albumRepository.save(albumModel);
    }

    @Transactional
    public void deleteAlbum(JwtAuthenticationToken token, UUID idAlbum){
        UserModel userLogged = userLogged(token);

        if(!albumRepository.existsById(idAlbum)){
            throw new EntityNotFoundException("Não foi possível deletar o album! O album não existente");
        }

        AlbumModel albumToDelete = userLogged.getAlbum().stream()
        .filter(album -> album.getId().equals(idAlbum))
        .findFirst()
        .orElseThrow(() -> new NoSuchElementException("Não foi possível encontrar o album"));

        userLogged.getAlbum().remove(albumToDelete);
        userRepository.save(userLogged);
    }

    private UserModel userLogged(JwtAuthenticationToken token){        
        return userRepository.findById(UUID.fromString(token.getName()))
        .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    }
}   
