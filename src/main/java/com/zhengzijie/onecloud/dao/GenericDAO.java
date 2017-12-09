package com.zhengzijie.onecloud.dao;

import java.util.List;
import java.util.Map;

public interface GenericDAO<T extends Object> {
    /* 基本的增删改查操作 */
    void save(T obj);
    T get(long id);
    void update(T obj);
    void remove(T obj);
    
    /* 根据条件params查询符合的数据 */
    /* 已废弃，使用其它更具体的DAO方法替代 */
    @Deprecated T get(Map<String, Object> params);
    @Deprecated List<T> list(Map<String, Object> params);
}
