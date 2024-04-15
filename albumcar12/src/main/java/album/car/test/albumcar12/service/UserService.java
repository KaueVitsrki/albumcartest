package album.car.test.albumcar12.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import album.car.test.albumcar12.dto.UserDto;
import album.car.test.albumcar12.model.UserModel;
import album.car.test.albumcar12.repository.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public UserDto createUser(UserDto userDto){
        UserModel userModel = modelMapper.map(userDto, UserModel.class);
        userModel.setAlbum(new ArrayList<>());
        userModel.setDiary(new ArrayList<>());
        userModel.setHot(new ArrayList<>());
        
        UserModel createUser = userRepository.save(userModel);
        UserDto returnUser = modelMapper.map(createUser, UserDto.class);

        return returnUser;
    }

    public List<UserDto> listUsers(){
        List<UserModel> listUsers = userRepository.findAll();
        return UserDto.convert(listUsers);
    }  

    @Transactional
    public void deleteUserByid(UUID id){
        userRepository.deleteById(id);
    }

}
