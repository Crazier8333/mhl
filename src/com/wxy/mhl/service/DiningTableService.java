package com.wxy.mhl.service;

import com.wxy.mhl.dao.DiningTableDAO;
import com.wxy.mhl.domain.DiningTable;

import java.util.*;


public class DiningTableService {
    private DiningTableDAO diningTableDAO = new DiningTableDAO();

    //返回所有餐桌的信息
    public List<DiningTable> list() {
        return diningTableDAO.queryMulti("select id, state from diningTable", DiningTable.class);
    }

    // 根据id，查询对应的餐桌对象
    public DiningTable getDiningTableById(int id) {
        return diningTableDAO.querySingle("select * from diningTable where id = ?", DiningTable.class, id);
    }

    //如果餐桌可以预定，对其状态进行更新
    public boolean orderDiningTable(int id, String orderName, String orderTel) {
        int update = diningTableDAO.update("update diningTable set state='已经预定', orderName = ?, orderTel = ? where id = ?", orderName, orderTel, id);
        return update > 0;
    }

    // 更新餐桌状态
    public boolean updateDiningTableState(int id, String state) {
        int update = diningTableDAO.update("update diningTable set state=? where id=?", state, id);
        return update > 0;
    }

    // 将餐桌设置为空闲状态
    public boolean updateDiningTableToFree(int id) {
        int update = diningTableDAO.update("update diningTable set state='空', orderName='', orderTel='' where id=?", id);
        return update > 0;
    }
}
