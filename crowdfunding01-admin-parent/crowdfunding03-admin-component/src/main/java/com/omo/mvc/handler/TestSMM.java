package com.omo.mvc.handler;

import com.omo.bean.Admin;
import com.omo.service.api.AdminService;
import com.omo.util.CrowdUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author MoYu
 * @create 2021-05-03 17:47
 */
@Controller
public class TestSMM {

//    private Logger logger;

    @Autowired
    private AdminService adminService;

    @RequestMapping("/test/ssm.html")
    public String testSmm(ModelMap modelMap, HttpServletRequest request){

        boolean requestType = CrowdUtil.judgeRequestType(request);
        System.out.println("requestType = " + requestType);

//        logger.info("requestType="+requestType);

//        System.out.println("输出该语句吗？？");

        List<Admin> allAdmin = adminService.getAllAdmin();

        modelMap.addAttribute("allAdmin",allAdmin);

//        System.out.println(10/0);
//        String s = null;
//        System.out.println(" = " + s.length());
        return "success";
    }
}
