package album.car.test.albumcar12.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import album.car.test.albumcar12.dto.albumDto.AlbumDtoCreateInput;
import album.car.test.albumcar12.dto.albumDto.AlbumDtoDeleteImageInput;
import album.car.test.albumcar12.dto.albumDto.AlbumDtoImageInput;
import album.car.test.albumcar12.dto.albumDto.AlbumDtoOutput;
import album.car.test.albumcar12.dto.albumDto.AlbumDtoUpdateInput;
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
    public AlbumDtoOutput createAlbum(UUID idUser, AlbumDtoCreateInput albumDto){
        if(!userRepository.existsById(idUser)){
            throw new EntityNotFoundException("Não foi possível criar um novo album, usuário não existente");
        }

        UserModel user = userRepository.findUserById(idUser);
        boolean nameAlbumExist = user.getAlbum().stream()
        .anyMatch(album -> album.getName().equals(albumDto.getName()));

        if(nameAlbumExist){
            throw new NameAlbumAlreadyExistsException("Não foi possível criar o album! Já existe um album com o mesmo nome.");
        }

        AlbumModel albumModel = modelMapper.map(albumDto, AlbumModel.class);
        albumModel.setImage(new ArrayList<>());
        albumModel.setDiary(new ArrayList<>());
        albumModel.setHot(new ArrayList<>());
        albumModel.setUser(user);
        user.getAlbum().add(albumModel);
        userRepository.save(user);
        AlbumDtoOutput albumOutput = modelMapper.map(albumModel, AlbumDtoOutput.class);

        return albumOutput;
    }

    @Transactional
    public AlbumDtoOutput insertImage(AlbumDtoImageInput imageDto, UUID idUser, UUID idAlbum){
        if(!userRepository.existsById(idUser)){
            throw new EntityNotFoundException("Não foi possível adicionar a imagem! O usuário não existe");
        }
        if(!albumRepository.existsById(idAlbum)){
            throw new EntityNotFoundException("Não foi possível adicionar a imagem! O album não existente");
        }

        UserModel user = userRepository.findUserById(idUser);
        AlbumModel albumModel = user.getAlbum().stream()
        .filter(album -> album.getId().equals(idAlbum))
        .findFirst()
        .orElseThrow(() -> new NoSuchElementException("O usuário fornecido não possui nenhum album correspondente a o id fornecido"));
        List<String> listImage = imageDto.getImage();
        listImage.stream()
        .map(image -> albumModel.getImage().add(image))
        .collect(Collectors.toList());

        albumRepository.save(albumModel);
        AlbumDtoOutput albumOutput = modelMapper.map(albumModel, AlbumDtoOutput.class);

        return albumOutput;
    }

    public List<AlbumDtoOutput> listAlbum(UUID idUser){
        if(!userRepository.existsById(idUser)){
            throw new EntityNotFoundException("Não foi possível criar um novo album, usuário não existente");
        }

        UserModel user = userRepository.findUserById(idUser);
        List<AlbumModel> listAlbum = user.getAlbum();

        return AlbumDtoOutput.convert(listAlbum);
    }

    @Transactional
    public AlbumDtoOutput updateAlbum(UUID idUser, UUID idAlbum, AlbumDtoUpdateInput albumDto){
        if(!userRepository.existsById(idUser)){
            throw new EntityNotFoundException("Não foi possível atualizar o campo! O usuário não existe");
        }
        if(!albumRepository.existsById(idAlbum)){
            throw new EntityNotFoundException("Não foi possível adicionar a imagem! O album não existente");
        }

        UserModel user = userRepository.findUserById(idUser);
        AlbumModel albumModel = user.getAlbum().stream()
        .filter(album -> album.getId().equals(idAlbum))
        .findFirst()
        .orElseThrow(() -> new NoSuchElementException("Não foi possível encontrar o album"));

        if(albumDto.getName() == null){
            albumDto.setName(albumModel.getName());
        }
        if(albumDto.getDescription() == null){
            albumDto.setDescription(albumModel.getDescription());
        }

        albumModel.setName(albumDto.getName());
        albumModel.setDescription(albumDto.getDescription());
        albumRepository.save(albumModel);
        AlbumDtoOutput albumDtoOutput = modelMapper.map(albumModel, AlbumDtoOutput.class);

        return albumDtoOutput;
    }

    @Transactional
    public void deleteImageAlbum(UUID idUser, UUID idAlbum, AlbumDtoDeleteImageInput imageDto){
        if(!userRepository.existsById(idUser)){
            throw new EntityNotFoundException("Não foi possível deletar o album, usuário não existente");
        }
        if(!albumRepository.existsById(idAlbum)){
            throw new EntityNotFoundException("Não foi possível adicionar a imagem! O album não existente");
        }

        UserModel user = userRepository.findUserById(idUser);
        AlbumModel albumModel = user.getAlbum().stream()
        .filter(album -> album.getId().equals(idAlbum))
        .findFirst()
        .orElseThrow(() -> new NoSuchElementException("Não foi possível encontrar o album"));

        List<String> imageDelete = albumModel.getImage().stream()
        .filter(image -> !image.equals(imageDto.getImage()))
        .collect(Collectors.toList());

        albumModel.setImage(imageDelete);
        albumRepository.save(albumModel);
    }

    @Transactional
    public void deleteAlbumById(UUID idUser, UUID idAlbum){
        if(!userRepository.existsById(idUser)){
            throw new EntityNotFoundException("Não foi possível deletar o album, usuário não existente");
        }
        if(!albumRepository.existsById(idAlbum)){
            throw new EntityNotFoundException("Não foi possível adicionar a imagem! O album não existente");
        }

        UserModel user = userRepository.findUserById(idUser);
        AlbumModel albumToDelete = user.getAlbum().stream()
        .filter(album -> album.getId().equals(idAlbum))
        .findFirst()
        .orElseThrow(() -> new NoSuchElementException("Não foi possível encontrar o album"));

        user.getAlbum().remove(albumToDelete);
        userRepository.save(user);
    }
}   
