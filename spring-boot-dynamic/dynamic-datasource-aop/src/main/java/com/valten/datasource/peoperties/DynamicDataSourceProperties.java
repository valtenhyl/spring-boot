package com.valten.datasource.peoperties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @className: DynamicDataSourceProperties
 * @package: com.valten.datasource.peoperties
 * @describe: 动态数据源属性
 * @author: huangyuanli
 * @date: 2020/1/14 16:50
 **/
@Component
@ConfigurationProperties(prefix = "dynamic")
public class DynamicDataSourceProperties {

    private Map<String, DataSourceProperties> datasource = new LinkedHashMap<>();

    public Map<String, DataSourceProperties> getDatasource() {
        return datasource;
    }

    public void setDatasource(Map<String, DataSourceProperties> datasource) {
        this.datasource = datasource;
    }
}
