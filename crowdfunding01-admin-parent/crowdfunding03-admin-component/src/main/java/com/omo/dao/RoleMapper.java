package com.omo.dao;

import com.omo.bean.Role;
import com.omo.bean.RoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoleMapper {

    // 根据关键字来查询role角色并显示分页操作
    List<Role> selectRoleByKeyword(String keyword);

    // 查询admin对象分配的角色种类
    List<Role> selectAssignedRole(Integer adminId);

    // 未分配的角色种类
    List<Role> selectUnAssignedRole(Integer adminId);

    int countByExample(RoleExample example);

    int deleteByExample(RoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExample(RoleExample example);

    Role selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

}