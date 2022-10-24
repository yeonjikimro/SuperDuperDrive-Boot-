package com.udacity.jwdnd.course1.cloudstorage.mapper;




import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserMapper {

    @Select(("SELECT * FROM USERS"))
    List<User> AllUser();

    @Select("SELECT * FROM USERS WHERE username = #{username}")
    User getUser(String username);

    @Insert("INSERT INTO USERS(username,salt,password,firstname,lastname) VALUES(#{username},#{salt},#{password},#{firstName},#{lastName})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int insert(User user);

    @Update("UPDATE USERS SET username=#{username}, salt=#{salt}, password=#{password}, firstname=#{firstName}, lastname=#{lastName} WHERE userid=#{userId}")
    public void updateUser(User user);

    @Delete("DELETE FROM USERS WHERE userid = #{userId}")
    public void deleteUser(User user);
}
