package com.valten;

import org.springframework.boot.context.properties.ConfigurationProperties;

// 前缀 valten.hello
@ConfigurationProperties(prefix = "valten.hello")
public class HelloProperties {

    private String prefix;
    private String suffix;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
