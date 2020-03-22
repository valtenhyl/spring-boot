package com.valten;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
class SpringBootDataApplicationTests {

    @Autowired
    DataSource dataSource;

    @Test
    void contextLoads() throws SQLException {
        // 查看一下默认数据源：class com.zaxxer.hikari.HikariDataSource 据传是当前web最快数据源
       System.out.println(dataSource.getClass());

        Connection connection = dataSource.getConnection();
        System.out.println(connection);

        // xxxTemplate SpringBoot已经配置好模板，拿来即用

        connection.close();
    }

}
