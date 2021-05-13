package com.omo.service.impl;

import com.omo.bean.Menu;
import com.omo.bean.MenuExample;
import com.omo.dao.MenuMapper;
import com.omo.service.api.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author MoYu
 * @create 2021-05-11 17:23
 */
@Service
public class MenuServiceImpl  implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> getAll() {

        return menuMapper.selectByExample(new MenuExample());
    }
}
