package com.cp.entity;

/**
 * 
 * @author zengxm 2014年11月6日
 *
 *
 * 个人资料
 */
public class CpIdentity {
	
	private int cp_id;
	
	private String cp_gender;
	private String cp_age;
	private String cp_constellation;
	private String cp_cons_weight;
	private String cp_occupation_level; //
	private String cp_income;
	private String cp_email;
	private String cp_telphone;
	private String cp_userid;	  
	private boolean cp_data_open; // 是否公开
	
	public int getCp_id() {
		return cp_id;
	}
	public void setCp_id(int cp_id) {
		this.cp_id = cp_id;
	}
	public String getCp_gender() {
		return cp_gender;
	}
	public void setCp_gender(String cp_gender) {
		this.cp_gender = cp_gender;
	}
	public String getCp_age() {
		return cp_age;
	}
	public void setCp_age(String cp_age) {
		this.cp_age = cp_age;
	}
	public String getCp_constellation() {
		return cp_constellation;
	}
	public void setCp_constellation(String cp_constellation) {
		this.cp_constellation = cp_constellation;
	}
	public String getCp_cons_weight() {
		return cp_cons_weight;
	}
	public void setCp_cons_weight(String cp_cons_weight) {
		this.cp_cons_weight = cp_cons_weight;
	}
	public String getCp_occupation_level() {
		return cp_occupation_level;
	}
	public void setCp_occupation_level(String cp_occupation_level) {
		this.cp_occupation_level = cp_occupation_level;
	}
	public String getCp_income() {
		return cp_income;
	}
	public void setCp_income(String cp_income) {
		this.cp_income = cp_income;
	}
	public String getCp_email() {
		return cp_email;
	}
	public void setCp_email(String cp_email) {
		this.cp_email = cp_email;
	}
	public String getCp_telphone() {
		return cp_telphone;
	}
	public void setCp_telphone(String cp_telphone) {
		this.cp_telphone = cp_telphone;
	}
	public String getCp_userid() {
		return cp_userid;
	}
	public void setCp_userid(String cp_userid) {
		this.cp_userid = cp_userid;
	}
	public boolean isCp_data_open() {
		return cp_data_open;
	}
	public void setCp_data_open(boolean cp_data_open) {
		this.cp_data_open = cp_data_open;
	}
	
}
