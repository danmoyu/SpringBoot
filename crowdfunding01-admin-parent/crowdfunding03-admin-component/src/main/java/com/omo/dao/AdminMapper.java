package com.omo.dao;

import com.omo.bean.Admin;
import com.omo.bean.AdminExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdminMapper {

    //根据账号查询admin对象
    Admin selectAdminByLoginAcct(String loginAcct);

    // 根据关键字来查询admin对象
    List<Admin> selectAdminByKeyword(String keyword);

    // 删除admin对象中所有的角色分配
    void deleteOldRelationship(Integer adminId);

    // 添加新的admin角色分配
    void insertNewRelationship(@Param("adminId") Integer adminId, @Param("roleIdList") List<Integer> roleIdList);

    int countByExample(AdminExample example);

    int deleteByExample(AdminExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Admin record);

    int insertSelective(Admin record);

    List<Admin> selectByExample(AdminExample example);

    Admin selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Admin record, @Param("example") AdminExample example);

    int updateByExample(@Param("record") Admin record, @Param("example") AdminExample example);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);

}