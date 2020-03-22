package com.valten.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class JdbcController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping("/listUser")
    public List<Map<String, Object>> list() {
        String sql = "select * from tb_user;";
        return jdbcTemplate.queryForList(sql);
    }

    @RequestMapping("/addUser")
    public String addUser() {
        String sql = "insert into tb_user values (8, 'test', 'test123', 'test@qq.com', 'test2020', CURRENT_DATE );";
        jdbcTemplate.update(sql);
        return "addUser-ok";
    }

    @RequestMapping("/delUser/{id}")
    public String delUser(@PathVariable("id") Integer id) {
        String sql = "delete from tb_user where id = ?";
        jdbcTemplate.update(sql, id);
        return "delUser-ok";
    }

    @RequestMapping("/updateUser/{id}")
    public String updateUser(@PathVariable("id") Integer id) {
        String sql = "update tb_user set username = ?, password = ? where id = ?;";
        Object[] objects = new Object[3];
        objects[0] = "uTest";
        objects[1] = "uTestPd";
        objects[2] = id;
        jdbcTemplate.update(sql, objects);
        return "updateUser-ok";
    }

    @RequestMapping("/queryUser/{id}")
    public Map<String, Object> queryUser(@PathVariable("id") Integer id) {
        String sql = "select * from tb_user where id = ?;";
        return jdbcTemplate.queryForMap(sql, id);
    }
}
