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
import album.car.test.albumcar12.dto.userDto.UserDtoInsertDescriptionInput;
import album.car.test.albumcar12.dto.userDto.UserDtoInsertLocationUserInput;
import album.car.test.albumcar12.dto.userDto.UserDtoOutput;
import album.car.test.albumcar12.dto.userDto.UserDtoUpdateLocationUserInput;
import album.car.test.albumcar12.exception.DescriptionCreatedException;
import album.car.test.albumcar12.exception.InvalidFieldsException;
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

    @Transactional
    public UserDtoOutput createUser(UserDtoCreateInput userDto){
        UserModel userModel = modelMapper.map(userDto, UserModel.class);
        String password = new BCryptPasswordEncoder().encode(userModel.getPassword());

        userModel.setPassword(password);
        userModel.setAlbum(new ArrayList<>());
        userModel.setDiary(new ArrayList<>());
        userModel.setHot(new ArrayList<>());

        UserModel createUser = userRepository.save(userModel);
        UserDtoOutput returnUser = modelMapper.map(createUser, UserDtoOutput.class);

        return returnUser;
    }

    public List<UserDtoOutput> listUsers(){
        List<UserModel> listUsers = userRepository.findAll();
        return UserDtoOutput.convert(listUsers);
    }  

    @Transactional
    public UserDtoOutput insertDescriptionUser(UUID idUser, UserDtoInsertDescriptionInput userDto){
        if(!userRepository.existsById(idUser)){
            throw new EntityNotFoundException("Usuário não existente");
        }
        if(userDto.getDescription().equals(null)){
            throw new EntityNotFoundException("Não foi possível criar a descrição, pois o campo está nulo");
        }

        UserModel user = userRepository.findUserById(idUser);


        user.setDescription(userDto.getDescription());
        userRepository.save(user);
        UserDtoOutput userDtoOutput = modelMapper.map(user, UserDtoOutput.class);

        return userDtoOutput;
    }

    @Transactional
    public UserDtoOutput updateDescriptionUser(UUID idUser, UserDtoInsertDescriptionInput userDto){
        if(!userRepository.existsById(idUser)){
            throw new EntityNotFoundException("Usuário não existente");
        }
        if(userDto.getDescription().equals(null)){
            throw new EntityNotFoundException("Não foi possível atualizar a descrição, pois o campo está nulo");
        }

        UserModel user = userRepository.findUserById(idUser);

        if(user.getDescription().equals(null)){
            throw new DescriptionCreatedException("A descrição não foi criada!");
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
        user.setCountry(userDto.getCountry());
        user.setState(userDto.getState());
        user.setCity(userDto.getCity());
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

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private boolean matches(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}
