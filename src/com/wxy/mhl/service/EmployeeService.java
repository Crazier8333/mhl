package com.wxy.mhl.service;

import com.wxy.mhl.dao.EmployeeDAO;
import com.wxy.mhl.domain.Employee;

/*
 * 完成对employee表的各种操作
 */
public class EmployeeService {
    private EmployeeDAO employeeDAO = new EmployeeDAO();

    //根据empId和pwd返回一个Employee对象
    public Employee getEmployeeByIdAndPwd(String empId, String pwd) {
        return employeeDAO.querySingle("select * from employee where empId = ? and pwd = md5(?)", Employee.class, empId, pwd);
    }
}
