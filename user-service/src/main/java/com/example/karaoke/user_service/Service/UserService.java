package com.example.karaoke.user_service.Service;

import com.example.karaoke.user_service.DTO.UserInfoDto;
import com.example.karaoke.user_service.Entity.User;
import com.example.karaoke.user_service.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper mapper;

    public UserInfoDto login(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, password);
        if (user == null) return null;
        return mapper.map(user, UserInfoDto.class);
    }
}
