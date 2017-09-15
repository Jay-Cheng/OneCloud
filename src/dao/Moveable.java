package dao;

import java.time.LocalDateTime;

public interface Moveable {
    LocalDateTime move(Long id, Long to);
}
