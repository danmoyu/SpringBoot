package com.omo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.omo.bean.Admin;
import com.omo.bean.AdminExample;
import com.omo.bean.Role;
import com.omo.constant.CrowdConstant;
import com.omo.dao.AdminMapper;
import com.omo.dao.RoleMapper;
import com.omo.exception.LoginAcctAlreadyInUserException;
import com.omo.exception.LoginFailedException;
import com.omo.service.api.AdminService;
import com.omo.util.CrowdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author MoYu
 * @create 2021-05-02 21:06
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList) {
    // 旧数据如下：
    // adminId    roleId
    // 1        1（要删除）
    // 1        2（要删除）
    // 1        3
    // 1        4
    // 1        5
    // 新数据如下：
    // adminId    roleId
    // 1    3（本来就有）
    // 1    4（本来就有）
    // 1    5（本来就有）
    // 1    6（新）
    // 1    7（新）
    // 为了简化操作：先根据 adminId 删除旧的数据，再根据 roleIdList 保存全部新的数据
        // 1.根据 adminId 删除旧的关联关系数据
        adminMapper.deleteOldRelationship(adminId);

        // 2.根据 roleIdList 和 adminId 保存新的关联关系
        if(roleIdList != null && roleIdList.size() > 0) {

            adminMapper.insertNewRelationship(adminId, roleIdList);
        }
    }

    @Override
    public void updateAdmin(Admin admin) {

        // selective 表示有选择的更新，对于null值的字段不更新
        try {
            adminMapper.updateByPrimaryKeySelective(admin);

        } catch (Exception e) {

            e.printStackTrace();

            // 判断异常类型是不是账号重复异常
            if(e instanceof DuplicateKeyException){
                throw new LoginAcctAlreadyInUserException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }

            throw e;

        }
    }

    @Override
    public Admin getAdminById(Integer adminId) {

         return adminMapper.selectByPrimaryKey(adminId);
    }

    @Override
    public void remove(Integer adminId) {
        adminMapper.deleteByPrimaryKey(adminId);
    }

    @Override
    public PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {

        // 1、调用PageHelper的静态方法开启分页功能
        // 这里充分体现了PageHelper的“非侵入式”设计，原本要做的查询不必有任何修改
        PageHelper.startPage(pageNum,pageSize);

        // 2、指定查询
        List<Admin> list = adminMapper.selectAdminByKeyword(keyword);

        // 3、封装到PageInfo对象中
        return new PageInfo<>(list);
    }

    // 根据登录的账号密码查询是否有该用户
    @Override
    public Admin getAdminByLoginAcct(String loginAcct, String userPwd) {


//        Admin admin = adminMapper.selectAdminByLoginAcct(loginAcct);

        //  1.1创建AdminExample对象
        AdminExample adminExample = new AdminExample();

        //  1.2创建Criteria对象
        AdminExample.Criteria criteria = adminExample.createCriteria();

        //  1.3在Criteria对象中封装查询条件
        criteria.andLoginAcctEqualTo(loginAcct);

        //  1.4调用AdminMapper的方法执行查询
        List<Admin> list = adminMapper.selectByExample(adminExample);

        // 2、判断Admin对象是否为null
        if(list == null || list.size() == 0){
            throw  new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        if(list.size() > 1){
            throw new RuntimeException(CrowdConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);
        }

        Admin admin = list.get(0);

        // 3、判断Admin登录为null，则抛出异常
        if(admin == null){
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        // 4、如果Admin对象不为null则将数据库密码从Admin对象中取出
        String userPwdFromDB = admin.getUserPswd();

        // 5、将表单提交的明文密码进行加密
        String userPswdFromLogin = CrowdUtil.md5(userPwd);

        // 6、对两个加密的密码进行比较
        // 注：不能使用equals（）方法，原因两者都可能为null
        if(!Objects.equals(userPwdFromDB,userPswdFromLogin)){

            // 7、如果比较结果不一致就抛出异常
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        //8、如果一致则返回Admin对象
        return admin;

    }

    @Override
    public List<Admin> getAllAdmin() {
        return adminMapper.selectByExample(new AdminExample());
    }

    // 新增一个admin对象
    @Override
    public void saveAdmin(Admin admin) {

        // 给admin对象添加创建当前时间
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(date);
        
        admin.setCreateTime(format);

        // 密码加密
        String source = admin.getUserPswd();
        String encoded = CrowdUtil.md5(source);

        admin.setUserPswd(encoded);

        try {
            adminMapper.insert(admin);
        } catch (Exception e) {

            e.printStackTrace();

            // 判断异常类型是不是账号重复异常
            if(e instanceof DuplicateKeyException){
                throw new LoginAcctAlreadyInUserException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }

            throw e;
        }
    }
}
