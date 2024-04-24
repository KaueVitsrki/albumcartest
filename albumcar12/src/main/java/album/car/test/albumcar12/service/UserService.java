package album.car.test.albumcar12.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    public UserDtoOutput insertDescriptionUser(UUID idUser, UserDtoInsertDescriptionInput userDto){
        if(!userRepository.existsById(idUser)){
            throw new EntityNotFoundException("Usuário não existente");
        }

        UserModel user = userRepository.findUserById(idUser);

        if(user.getDescription() != null){
            throw new FieldAlreadyCreatedException("Não foi possível criar o campo, pois ele já existe!");
        }

        user.setDescription(userDto.getDescription());
        userRepository.save(user);
        UserDtoOutput userDtoOutput = modelMapper.map(user, UserDtoOutput.class);

        return userDtoOutput;
    }

    @Transactional 
    public UserDtoOutput insertLocationUser(UUID idUser, UserDtoInsertLocationUserInput userDto){
        if(!userRepository.existsById(idUser)){
            throw new EntityNotFoundException("Usuário não existente");
        }

        UserModel user = userRepository.findUserById(idUser);

        if(user.getCountry() != null){
            throw new FieldAlreadyCreatedException("Não foi possível criar o campo, pois ele já existe!");
        }

        user.setCountry(userDto.getCountry());
        user.setState(userDto.getState());
        user.setCity(userDto.getCity());
        userRepository.save(user);
        UserDtoOutput userDtoOutput = modelMapper.map(user, UserDtoOutput.class);

        return userDtoOutput;
    }

    @Transactional 
    public UserDtoOutput insertImageUser(UUID idUser, UserDtoInserImageUserInput userDto){
        if(!userRepository.existsById(idUser)){
            throw new EntityNotFoundException("Usuário não existente");
        }

        UserModel user = userRepository.findUserById(idUser);

        if(user.getImage() != null){
            throw new FieldAlreadyCreatedException("Não foi possível criar o campo, pois ele já existe!");
        }

        user.setImage(userDto.getImage());
        userRepository.save(user);
        UserDtoOutput userDtoOutput = modelMapper.map(user, UserDtoOutput.class);
        
        return userDtoOutput;
    }

    @Transactional
    public UserDtoOutput insertWallpaperUser(UUID idUser, UserDtoInsertWallpaperUserInput userDto){
        if(!userRepository.existsById(idUser)){
            throw new EntityNotFoundException("Usuário não existente");
        }

        UserModel user = userRepository.findUserById(idUser);

        if(user.getWallpaper() != null){
            throw new FieldAlreadyCreatedException("Não foi possível criar o campo, pois ele já existe!");
        }
        
        user.setWallpaper(userDto.getWallpaper());
        userRepository.save(user);
        UserDtoOutput userDtoOutput = modelMapper.map(user, UserDtoOutput.class);

        return userDtoOutput;

    }

    @Transactional
    public UserDtoOutput updateNameUser(UUID idUser, UserDtoUpdateNameUserInput userDto){
        if(!userRepository.existsById(idUser)){
            throw new EntityNotFoundException("Usuário não existente");
        }

        UserModel user = userRepository.findUserById(idUser);
        user.setName(userDto.getName());
        userRepository.save(user);
        UserDtoOutput userDtoOutput = modelMapper.map(user, UserDtoOutput.class);

        return userDtoOutput;
    }

    @Transactional 
    public UserDtoOutput updateEmailUser(UUID idUser, UserDtoUpdateEmailUserInput userDto){
        if(!userRepository.existsById(idUser)){
            throw new EntityNotFoundException("Usuário não existente");
        }
        if(userRepository.existsByEmail(userDto.getNewEmail())){
            throw new EmailExistException("Não foi possível atualizar o email! O email informado já foi cadastrado");
        }

        UserModel user = userRepository.findUserById(idUser);
        boolean passwordMatch = matches(userDto.getPassword(), user.getPassword()); 
        boolean passwordConfirmationMatch = matches(userDto.getPasswordConfirmation(), user.getPassword());

        if(!passwordMatch || !passwordConfirmationMatch || passwordMatch != passwordConfirmationMatch){
            throw new InvalidFieldsException("Um ou todos os campos estão inválidos");
        }

        user.setEmail(userDto.getNewEmail());
        userRepository.save(user);
        UserDtoOutput userDtoOutput = modelMapper.map(user, UserDtoOutput.class);
        
        return userDtoOutput;
    }

    @Transactional
    public UserDtoOutput updatePasswordUser(UUID idUser, UserDtoUpdatePasswordUserInput userDto){
        if(!userRepository.existsById(idUser)){
            throw new EntityNotFoundException("Usuário não existente");
        }
        
        UserModel user = userRepository.findUserById(idUser);
        boolean currentPasswordMatch = matches(userDto.getCurrentPassword(), user.getPassword()); 
        
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
        user.setPassword(newPassword);
        userRepository.save(user);
        UserDtoOutput userDtoOutput = modelMapper.map(user, UserDtoOutput.class);

        return userDtoOutput;
    }

    @Transactional
    public UserDtoOutput updateDescriptionUser(UUID idUser, UserDtoInsertDescriptionInput userDto){
        if(!userRepository.existsById(idUser)){
            throw new EntityNotFoundException("Usuário não existente");
        }

        UserModel user = userRepository.findUserById(idUser);

        if(user.getDescription() == null){ // mudei de .equals(null)
            throw new DescriptionNotCreatedException("A descrição não foi criada!");
        }

        user.setDescription(userDto.getDescription());
        userRepository.save(user);
        UserDtoOutput userDtoOutput = modelMapper.map(user, UserDtoOutput.class);

        return userDtoOutput;
    }

    @Transactional
    public UserDtoOutput updateLocationUser(UUID idUser, UserDtoUpdateLocationUserInput userDto){
        if(!userRepository.existsById(idUser)){
            throw new EntityNotFoundException("Usuário não existente");
        }

        UserModel user = userRepository.findUserById(idUser);

        if(user.getCountry() == null){
            throw new EntityNotFoundException("Adicione primeiro o país");
        }
        if(userDto.getCountry() == null){
            userDto.setCountry(user.getCountry());
        }
        if(userDto.getState() == null){
            userDto.setState(user.getState());
        }
        if(userDto.getCity() == null){
            userDto.setCity(user.getCity());
        }

        user.setCountry(userDto.getCountry());
        user.setState(userDto.getState());
        user.setCity(userDto.getCity());
        userRepository.save(user);
        UserDtoOutput userDtoOutput = modelMapper.map(user, UserDtoOutput.class);

        return userDtoOutput;
    }

    @Transactional 
    public UserDtoOutput updateImageUser(UUID idUser, UserDtoInserImageUserInput userDto){
        if(!userRepository.existsById(idUser)){
            throw new EntityNotFoundException("Usuário não existente");
        }

        UserModel user = userRepository.findUserById(idUser);

        if(user.getImage() == null){ // mudei de .equals(null)
            throw new ImageNotCreatedException("A imagem não foi adicionada!");
        }

        user.setImage(userDto.getImage());
        userRepository.save(user);
        UserDtoOutput userDtoOutput = modelMapper.map(user, UserDtoOutput.class);
        
        return userDtoOutput;
    }

    @Transactional
    public UserDtoOutput updateWallpaperUser(UUID idUser, UserDtoInsertWallpaperUserInput userDto){
        if(!userRepository.existsById(idUser)){
            throw new EntityNotFoundException("Usuário não existente");
        }

        UserModel user = userRepository.findUserById(idUser);

        if(user.getWallpaper() == null){ // mudei de .equals(null)
            throw new ImageNotCreatedException("A imagem não foi adicionada!");
        }
       
        user.setWallpaper(userDto.getWallpaper());
        userRepository.save(user);
        UserDtoOutput userDtoOutput = modelMapper.map(user, UserDtoOutput.class);

        return userDtoOutput;
    }
 
    public List<UserDtoOutput> listUsers(){
        List<UserModel> listUsers = userRepository.findAll();
        return UserDtoOutput.convert(listUsers);
    } 

    @Transactional
    public void deleteWallpaperUser(UUID idUser){
        if(!userRepository.existsById(idUser)){
            throw new EntityNotFoundException("Usuário não existente");
        }

        UserModel user = userRepository.findUserById(idUser);
        user.setWallpaper(null);
        userRepository.save(user);
    }

    @Transactional
    public UserDtoOutput deleteLocationUser(UUID idUser){
        if(!userRepository.existsById(idUser)){
            throw new EntityNotFoundException("Usuário não existente");
        }

        UserModel user = userRepository.findUserById(idUser);
        user.setCountry(null);
        user.setState(null);
        user.setCity(null);
        userRepository.save(user);
        UserDtoOutput userDtoOutput = modelMapper.map(user, UserDtoOutput.class);

        return userDtoOutput;
    }

    @Transactional
    public void deleteImageUser(UUID idUser){
        if(!userRepository.existsById(idUser)){
            throw new EntityNotFoundException("Usuário não existente");
        }

        UserModel user = userRepository.findUserById(idUser);
        user.setImage(null);
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(UUID idUser, UserDtoDeleteInput userDto){
        if(!userRepository.existsById(idUser)){
            throw new EntityNotFoundException("Usuário não existente");
        }

        UserModel user = userRepository.findUserById(idUser);
        boolean emailMatch = userDto.getEmail().equals(user.getEmail());
        boolean passwordMatch = matches(userDto.getPassword(), user.getPassword()); 

        if(!emailMatch || !passwordMatch){
            throw new InvalidFieldsException("Um ou todos os campos estão inválidos");
        }

        userRepository.deleteById(idUser);
    }

    private boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
