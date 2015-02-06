package com.cp.base.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

/**
 * 
 * @author zengxm 2015年1月16日
 * 
 * @param <E>
 *            实体对象
 * @param <PK>
 *            主键
 * 
 * @version v.1.0 Jpa Base abstract Api
 */
public abstract class EntityDAOImpl<E extends Serializable, PK extends Serializable>
		implements EntityDAO<E, PK> {

	//@PersistenceContext
	private EntityManager em;

	private Class<?> entityClass;

	/**
	 * 获取实体E类类型
	 */
	public EntityDAOImpl() {
		Class<?> c = (Class<?>) this.getClass();
		Type t = c.getGenericSuperclass();
		if (t instanceof ParameterizedType) {
			this.entityClass = (Class<?>) ((ParameterizedType) t)
					.getActualTypeArguments()[0];
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public E get(PK id) {
		return (E) em.find(entityClass, id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<E> getAll() {
		String findAll = "from " + entityClass.getName();
		return em.createQuery(findAll).getResultList();
	}

	@Override
	public void save(E entity) {
		em.persist(entity);
	}

	@Override
	public void delete(E entity) {
		em.remove(entity);
	}

	@Override
	public void update(E entity) {
		em.refresh(entity);
	}

	// save if not exists else update
	@Override
	public void saveOrUpdate(E entity) {
		em.refresh(entity);
		em.merge(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public E find(String hql, PK id, Map<String, Object> properties) {
		return (E) em.find(entityClass, id, properties);
	}

}
