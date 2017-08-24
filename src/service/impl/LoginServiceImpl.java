package service.impl;

import java.util.HashMap;
import java.util.Map;

import dao.UserDAO;
import dao.entity.UserDO;
import dao.factory.UserDAOFactory;
import manager.exception.UserNotFoundException;
import service.LoginService;
import web.dto.UserDTO;

public class LoginServiceImpl implements LoginService {
    
    private UserDO loginUser = null;
    
    /**
     * 检查登录账户与密码是否匹配
     * @return 若匹配返回true，不匹配返回false
     * @throws 如果账户名在数据库中不存在，抛出UserNotFoundException
     */
    @Override
    public boolean checkPassword(String account, String password) throws UserNotFoundException {
        /* 设置账户为查询参数 */
        Map<String, Object> params = new HashMap<>();
        params.put("account", account);
        
        UserDAOFactory factory = new UserDAOFactory();
        UserDAO userDAO = factory.getUserDAO("Hibernate");
        
        loginUser = userDAO.get(params);
        /* 账户不存在，抛出异常 */
        if (loginUser == null) {
            throw new UserNotFoundException();
        }
        if (loginUser.getPassword().equals(password)) {
            return true;
        } else {
            return false;
        }
    }
    
    public UserDTO getUserDTO() {
        UserDTO dto = new UserDTO();
        // TODO
        return dto;
    }
}
