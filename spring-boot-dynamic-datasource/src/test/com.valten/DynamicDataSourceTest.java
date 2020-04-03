package com.valten;

import com.valten.service.DynamicDataSourceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 多数据源测试
 *
 * @author Mark sunlightcs@gmail.com
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DynamicApplication.class)
public class DynamicDataSourceTest {

    @Autowired
    private DynamicDataSourceService dynamicDataSourceService;

    @Test
    public void test(){
        Long id = 1L;

        dynamicDataSourceService.updateUser(id);
        dynamicDataSourceService.updateUserBySlave1(id);
        dynamicDataSourceService.updateUserBySlave2(id);
    }

}
