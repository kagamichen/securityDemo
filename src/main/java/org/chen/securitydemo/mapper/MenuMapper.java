package org.chen.securitydemo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.chen.securitydemo.pojo.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
public interface MenuMapper {

//    @Select("SELECT m.*,r.id as rid,r.name as rname,r.namezh AS rnamezhe FROM menu m LEFT JOIN menu_role mr ON m.id=mr.mid left JOIN role r ON mr.rid=r.id")
    List<Menu> getAllmenus();

//@Select("SELECT * FROM role")
//    List<Role> getAllRoles();
}
