package com.valten.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xis
 * @date 2020-06-10 16:42
 **/
public class BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 删除标识
	 */
	private String del;

	/**
	 * 创建用户ukey
	 */
	@JSONField(serialize = false)
	private String createUkey;

	/**
	 * 创建用户账号
	 */
	@JSONField(serialize = false)
	private String createPid;

	/**
	 * 创建用户姓名
	 */
	private String createUser;

	/**
	 * 创建用户所在部门名称
	 */
	private String createUnit;

	/**
	 * 创建用户所在部门编码
	 */
	private String createUnitCode;

	/**
	 * 创建日期
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createDate;
	/**
	 * 修改用户ukey
	 */
	@JSONField(serialize = false)
	private String modifyUkey;

	/**
	 * 修改用户帐号
	 */
	@JSONField(serialize = false)
	private String modifyPid;

	/**
	 * 修改用户姓名
	 */
	@JSONField(serialize = false)
	private String modifyUser;

	/**
	 * 修改日期
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss", serialize = false)
	private Date modifyDate;

	/**
	 * 修改用户所在部门
	 */
	@JSONField(serialize = false)
	private String modifyUnit;

	/**
	 * 修改用户所在部门编码
	 */
	@JSONField(serialize = false)
	private String modifyUnitCode;

	/**
	 * 页码
	 */
	@JSONField(serialize = false)
	private Integer pageNum;

	/**
	 * 每页记录数
	 */
	@JSONField(serialize = false)
	private Integer pageSize;

	public String getDel() {
		return del;
	}

	public void setDel(String del) {
		this.del = del;
	}

	public String getCreateUkey() {
		return createUkey;
	}

	public void setCreateUkey(String createUkey) {
		this.createUkey = createUkey;
	}

	public String getCreatePid() {
		return createPid;
	}

	public void setCreatePid(String createPid) {
		this.createPid = createPid;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCreateUnit() {
		return createUnit;
	}

	public void setCreateUnit(String createUnit) {
		this.createUnit = createUnit;
	}

	public String getCreateUnitCode() {
		return createUnitCode;
	}

	public void setCreateUnitCode(String createUnitCode) {
		this.createUnitCode = createUnitCode;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getModifyUkey() {
		return modifyUkey;
	}

	public void setModifyUkey(String modifyUkey) {
		this.modifyUkey = modifyUkey;
	}

	public String getModifyPid() {
		return modifyPid;
	}

	public void setModifyPid(String modifyPid) {
		this.modifyPid = modifyPid;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getModifyUnit() {
		return modifyUnit;
	}

	public void setModifyUnit(String modifyUnit) {
		this.modifyUnit = modifyUnit;
	}

	public String getModifyUnitCode() {
		return modifyUnitCode;
	}

	public void setModifyUnitCode(String modifyUnitCode) {
		this.modifyUnitCode = modifyUnitCode;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}
