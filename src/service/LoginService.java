package service;

import manager.exception.DBQueryException;
import manager.exception.UserNotFoundException;
import web.dto.UserDTO;

public interface LoginService {
    boolean checkPassword(String account, String password) throws UserNotFoundException, DBQueryException;
    UserDTO getUserDTO();
}
