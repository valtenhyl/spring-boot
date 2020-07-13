package com.valten.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * 用户Document
 *
 * @author huangyuanli
 * @className UserDocument
 * @package com.valten.model
 * @date 2020/7/13 10:00
 **/
@Document(indexName = "user", type = "base")
@Data
public class User {

    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Text)
    private Character sex;

    @Field(type = FieldType.Integer)
    private Integer age;

    @Field(type = FieldType.Date)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date birth;
    
    @Field(type = FieldType.Text)
    private String address;

}