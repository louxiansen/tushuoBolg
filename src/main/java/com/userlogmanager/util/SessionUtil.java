package com.userlogmanager.util;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.SessionFactory;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Created by hyrj on 2019/10/14.
 */
@Repository
@Transactional
public class SessionUtil {

    @Autowired
    private SessionFactory sessionFactory;

    public SessionUtil() {
    }

    public SessionUtil(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public <T> T get(Class<T> requiredType, Serializable pk){
        return (T)this.sessionFactory.getCurrentSession().get(requiredType, pk);
    }

    public <T> T load(Class<T> requiredType, Serializable pk){
        return (T)this.sessionFactory.getCurrentSession().load(requiredType, pk);
    }

    public void delete(Object entity){
        this.sessionFactory.getCurrentSession().delete(entity);
    }

    public void delete(Class requireType, Serializable pk){
        this.sessionFactory.getCurrentSession().delete(this.sessionFactory.getCurrentSession().load(requireType, pk));
    }

    public void deleteCollection(Collection entityArray){
        for(Object o : entityArray){
            this.sessionFactory.getCurrentSession().delete(o);
        }
    }

    @Transactional(readOnly = false)
    public Serializable save(Object entity){
        return this.sessionFactory.getCurrentSession().save(entity);
    }

    public void update(Object entity){
        this.sessionFactory.getCurrentSession().update(entity);
    }

    public void saveOrUpdate(Object entity){
        this.sessionFactory.getCurrentSession().saveOrUpdate(entity);
    }

    public <T> T merge(Object entity){
        return (T)this.sessionFactory.getCurrentSession().merge(entity);
    }

    public <T> T merge(Class<T> requiredType, Object entity){
        return (T)this.sessionFactory.getCurrentSession().merge(entity);
    }

    @Transactional
    public <T> Page<T> findPage(Class<T> requiredType, Page<T> page){
        Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(requiredType);
        page.setRowCount((Long) criteria.setProjection(Projections.rowCount()).uniqueResult());
        criteria.setProjection(null);
        criteria.setFirstResult(page.getFirstResult());
        criteria.setMaxResults(page.getMaxResult());
        page.setData(criteria.list());
        return page;
    }

    /**
     * 获得所有指定类型数据.
     * @param requiredType
     * @return
     */
    public <T> List<T> getAll(Class<T> requiredType){
        return this.sessionFactory.getCurrentSession().createCriteria(requiredType).list();
    }

    public <T> List<T> findByDetachedCriteria(DetachedCriteria detachedCriteria){
        return detachedCriteria.getExecutableCriteria(this.sessionFactory.getCurrentSession()).list();
    }

    public <T> List<T> findByDetachedCriteria(Class<T> requiredType, DetachedCriteria detachedCriteria){
        return detachedCriteria.getExecutableCriteria(this.sessionFactory.getCurrentSession()).list();
    }

    public <T> List<T> findByDetachedCriteria(DetachedCriteria detachedCriteria, int firstIndex, int maxResult){
        Criteria criteria = detachedCriteria.getExecutableCriteria(this.sessionFactory.getCurrentSession());
        criteria.setFirstResult(firstIndex);
        criteria.setMaxResults(maxResult);
        return criteria.list();
    }

    public <T> Page<T> findPageByDetachedCriteria(DetachedCriteria detachedCriteria, Page<T> page){
        Criteria criteria = detachedCriteria.getExecutableCriteria(this.sessionFactory.getCurrentSession());
        page.setRowCount((Long)criteria.setProjection(Projections.rowCount()).uniqueResult());
        criteria.setProjection(null);
        criteria.setFirstResult(page.getFirstResult());
        criteria.setMaxResults(page.getMaxResult());
        criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        page.setData(criteria.list());
        return page;
    }

    /**
     * 统计记录数.
     * @param detachedCriteria
     * @return
     */
    public Long countByDetachedCriteria(DetachedCriteria detachedCriteria){
        return (Long)detachedCriteria.getExecutableCriteria(this.sessionFactory.getCurrentSession()).setProjection(Projections.rowCount()).uniqueResult();
    }

    public Session getCurrentSession(){
        return this.sessionFactory.getCurrentSession();
    }

//    public <T> Page<T> findByHql(String hql, Page<T> page){
//        List<T> dataRow = this.sessionFactory.getCurrentSession().createQuery(hql)
//                .setFirstResult(page.getFirstResult())
//                .setMaxResults(page.getMaxResult()).list();
//        page.setData(dataRow);
//        return page;
//    }
//
//    public <T> List<T> findByHql(String hql){
//        return this.sessionFactory.getCurrentSession().createQuery(hql).list();
//    }

    public void refresh(Object object){
        this.getCurrentSession().refresh(object);
    }

}
