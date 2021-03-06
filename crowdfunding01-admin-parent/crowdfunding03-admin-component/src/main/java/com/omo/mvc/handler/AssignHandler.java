package com.omo.mvc.handler;

import com.omo.bean.Auth;
import com.omo.bean.Role;
import com.omo.service.api.AdminService;
import com.omo.service.api.AuthService;
import com.omo.service.api.RoleService;
import com.omo.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author MoYu
 * @create 2021-05-12 16:53
 */
@Controller
public class AssignHandler {

    @Autowired
    private RoleService roleService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private AuthService authService;

    @ResponseBody
    @RequestMapping("/assign/do/role/assign/auth.json")
    public ResultEntity<String> saveRoleAuthRelationship( @RequestBody Map<String, List<Integer>> map) {

        authService.saveRoleAuthRelationship(map);

        return ResultEntity.successWithoutData();
    }


    @ResponseBody @RequestMapping("/assign/get/assigned/auth/id/by/role/id.json")
    public ResultEntity<List<Integer>> getAssignedAuthIdByRoleId(
            @RequestParam("roleId") Integer roleId) {

        List<Integer> authIdList = authService.getAssignedAuthIdByRoleId(roleId);

        return ResultEntity.successWithData(authIdList);
    }

    @ResponseBody
    @RequestMapping("/assign/get/all/auth.json")
    public ResultEntity<List<Auth>> getAllAuth(){

        List<Auth> authList = authService.getAll();

        return ResultEntity.successWithData(authList);
    }

    @RequestMapping("/assign/do/role/assign/assign.html")
    public String saveAdminRoleRelationship(
            @RequestParam("adminId") Integer adminId,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("keyword") String keyword,
            // ????????????????????????????????????????????????????????????????????????????????????????????????roleIdList ????????????
            // ?????? required=false ???????????????????????????????????????
            @RequestParam(value="roleIdList", required=false) List<Integer> roleIdList
    ) {
        adminService.saveAdminRoleRelationship(adminId, roleIdList);

        return "redirect:/admin/get/page.html?pageNum="+pageNum+"&keyword="+keyword;
    }


    @RequestMapping("/assign/to/assign/role/page.html")
    public String toAssignRolePage(
            @RequestParam("adminId") Integer adminId,
            ModelMap modelMap
    ) {

        // 1.??????????????????????????????adminId?????????inner_admin_role???????????????role??????id?????????
        List<Role> assignedRoleList = roleService.getAssignedRole(adminId);

        // 2.?????????????????????
        List<Role> unAssignedRoleList = roleService.getUnAssignedRole(adminId);

        // 3.????????????????????????????????????request.setAttribute("attrName",attrValue);
        modelMap.addAttribute("assignedRoleList", assignedRoleList);
        modelMap.addAttribute("unAssignedRoleList", unAssignedRoleList);

        return "assign-role";
    }
}
