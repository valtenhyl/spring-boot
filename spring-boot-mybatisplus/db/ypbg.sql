-- tb_yw_ypbg
create table  tb_yw_ypbg
(
    id                Char(32) not null 	/*主键*/,
    bgmc              VARCHAR2(255) 	/*报告名称*/,
    bglbdm            VARCHAR2(10) 	/*报告类别 #BGLBDM*/,
    jqbh              VARCHAR2(50) 	/*警情编号*/,
    ajbh              VARCHAR2(50) 	/*案件编号*/,
    jyaq              VARCHAR2(4000) 	/*简要案情*/,
    next_work         VARCHAR2(4000) 	/*接续研判工作*/,
    conclusion        VARCHAR2(4000) 	/*结论*/,
    del               Char(1) default 0 null 	/*删除标志 0未删除 1已删除*/,
    create_username   VARCHAR2(18) 	/*创建人编号*/,
    create_truename   VARCHAR2(100) 	/*创建人姓名*/,
    create_date       DATE default sysdate null 	/*创建时间 默认为当前时间*/,
    create_unit       VARCHAR2(100) 	/*创建单位*/,
    create_unit_code  VARCHAR2(12) 	/*创建单位代码*/,
    modify_username   VARCHAR2(18) 	/*修改人编号*/,
    modify_truename   VARCHAR2(100) 	/*修改人姓名*/,
    modify_date       DATE 	/*修改时间*/,
    modify_unit       VARCHAR2(100) 	/*修改单位*/,
    modify_unit_code  VARCHAR2(12) 	/*修改单位代码*/
);
alter  table tb_yw_ypbg
    add constraint PK_tb_yw_ypbg_id primary key (id);
comment on table tb_yw_ypbg is '研判报告';
comment on column tb_yw_ypbg.id is '主键';
comment on column tb_yw_ypbg.bgmc is '报告名称';
comment on column tb_yw_ypbg.bglbdm is '报告类别 #BGLBDM';
comment on column tb_yw_ypbg.jqbh is '警情编号';
comment on column tb_yw_ypbg.ajbh is '案件编号';
comment on column tb_yw_ypbg.jyaq is '简要案情';
comment on column tb_yw_ypbg.next_work is '接续研判工作';
comment on column tb_yw_ypbg.conclusion is '结论';
comment on column tb_yw_ypbg.del is '删除标志 0未删除 1已删除';
comment on column tb_yw_ypbg.create_username is '创建人编号';
comment on column tb_yw_ypbg.create_truename is '创建人姓名';
comment on column tb_yw_ypbg.create_date is '创建时间 默认为当前时间';
comment on column tb_yw_ypbg.create_unit is '创建单位';
comment on column tb_yw_ypbg.create_unit_code is '创建单位代码';
comment on column tb_yw_ypbg.modify_username is '修改人编号';
comment on column tb_yw_ypbg.modify_truename is '修改人姓名';
comment on column tb_yw_ypbg.modify_date is '修改时间';
comment on column tb_yw_ypbg.modify_unit is '修改单位';
comment on column tb_yw_ypbg.modify_unit_code is '修改单位代码';

insert into TB_YW_YPBG (id, bgmc, bglbdm, jqbh, ajbh, jyaq, next_work, conclusion, del, create_username, create_truename, create_date, create_unit, create_unit_code, modify_username, modify_truename, modify_date, modify_unit, modify_unit_code)
values ('A7CA58A50CAB49CCE0536828A8C07268', '关于李在鹏的研判报告--空港队', '001', 'J110108000000202006050001', 'A1101080000002020060003', '简要案情A1101080000002020060003', '2020年4月10日查询结果：3', '要求红色字体显示3', '0', 'test', '张三', to_date('11-06-2020 14:48:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, null, null, null, null);
insert into TB_YW_YPBG (id, bgmc, bglbdm, jqbh, ajbh, jyaq, next_work, conclusion, del, create_username, create_truename, create_date, create_unit, create_unit_code, modify_username, modify_truename, modify_date, modify_unit, modify_unit_code)
values ('A7CA58A50CAB49CCE0536828A8C07267', '关于嫌疑车辆京N8P8F8的研判报告--空港队', '002', 'J110108000000202006050002', 'A1101080000002020060002', '简要案情A1101080000002020060002', '2020年4月10日查询结果：2', '要求红色字体显示2', '0', 'test', '张三', to_date('11-06-2020 14:50:32', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, null, null, null, null);
insert into TB_YW_YPBG (id, bgmc, bglbdm, jqbh, ajbh, jyaq, next_work, conclusion, del, create_username, create_truename, create_date, create_unit, create_unit_code, modify_username, modify_truename, modify_date, modify_unit, modify_unit_code)
values ('A7CA58A50CAB49CCE0536828A8C07266', '关于2020年4月21日东兴1区入室盗窃案的视频报告', '003', 'J110108000000202006050003', 'A1101080000002020060001', '简要案情A1101080000002020060001', '2020年4月10日查询结果：1', '要求红色字体显示1', '0', 'test2', '里斯', to_date('11-06-2020 14:50:32', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, null, null, null, null);
commit;