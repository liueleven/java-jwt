package cn.liueleven.jwt.service;


import cn.liueleven.jwt.mapper.UserMapper;
import cn.liueleven.jwt.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@SuppressWarnings("ALL")
@Service
public class UserService {



    @Autowired
    private UserMapper userMapper;




    public List<User> selectUsers() {
        return userMapper.selectUsers();
    }

    public User findUser(User user) {
        return userMapper.findBynameAndPasswd(user);
    }

    public User findByEmail(String email) {
        return userMapper.findByEmail(email);
    }

}
