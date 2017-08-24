package service;

import manager.exception.UserNotFoundException;
import web.dto.UserDTO;

public interface LoginService {
    boolean checkPassword(String account, String password) throws UserNotFoundException;
    UserDTO getUserDTO();
}
