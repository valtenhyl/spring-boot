package com.valten.dao;

import com.valten.pojo.Department;
import com.valten.pojo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

// 员工dao
@Repository
public class EmployeeDao {

    @Autowired
    private DepartmentDao departmentDao;

    private static Map<Integer, Employee> employees;

    static {
        employees = new HashMap<>();

        employees.put(1001, new Employee(1001, "Lily", "lily@163.com", 0, new Department(101, "总裁办")));
        employees.put(1002, new Employee(1002, "Bob", "bob@163.com", 1, new Department(102, "行政中心")));
        employees.put(1003, new Employee(1003, "Lufy", "lufy@163.com", 1, new Department(103, "产品中心")));
        employees.put(1004, new Employee(1004, "Lucy", "lucy@163.com", 0, new Department(104, "研发中心")));
        employees.put(1005, new Employee(1005, "Robbin", "robbin@163.com", 0, new Department(105, "销售中心")));
    }

    private static Integer initId = 1006;


    // 获取所有员工信息
    public Collection<Employee> getEmployees() {
        return employees.values();
    }

    // 根据员工id获取员工信息
    public Employee getEmployeeById(Integer empId) {
        return employees.get(empId);
    }

    // 新增员工信息
    public void addEmployee(Employee emp) {
        if (emp.getId() == null) {
            emp.setId(initId++);
        }
        emp.setDepartment(departmentDao.getDepartmentById(emp.getDepartment().getId()));

        employees.put(emp.getId(), emp);
    }

    // 根据员工id删除员工信息
    public void delEmployee(Integer empId) {
        employees.remove(empId);
    }

}
