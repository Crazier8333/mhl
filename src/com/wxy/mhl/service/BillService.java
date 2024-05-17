package com.wxy.mhl.service;

import com.wxy.mhl.dao.BillDAO;
import com.wxy.mhl.dao.MultiTableDAO;
import com.wxy.mhl.domain.Bill;
import com.wxy.mhl.domain.MultiTableBean;

import java.util.List;
import java.util.UUID;

public class BillService {
    private BillDAO billDAO = new BillDAO();
    private MenuService menuService = new MenuService();
    private DiningTableService diningTableService = new DiningTableService();

    private MultiTableDAO multiTableDAO = new MultiTableDAO();
    public boolean orderMenu(int menuId, int nums, int diningTableId) {
        String billID = UUID.randomUUID().toString();
        int update = billDAO.update("insert into bill values(null, ?,?,?,?,?, now(), '未结账')",
                billID, menuId, nums, menuService.getMenuByID(menuId).getPrice() * nums, diningTableId);
        if (update <= 0) {
            return false;
        }

        //更新餐桌状态
        return diningTableService.updateDiningTableState(diningTableId, "就餐中");
    }

    // 返回所有的账单
    public List<Bill> list(){
        return billDAO.queryMulti("select * from bill", Bill.class);

    }

    public List<MultiTableBean> list2(){
        return multiTableDAO.queryMulti("SELECT bill.*, `name`, price FROM bill, menu WHERE bill.menuId = menu.id;", MultiTableBean.class);


    }

    // 查看餐桌是否有未结账的账单
    public boolean hasPayBillByDiningTableId(int diningTableId){
        Bill bill = billDAO.querySingle("SELECT * FROM bill WHERE diningTableId=? AND state='未结账' LIMIT 0, 1", Bill.class, diningTableId);
        return bill != null;
    }

    // 结账
    public boolean payBill(int diningTableId, String payMode){
        // 修改bill
        int update = billDAO.update("update bill set state=? where diningTableId=? and state='未结账'", payMode, diningTableId);
        if(update <= 0){
            return false;
        }

        // 修改diningTable
        if(!(diningTableService.updateDiningTableToFree(diningTableId))){
            return false;
        }
        return true;
    }
}
