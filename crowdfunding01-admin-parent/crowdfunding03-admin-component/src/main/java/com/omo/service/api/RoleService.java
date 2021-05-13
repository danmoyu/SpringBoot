package com.omo.service.api;

import com.github.pagehelper.PageInfo;
import com.omo.bean.Role;

import java.util.List;

/**
 * @author MoYu
 * @create 2021-05-10 14:05
 */
public interface RoleService {

    // 根据关键查询role角色
    PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyword);

    void saveRole(Role role);

    void updateRole(Role role);

    void removeRole(List<Integer> roleArray);

    List<Role> getAssignedRole(Integer adminId);

    List<Role> getUnAssignedRole(Integer adminId);
}
