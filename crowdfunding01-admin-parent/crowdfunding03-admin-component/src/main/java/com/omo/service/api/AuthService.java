package com.omo.service.api;

import com.omo.bean.Auth;

import java.util.List;
import java.util.Map;

/**
 * @author MoYu
 * @create 2021-05-12 20:24
 */
public interface AuthService {
    List<Auth> getAll();

    List<Integer> getAssignedAuthIdByRoleId(Integer roleId);

    void saveRoleAuthRelationship(Map<String, List<Integer>> map);
}
