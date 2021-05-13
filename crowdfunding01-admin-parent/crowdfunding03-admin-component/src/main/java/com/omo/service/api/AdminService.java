package com.omo.service.api;

import com.github.pagehelper.PageInfo;
import com.omo.bean.Admin;
import com.omo.bean.Role;

import java.util.List;

/**
 * @author MoYu
 * @create 2021-05-02 21:02
 */

public interface AdminService {

    // 根据关键字查询admin条数并进行分页显示
    PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize );

    void saveAdmin(Admin admin);

    List<Admin> getAllAdmin();

    Admin getAdminByLoginAcct(String loginAcct, String userPwd);

    void remove(Integer adminId);

    Admin getAdminById(Integer adminId);

    void updateAdmin(Admin admin);

    void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList);
}

