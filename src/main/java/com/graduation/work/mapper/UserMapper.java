package com.graduation.work.mapper;

import com.graduation.work.domain.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    @Insert("insert into user(name, username, password, state, create_time, update_time) value (#{name}, #{username}, #{password}, #{state}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    boolean insertUser(User user);
    @Select("select * from user where username=#{name}")
    User selectUserByName(String name);
    @Select("select * from user where id=#{id}")
    User selectUserById(Long id);
    @Select("select * from user")
    List<User> selectUserList();
    @Update("update user set name=#{name}, username=#{username}, state=#{state}, update_time=#{updateTime} where id=#{id}")
    boolean updateUser(User user);
    @Delete("delete from user where id=#{id}")
    boolean deleteUser(long id);
}
