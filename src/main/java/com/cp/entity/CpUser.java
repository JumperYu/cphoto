package com.cp.entity;

/**
 * 
 * @author zengxm 2014年11月6日
 * 
 *         主账号
 *
 */
public class CpUser {

	public CpUser() {
	}

	public CpUser(int cp_userid, String cp_account, String cp_pwd,
			CpIdentity cpIdentity) {
		super();
		this.cp_userid = cp_userid;
		this.cp_account = cp_account;
		this.cp_pwd = cp_pwd;
		this.cpIdentity = cpIdentity;
	}

	private int cp_userid;

	private String cp_account;

	private String cp_pwd;

	private CpIdentity cpIdentity;

	public int getCp_userid() {
		return cp_userid;
	}

	public void setCp_userid(int cp_userid) {
		this.cp_userid = cp_userid;
	}

	public String getCp_account() {
		return cp_account;
	}

	public void setCp_account(String cp_account) {
		this.cp_account = cp_account;
	}

	public String getCp_pwd() {
		return cp_pwd;
	}

	public void setCp_pwd(String cp_pwd) {
		this.cp_pwd = cp_pwd;
	}

	public CpIdentity getCpIdentity() {
		return cpIdentity;
	}

	public void setCpIdentity(CpIdentity cpIdentity) {
		this.cpIdentity = cpIdentity;
	}
}
