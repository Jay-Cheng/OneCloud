package service.impl;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Session;

import com.alibaba.fastjson.JSONObject;

import dao.UserDAO;
import dao.entity.UserDO;
import dao.factory.UserDAOFactory;
import manager.util.HibernateUtil;
import service.LoginService;
import service.dto.DTOConvertor;

public class LoginServiceImpl implements LoginService {
    
    private UserDAO userDAO = UserDAOFactory.getInstance("Hibernate");
    
    @Override
    public JSONObject serve(String account, String password) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
        JSONObject result = new JSONObject();
        
        /* 设置查询参数，根据参数查询数据 */
        Map<String, Object> queryParam = new HashMap<>();
        queryParam.put("account", account);
        UserDO user = userDAO.get(queryParam);
        
        if (user == null) {
            result.put("status", 3);
            result.put("msg", "account does not exist");
        } else if (user.getPassword().equals(password)) {
            result.put("status", 1);
            result.put("msg", "success");
            result.put("data", DTOConvertor.convert(user));
        } else {
            result.put("status", 2);
            result.put("msg", "incorrect password");
        }
        
        session.getTransaction().commit();
        return result;
    }
    
}
