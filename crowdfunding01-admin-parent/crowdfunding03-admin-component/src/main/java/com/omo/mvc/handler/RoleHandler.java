package com.omo.mvc.handler;

import com.github.pagehelper.PageInfo;
import com.omo.bean.Role;
import com.omo.service.api.RoleService;
import com.omo.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author MoYu
 * @create 2021-05-10 14:03
 */
@Controller
public class RoleHandler {

    @Autowired
    private RoleService roleService;

    //删除role角色
    @ResponseBody
    @RequestMapping("/role/remove/by/id/array.json")
    public ResultEntity<String> removeRole(@RequestBody List<Integer> roleArray){

        roleService.removeRole(roleArray);

        return ResultEntity.successWithoutData();
    }

    // 更新role角色
    @ResponseBody
    @RequestMapping("/role/update.json")
    public ResultEntity<String> updateRole(Role role){

        roleService.updateRole(role);

        return ResultEntity.successWithoutData();
    }


    // 保存角色
    @ResponseBody
    @RequestMapping("/role/save.json")
    public ResultEntity<String> saveRole(Role role){

        roleService.saveRole(role);

        return ResultEntity.successWithoutData();
    }

    @ResponseBody
    @RequestMapping("/role/get/page/info.json")
    public ResultEntity<PageInfo<Role>> getPageInfo(
            @RequestParam(value ="pageNum" ,defaultValue = "1") Integer pageNum,
            @RequestParam(value ="pageSize",defaultValue = "8") Integer pageSize,
            @RequestParam(value ="keyword",defaultValue ="") String keyword
    ){

        // 调用分页方法来获取分页数据
        PageInfo<Role> pageInfo = roleService.getPageInfo(pageNum, pageSize, keyword);


        // 将获取到的数据封装到ResultEntity对象中返回（如果获取分页数据抛出异常，交给异常映射机制处理）
        return ResultEntity.successWithData(pageInfo);

    }
}
