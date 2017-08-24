
package dao;

import java.util.Map;

public interface GenericDAO<T> {
    T get(Map<String, Object> params);
    T list(Map<String, Object> params);
    boolean save(T t);
    boolean remove(T t);
    boolean update(T t);
}
