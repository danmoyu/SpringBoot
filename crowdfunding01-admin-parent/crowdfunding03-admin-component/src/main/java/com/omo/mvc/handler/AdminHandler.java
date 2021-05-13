package com.omo.mvc.handler;

import com.github.pagehelper.PageInfo;
import com.omo.bean.Admin;
import com.omo.constant.CrowdConstant;
import com.omo.service.api.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * @author MoYu
 * @create 2021-05-06 19:56
 */
@Controller
public class AdminHandler {

    @Autowired
    AdminService adminService;

    // 更新admin对象
    @RequestMapping("/admin/update.html")
    public String updateAdmin(
            Admin admin,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("keyword") Integer keyword
            ){
        adminService.updateAdmin(admin);

        return "redirect:/admin/get/page.html?pageNum="+pageNum+"&keyword="+keyword;


    }

    // 根据adminId查询用户
    @RequestMapping("/admin/to/edit/page.html")
    public String toEditPage(@RequestParam("adminId") Integer adminId,ModelMap modelMap){

        Admin admin = adminService.getAdminById(adminId);

        modelMap.addAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN,admin);

        return "admin-edit";

    }

    // 新增一条记录
    @RequestMapping("/admin/save.html")
    public String addAdmin(Admin admin){

        adminService.saveAdmin(admin);

        // 新增的记录都放到了最后，所以页码也调到了最后
        return "redirect:/admin/get/page.html?pageNum="+Integer.MAX_VALUE;
    }


    //删除单条记录
    @RequestMapping("/admin/remove/{adminId}/{pageNum}/{keyword}.html")
    public String removeAdmin(
            @PathVariable("adminId") Integer adminId,
            @PathVariable("pageNum") Integer pageNum,
            @PathVariable("keyword") String keyword
    ){

        // 执行删除操作
        adminService.remove(adminId);

        // 页面跳转

        // 1、直接转发到admin-page.jsp会无法显示分页数据；不推荐
        //return "admin-page";

        // 2、转发到/admin/get/page.html地址，一旦刷新页面会重复执行删除浪费性能；且关键字搜索进行删除时，会回到全信息展示页面
        //return "forward:/admin/get/page.html";

        // 3、重定向
        return "redirect:/admin/get/page.html?pageNum="+pageNum+"&keyword="+keyword;
    }


    // 查询admin条数信息并分页显示在页面上
    @RequestMapping("/admin/get/page.html")
    public String getPageInfo(

            // 使用@RequestParam注解的defaultValue属性，指定默认值，在请求中没有携带对象的关键字时使用默认值
            // keyword默认值使用空字符串，和SQL语句配合实现有无关键字两种情况的适配
            @RequestParam(value = "keyword",defaultValue = "") String keyword,

            // pageNum默认值使用1，默认显示第一页的数据
            @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,

            // pageSize默认值使用5，默认每页显示5条数据
            @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
            ModelMap modelMap
    ){

        //调用Service方法获取PageInfo对象
        PageInfo<Admin> pageInfo = adminService.getPageInfo(keyword, pageNum, pageSize);

        // 将数据放在ModelMap模型中
        modelMap.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO,pageInfo);
        return "admin-page";
    }


    // 根据账号密码登录操作
    @RequestMapping("/admin/do/login.html")
    public String doLogin(
            @RequestParam("loginAcct") String loginAcct,
            @RequestParam("userPwd") String userPwd,
            HttpSession session
    ){

        // 调用Service方法执行登录检查
        // 这个方法如果能够返回admin对象说明登录成功，如果账号、密码不正确就会抛出异常
        Admin admin = adminService.getAdminByLoginAcct(loginAcct, userPwd);

        // 将成功登录的admin对象存放在Session域中
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN,admin);

        return "redirect:/admin/to/main/page.html";
    }

    // 退出登录
    @RequestMapping("/admin/do/logout.html")
    public String doLogout(HttpSession session){
        session.invalidate();

        return "redirect:/admin/to/login/page.html";
    }

}
