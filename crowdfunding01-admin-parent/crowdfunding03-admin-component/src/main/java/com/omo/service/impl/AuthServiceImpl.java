package com.omo.service.impl;

import com.omo.bean.Auth;
import com.omo.bean.AuthExample;
import com.omo.dao.AuthMapper;
import com.omo.service.api.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author MoYu
 * @create 2021-05-12 20:25
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthMapper authMapper;

    @Override
    public void saveRoleAuthRelationship(Map<String, List<Integer>> map) {

        List<Integer> roleList = map.get("roleId");
        // 1.获取 roleId 的值
        Integer roleId = roleList.get(0);

        // 2.删除旧关联关系数据
        authMapper.deleteOldRelationship(roleId);

        // 3.获取 authIdList
        List<Integer> authIdList = map.get("authIdArray");

        // 4.判断 authIdList 是否有效
        if(authIdList != null && authIdList.size() > 0) {

            authMapper.insertNewRelationship(roleId, authIdList);
        }
    }

    @Override
    public List<Integer> getAssignedAuthIdByRoleId(Integer roleId) {

        return authMapper.selectAssignedAuthIdByRoleId(roleId);
    }

    @Override
    public List<Auth> getAll() {

        return authMapper.selectByExample(new AuthExample());
    }
}
