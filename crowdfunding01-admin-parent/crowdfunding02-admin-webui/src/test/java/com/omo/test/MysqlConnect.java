package com.omo.test;

import com.omo.bean.Admin;
import com.omo.bean.Role;
import com.omo.dao.AdminMapper;
import com.omo.dao.RoleMapper;
import com.omo.service.api.AdminService;
import com.omo.util.CrowdUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author MoYu
 * @create 2021-05-02 10:58
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml","classpath:spring-persist-tx.xml"})
public class MysqlConnect {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired()
    private AdminService adminService;

    @Autowired
    private RoleMapper roleMapper;

    @Test
    public void testRole(){

        for (int i = 0;i < 365;i++){
            Role role = new Role(null, "程序员"+i);
            roleMapper.insert(role);
        }
    }


    @Test
    public void testTx(){

//        for(int i =0;i<256;i++) {
//            Admin admin = new Admin(null, "mary__"+i, "456", "玛丽-"+i, "mary@qq.com", null);
//            adminService.saveAdmin(admin);
//
//        }
    }

    @Test
    public void test01(){

        Admin admin = new Admin(null, "jack", "123456", "杰克", "jack@qq.com", null);
        int insert = adminMapper.insert(admin);
        Admin admin1 = adminMapper.selectByPrimaryKey(1);
        System.out.println("admin1 = " + admin1);
        System.out.println("insert = " + insert);
    }

    @Test
    public void testConnection() throws SQLException {


        Connection connection = dataSource.getConnection();
        System.out.println("connection = " + connection);
    }
}
