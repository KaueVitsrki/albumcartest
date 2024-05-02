package album.car.test.albumcar12.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import album.car.test.albumcar12.dto.userDto.UserDtoCreateInput;
import album.car.test.albumcar12.dto.userDto.UserDtoDeleteInput;
import album.car.test.albumcar12.dto.userDto.UserDtoInserImageUserInput;
import album.car.test.albumcar12.dto.userDto.UserDtoInsertDescriptionInput;
import album.car.test.albumcar12.dto.userDto.UserDtoInsertLocationUserInput;
import album.car.test.albumcar12.dto.userDto.UserDtoInsertWallpaperUserInput;
import album.car.test.albumcar12.dto.userDto.UserDtoOutput;
import album.car.test.albumcar12.dto.userDto.UserDtoUpdateEmailUserInput;
import album.car.test.albumcar12.dto.userDto.UserDtoUpdateLocationUserInput;
import album.car.test.albumcar12.dto.userDto.UserDtoUpdateNameUserInput;
import album.car.test.albumcar12.dto.userDto.UserDtoUpdatePasswordUserInput;
import album.car.test.albumcar12.exception.DescriptionNotCreatedException;
import album.car.test.albumcar12.exception.EmailExistException;
import album.car.test.albumcar12.exception.FieldAlreadyCreatedException;
import album.car.test.albumcar12.exception.ImageNotCreatedException;
import album.car.test.albumcar12.exception.InvalidFieldsException;
import album.car.test.albumcar12.exception.InvalidPassword;
import album.car.test.albumcar12.model.UserModel;
import album.car.test.albumcar12.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordService passwordService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public UserDtoOutput createUser(UserDtoCreateInput userDto){
        UserModel userModel = modelMapper.map(userDto, UserModel.class);

        List<String> passwordStrings = passwordService.validate(userDto.getPassword());
        if(!passwordStrings.isEmpty()){
            throw new InvalidPassword(passwordStrings.toString());
        }

        String password = passwordEncoder.encode(userModel.getPassword());
        userModel.setPassword(password);
        userModel.setAlbum(new ArrayList<>());
        userModel.setDiary(new ArrayList<>());
        userModel.setHot(new ArrayList<>());
        UserModel createUser = userRepository.save(userModel);
        UserDtoOutput returnUser = modelMapper.map(createUser, UserDtoOutput.class);

        return returnUser;
    }

    @Transactional
    public UserDtoOutput insertDescriptionUser(JwtAuthenticationToken token, UserDtoInsertDescriptionInput userDto){
        UserModel userLogged = userLogged(token);

        if(userLogged.getDescription() != null){
            throw new FieldAlreadyCreatedException("Não foi possível criar o campo, pois ele já existe!");
        }

        userLogged.setDescription(userDto.getDescription());
        userRepository.save(userLogged);
        UserDtoOutput userDtoOutput = modelMapper.map(userLogged, UserDtoOutput.class);

        return userDtoOutput;
    }

    @Transactional 
    public UserDtoOutput insertLocationUser(JwtAuthenticationToken token, UserDtoInsertLocationUserInput userDto){
        UserModel userLogged = userLogged(token);

        if(userLogged.getCountry() != null){
            throw new FieldAlreadyCreatedException("Não foi possível criar o campo, pois ele já existe!");
        }

        userLogged.setCountry(userDto.getCountry());
        userLogged.setState(userDto.getState());
        userLogged.setCity(userDto.getCity());
        userRepository.save(userLogged);
        UserDtoOutput userDtoOutput = modelMapper.map(userLogged, UserDtoOutput.class);

        return userDtoOutput;
    }

    @Transactional 
    public UserDtoOutput insertImageUser(JwtAuthenticationToken token, UserDtoInserImageUserInput userDto){
        UserModel userLogged = userLogged(token);

        if(userLogged.getImage() != null){
            throw new FieldAlreadyCreatedException("Não foi possível criar o campo, pois ele já existe!");
        }

        userLogged.setImage(userDto.getImage());
        userRepository.save(userLogged);
        UserDtoOutput userDtoOutput = modelMapper.map(userLogged, UserDtoOutput.class);
        
        return userDtoOutput;
    }

    @Transactional
    public UserDtoOutput insertWallpaperUser(JwtAuthenticationToken token, UserDtoInsertWallpaperUserInput userDto){
        UserModel userLogged = userLogged(token);

        if(userLogged.getWallpaper() != null){
            throw new FieldAlreadyCreatedException("Não foi possível criar o campo, pois ele já existe!");
        }
        
        userLogged.setWallpaper(userDto.getWallpaper());
        userRepository.save(userLogged);
        UserDtoOutput userDtoOutput = modelMapper.map(userLogged, UserDtoOutput.class);

        return userDtoOutput;

    }

    @Transactional
    public UserDtoOutput updateNameUser(JwtAuthenticationToken token, UserDtoUpdateNameUserInput userDto){
        UserModel userLogged = userLogged(token);

        userLogged.setName(userDto.getName());
        userRepository.save(userLogged);
        UserDtoOutput userDtoOutput = modelMapper.map(userLogged, UserDtoOutput.class);

        return userDtoOutput;
    }

    @Transactional 
    public UserDtoOutput updateEmailUser(JwtAuthenticationToken token, UserDtoUpdateEmailUserInput userDto){
        UserModel userLogged = userLogged(token);

        if(userRepository.existsByEmail(userDto.getNewEmail())){
            throw new EmailExistException("Não foi possível atualizar o email! O email informado já foi cadastrado");
        }

        boolean passwordMatch = matches(userDto.getPassword(), userLogged.getPassword()); 
        boolean passwordConfirmationMatch = matches(userDto.getPasswordConfirmation(), userLogged.getPassword());

        if(!passwordMatch || !passwordConfirmationMatch || passwordMatch != passwordConfirmationMatch){
            throw new InvalidFieldsException("Um ou todos os campos estão inválidos");
        }

        userLogged.setEmail(userDto.getNewEmail());
        userRepository.save(userLogged);
        UserDtoOutput userDtoOutput = modelMapper.map(userLogged, UserDtoOutput.class);
        
        return userDtoOutput;
    }

    @Transactional
    public UserDtoOutput updatePasswordUser(JwtAuthenticationToken token, UserDtoUpdatePasswordUserInput userDto){
        UserModel userLogged = userLogged(token);

        boolean currentPasswordMatch = matches(userDto.getCurrentPassword(), userLogged.getPassword()); 
        
        if(!currentPasswordMatch){
            throw new InvalidPassword("Senha errada!");
        }
        if(!userDto.getNewPassword().equals(userDto.getNewPasswordConfirmation())){
            throw new InvalidFieldsException("Os campos possuem conteúdos diferentes");
        }

        List<String> passwordStrings = passwordService.validate(userDto.getNewPassword());
        if(!passwordStrings.isEmpty()){
            throw new InvalidPassword(passwordStrings.toString());
        }

        String newPassword = passwordEncoder.encode(userDto.getNewPassword());
        userLogged.setPassword(newPassword);
        userRepository.save(userLogged);
        UserDtoOutput userDtoOutput = modelMapper.map(userLogged, UserDtoOutput.class);

        return userDtoOutput;
    }

    @Transactional
    public UserDtoOutput updateDescriptionUser(JwtAuthenticationToken token, UserDtoInsertDescriptionInput userDto){
        UserModel userLogged = userLogged(token);

        if(userLogged.getDescription() == null){ // mudei de .equals(null)
            throw new DescriptionNotCreatedException("A descrição não foi criada!");
        }

        userLogged.setDescription(userDto.getDescription());
        userRepository.save(userLogged);
        UserDtoOutput userDtoOutput = modelMapper.map(userLogged, UserDtoOutput.class);

        return userDtoOutput;
    }

    @Transactional
    public UserDtoOutput updateLocationUser(JwtAuthenticationToken token, UserDtoUpdateLocationUserInput userDto){
        UserModel userLogged = userLogged(token);

        if(userLogged.getCountry() == null){
            throw new EntityNotFoundException("Adicione primeiro o país");
        }
        if(userDto.getCountry() == null){
            userDto.setCountry(userLogged.getCountry());
        }
        if(userDto.getState() == null){
            userDto.setState(userLogged.getState());
        }
        if(userDto.getCity() == null){
            userDto.setCity(userLogged.getCity());
        }

        userLogged.setCountry(userDto.getCountry());
        userLogged.setState(userDto.getState());
        userLogged.setCity(userDto.getCity());
        userRepository.save(userLogged);
        UserDtoOutput userDtoOutput = modelMapper.map(userLogged, UserDtoOutput.class);

        return userDtoOutput;
    }

    @Transactional 
    public UserDtoOutput updateImageUser(JwtAuthenticationToken token, UserDtoInserImageUserInput userDto){
        UserModel userLogged = userLogged(token);

        if(userLogged.getImage() == null){ 
            throw new ImageNotCreatedException("A imagem não foi adicionada!");
        }

        userLogged.setImage(userDto.getImage());
        userRepository.save(userLogged);
        UserDtoOutput userDtoOutput = modelMapper.map(userLogged, UserDtoOutput.class);
        
        return userDtoOutput;
    }

    @Transactional
    public UserDtoOutput updateWallpaperUser(JwtAuthenticationToken token, UserDtoInsertWallpaperUserInput userDto){
        UserModel userLogged = userLogged(token);

        if(userLogged.getWallpaper() == null){ 
            throw new ImageNotCreatedException("A imagem não foi adicionada!");
        }
       
        userLogged.setWallpaper(userDto.getWallpaper());
        userRepository.save(userLogged);
        UserDtoOutput userDtoOutput = modelMapper.map(userLogged, UserDtoOutput.class);

        return userDtoOutput;
    }

    public List<UserDtoOutput> listUsers(){
        List<UserModel> listUsers = userRepository.findAll();
        return UserDtoOutput.convert(listUsers);
    } 

    @Transactional
    public void deleteWallpaperUser(JwtAuthenticationToken token){
        UserModel userLogged = userLogged(token);

        userLogged.setWallpaper(null);
        userRepository.save(userLogged);
    }

    @Transactional
    public UserDtoOutput deleteLocationUser(JwtAuthenticationToken token){
        UserModel userLogged = userLogged(token);

        userLogged.setCountry(null);
        userLogged.setState(null);
        userLogged.setCity(null);
        userRepository.save(userLogged);
        UserDtoOutput userDtoOutput = modelMapper.map(userLogged, UserDtoOutput.class);

        return userDtoOutput;
    }

    @Transactional
    public void deleteImageUser(JwtAuthenticationToken token){
        UserModel userLogged = userLogged(token);

        userLogged.setImage(null);
        userRepository.save(userLogged);
    }

    @Transactional
    public void deleteUser(JwtAuthenticationToken token, UserDtoDeleteInput userDto){
        UserModel userLogged = userLogged(token);

        boolean emailMatch = userDto.getEmail().equals(userLogged.getEmail());
        boolean passwordMatch = matches(userDto.getPassword(), userLogged.getPassword()); 

        if(!emailMatch || !passwordMatch){
            throw new InvalidFieldsException("Um ou todos os campos estão inválidos");
        }

        userRepository.deleteById(userLogged.getId());
    }

    private UserModel userLogged(JwtAuthenticationToken token){        
        return userRepository.findById(UUID.fromString(token.getName()))
        .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    }

    private boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
