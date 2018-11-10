package cn.liueleven.jwt.mapper;


import cn.liueleven.jwt.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    public List<User> selectUsers();

    User findBynameAndPasswd(User user);
    User findByEmail(String email);

}
