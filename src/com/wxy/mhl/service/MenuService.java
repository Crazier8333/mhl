package com.wxy.mhl.service;

import com.wxy.mhl.dao.MenuDAO;
import com.wxy.mhl.domain.Menu;

import java.util.List;

public class MenuService {
    private MenuDAO menuDAO = new MenuDAO();

    public List<Menu> list() {
        return menuDAO.queryMulti("select * from menu", Menu.class);
    }

    // 根据id返回Menu对象
    public Menu getMenuByID(int id){
        return menuDAO.querySingle("select * from menu where id = ?", Menu.class, id);
    }
}
