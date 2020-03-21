package com.valten.controller;

import com.valten.dao.DepartmentDao;
import com.valten.dao.EmployeeDao;
import com.valten.pojo.Department;
import com.valten.pojo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private DepartmentDao departmentDao;

    @RequestMapping("/emps")
    public String list(Model model) {
        Collection<Employee> employees = employeeDao.getEmployees();
        model.addAttribute("emps", employees);
        return "emp/list";
    }

    @GetMapping("/emp")
    public String toAddEmp(Model model) {
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("depts", departments);
        return "emp/add";
    }

    @PostMapping("/emp")
    public String addEmp(Employee emp) {
        employeeDao.addEmployee(emp);
        return "redirect:/emps";
    }

    @GetMapping("/emp/{id}")
    public String toEditEmp(@PathVariable("id") Integer empId, Model model) {
        Employee employee = employeeDao.getEmployeeById(empId);
        model.addAttribute("emp", employee);
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("depts", departments);
        return "emp/edit";
    }

    @GetMapping("/delEmp/{id}")
    public String delEmp(@PathVariable("id") Integer empId) {
        employeeDao.delEmployee(empId);
        return "redirect:/emps";
    }
}
