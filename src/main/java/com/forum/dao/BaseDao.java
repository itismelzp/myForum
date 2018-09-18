package com.forum.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lizhiping03 on 2018/9/17.
 */
public class BaseDao<T> {

    private Class<T> entiryClass;

    @Autowired
    protected HibernateTemplate hibernateTemplate;

    public BaseDao() {
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        entiryClass = (Class) params[0];
    }

    public void save(T entity) {
        hibernateTemplate.save(entity);
    }

    public void remove(T entity) {
        hibernateTemplate.delete(entity);
    }

    public void removeAll(String tableName) {
        getSession().createSQLQuery("truncate TABLE " + tableName).executeUpdate();
    }

    public void update(T entity) {
        hibernateTemplate.update(entity);
    }

    public T get(Serializable id) {
        return hibernateTemplate.get(entiryClass, id);
    }

    public T load(Serializable id) {
        return hibernateTemplate.load(entiryClass, id);
    }

    public List<T> loadAll() {
        return hibernateTemplate.loadAll(entiryClass);
    }

    public List find(String hql) {
        return hibernateTemplate.find(hql);
    }

    public List find(String hql, Object... params) {
        return hibernateTemplate.find(hql, params);
    }

    /**
     * 分页查询函数，使用hql.
     *
     * @param pageNo 页号,从1开始.
     */
    public Page pagedQuery(String hql, int pageNo, int pageSize, Object... values) {
        Assert.hasText(hql);
        Assert.isTrue(pageNo >= 1, "pageNo should start form 1");
        // Count查询
        String countQueryStr = " select count(*) " + removeSelct(removeOrders(hql));
        List countList = hibernateTemplate.find(countQueryStr, values);
        long totalCount = (Long) countList.get(0);

        if (totalCount < 1) {
            return new Page();
        }

        // 实际查询返回分页对象
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        Query query = createQuery(hql, values);
        List list = query.setFirstResult(startIndex).setMaxResults(pageSize).list();

        return new Page(startIndex, totalCount, pageSize, list);
    }

    public Query createQuery(String hql, Object... values) {
        Assert.hasText(hql);
        Query query = getSession().createQuery(hql);
        for (int i = 0; i < values.length; i++) {
            query.setParameter(i, values[i]);
        }
        return query;
    }

    /**
     * 去除hql的select 子句，未考虑union的情况,用于pagedQuery.
     *
     * @see #pagedQuery(String, int, int, Object[])
     */
    private static String removeSelct(String hql) {
        Assert.hasText(hql);
        int beginPos = hql.toLowerCase().indexOf("from");
        Assert.isTrue(beginPos != -1, " hql : " + hql + " must has a keyword 'from'");
        return hql.substring(beginPos);
    }

    /**
     * 去除hql的orderby 子句，用于pagedQuery.
     *
     * @see #pagedQuery(String, int, int, Object[])
     */
    private static String removeOrders(String hql) {
        Assert.hasText(hql);
        Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(hql);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, "");
        }
        m.appendTail(sb);
        return sb.toString();
    }

    public void initialize(Object entity) {
        hibernateTemplate.initialize(entity);
    }

    public Session getSession() {
        return hibernateTemplate.getSessionFactory().getCurrentSession();
    }

}
