package com.valten.anno;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface AuthPermission {

    /**
     * 默认为0， 表示功能id在第一个参数的位置上，-1则表示未提供，无法进行校验
     */
    int idx() default 0;
}
