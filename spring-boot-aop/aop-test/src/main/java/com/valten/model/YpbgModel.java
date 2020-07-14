package com.valten.support;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("TB_YW_YPBG")
public class YpbgModel implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private String id;
    /**
     * 报告名称
     */
    private String bgmc;
    /**
     * 报告类别 #BGLBDM
     */
    private String bglbdm;
    /**
     * 警情编号
     */
    private String jqbh;
    /**
     * 案件编号
     */
    private String ajbh;
    /**
     * 简要案情
     */
    private String jyaq;
    /**
     * 接续研判工作
     */
    private String nextWork;
    /**
     * 结论
     */
    private String conclusion;
    /**
     * 删除标志 0未删除 1已删除
     */
    private String del;
    /**
     * 创建人编号
     */
    private String createUsername;
    /**
     * 创建人姓名
     */
    private String createTruename;
    /**
     * 创建时间 默认为当前时间
     */
    private Date createDate;
    /**
     * 创建单位
     */
    private String createUnit;
    /**
     * 创建单位代码
     */
    private String createUnitCode;
    /**
     * 修改人编号
     */
    private String modifyUsername;
    /**
     * 修改人姓名
     */
    private String modifyTruename;
    /**
     * 修改时间
     */
    private Date modifyDate;
    /**
     * 修改单位
     */
    private String modifyUnit;
    /**
     * 修改单位代码
     */
    private String modifyUnitCode;

}

