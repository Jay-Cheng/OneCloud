
package dao;

import java.util.Map;

import manager.exception.DBQueryException;

public interface GenericDAO<T> {
    T get(Map<String, Object> params) throws DBQueryException;
    T list(Map<String, Object> params);
    boolean save(T t);
    boolean remove(T t);
    boolean update(T t);
}
