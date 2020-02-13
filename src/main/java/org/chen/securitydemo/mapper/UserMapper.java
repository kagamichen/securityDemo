package org.chen.securitydemo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.chen.securitydemo.pojo.Role;
import org.chen.securitydemo.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    @Select("select * from user where username=#{username}")
    User loadUserByUsername(String username);
    @Select("SELECT * FROM role r,user_role ur WHERE r.id=ur.rid AND uid=#{id}")
    List<Role> getUserRoleByUid(Integer id);
}
