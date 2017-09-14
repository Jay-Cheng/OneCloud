package service;

import java.time.LocalDateTime;

public interface RenameService<T> {
    LocalDateTime rename(T t);
}
