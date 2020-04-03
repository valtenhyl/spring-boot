package com.valten.datasource.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @className: DynamicDataSource
 * @package: com.valten.datasource.config
 * @describe: 动态数据源
 * @author: huangyuanli
 * @date: 2020/1/14 9:44
 **/
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicContextHolder.peek();
    }

}
