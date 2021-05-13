package com.omo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.omo.bean.Role;
import com.omo.bean.RoleExample;
import com.omo.dao.RoleMapper;
import com.omo.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author MoYu
 * @create 2021-05-10 14:05
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> getAssignedRole(Integer adminId) {

        return roleMapper.selectAssignedRole(adminId);
    }

    @Override
    public List<Role> getUnAssignedRole(Integer adminId) {

        return roleMapper.selectUnAssignedRole(adminId);
    }


    @Override
    public void removeRole(List<Integer> roleArray) {

        RoleExample roleExample = new RoleExample();

        // 根据id数组删除角色
        RoleExample.Criteria criteria = roleExample.createCriteria();
        criteria.andIdIn(roleArray);

        roleMapper.deleteByExample(roleExample);
    }

    @Override
    public void updateRole(Role role) {

        roleMapper.updateByPrimaryKey(role);
    }

    @Override
    public void saveRole(Role role) {

        roleMapper.insert(role);

    }

    @Override
    public PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyword) {

        PageHelper.startPage(pageNum,pageSize);

        List<Role> roles = roleMapper.selectRoleByKeyword(keyword);

        return new PageInfo<>(roles);
    }
}
