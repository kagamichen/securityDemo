package org.chen.securitydemo.service;

import org.chen.securitydemo.mapper.UserMapper;
import org.chen.securitydemo.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      User user= userMapper.loadUserByUsername(username);
      if (user==null){
          throw new UsernameNotFoundException("账号不存在");
      }
      user.setRoles(userMapper.getUserRoleByUid(user.getId()));
      return user;
    }
}
