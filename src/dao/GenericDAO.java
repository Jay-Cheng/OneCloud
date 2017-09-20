package dao;

import java.util.List;
import java.util.Map;

public interface GenericDAO<T extends Object> {
    /* 基本的CRUD操作 */
    long create(T obj);
    T read(long id);
    void update(T obj);
    void delete(T obj);
    
    /* 根据条件params查询符合的数据 */
    T get(Map<String, Object> params);
    List<T> list(Map<String, Object> params);
}
