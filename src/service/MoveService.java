package service;

import java.time.LocalDateTime;

public interface MoveService {
    LocalDateTime serve(Long id, Long to, String type);
}
