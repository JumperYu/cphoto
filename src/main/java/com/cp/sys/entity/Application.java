package com.cp.sys.entity;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * 应用菜单
 * 
 * @author zengxm 2014年12月29日
 * 
 */
//@Entity
//@Table(name = "application")
public class Application implements Serializable {

	private static final long serialVersionUID = 1L;

	// 主键
	private String appid;

	private boolean leaf = false; // 是否为叶子节点 
	private boolean expanded = false; // 是否展开

	private String text;
	private String html;

	// 父节点
	private Application parent;
	// 子节点
	private Set<Application> children = new LinkedHashSet<Application>();

	@Id
	@Column(name = "appid")
	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	@Column(name = "leaf")
	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	@Column(name = "expanded")
	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	@Column(name = "text")
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Column(name = "html")
	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "parentId")
	public Application getParent() {
		return parent;
	}

	public void setParent(Application parent) {
		this.parent = parent;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "parent", fetch = FetchType.EAGER)
	public Set<Application> getChildren() {
		return children;
	}

	public void setChildren(Set<Application> children) {
		this.children = children;
	}
}
