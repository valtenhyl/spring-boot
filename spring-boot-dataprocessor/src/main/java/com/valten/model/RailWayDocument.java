package com.valten.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

@Data
@Document(indexName = "rail_way", type = "doc", shards = 10, replicas = 0)
public class RailWayDocument implements Serializable {

    /**
     * 主键(id_no+board_train_code+train_date)
     * "@Id"作用在成员变量，标记一个字段作为id主键
     */
    @Id
    private String id;

    /**
     * 证件类型
     */
    @Field(type = FieldType.Text)
    private String idKind;

    /**
     * 证件号
     */
    @Field(type = FieldType.Text)
    private String idNo;

    /**
     * 姓名
     */
    @Field(type = FieldType.Text)
    private String name;

    /**
     * 乘车日期
     */
    @Field(type = FieldType.Text)
    private String trainDate;

    /**
     * 车次
     */
    @Field(type = FieldType.Text)
    private String boardTrainCode;

    /**
     * 发站
     */
    @Field(type = FieldType.Text)
    private String fromStationName;

    /**
     * 到站
     */
    @Field(type = FieldType.Text)
    private String toStationName;

    /**
     * 车厢
     */
    @Field(type = FieldType.Text)
    private String coachNo;

    /**
     * 座位号
     */
    @Field(type = FieldType.Text)
    private String seatNo;

    /**
     * 是否取消
     */
    @Field(type = FieldType.Text)
    private String isCancel;

    /**
     * 是否改签
     */
    @Field(type = FieldType.Text)
    private String isResign;

    /**
     * 是否退票
     */
    @Field(type = FieldType.Text)
    private String isReturn;

    /**
     * 是否取票
     */
    @Field(type = FieldType.Text)
    private String isTicket;
}
