package com.cp.base.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author zengxm 2015年1月16日
 * 
 * @param <E>
 *            实体对象
 * @param <PK>
 *            主键
 * 
 * @version v.1.0 Jpa Base Api interface
 */
public interface EntityDAO<E extends Serializable, PK extends Serializable> {

	/**
	 * 
	 * @param id
	 *            根据主键查询一个实体
	 * @return 一个实体对象
	 */
	public E get(PK id);

	/**
	 * 
	 * @return 加裁所有对象
	 */
	public List<E> getAll();

	/**
	 * 
	 * @param entity
	 *            保存一个实体
	 * 
	 */
	public void save(E entity);

	/**
	 * 
	 * @param entity
	 *            删除一个实体
	 */
	public void delete(E entity);

	/**
	 * 
	 * @param entity
	 *            修改一个实体
	 */
	public void update(E entity);

	/**
	 * 
	 * @param entity
	 *            当实体在数据库不在在与之对应记录时,则保存实体,在在实体,则更新实体
	 */
	public void saveOrUpdate(E entity);

	/**
	 * 
	 * @param hql
	 *            使用hql语句,检索数据
	 * @param params
	 *            浮动参数
	 * 
	 * @return 查找到某个实体的对象
	 */
	E find(String hql, PK id, Map<String, Object> properties);

}