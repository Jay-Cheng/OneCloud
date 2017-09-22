package service.impl;

import org.hibernate.Session;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import dao.UserDAO;
import dao.entity.UserDO;
import dao.factory.UserDAOFactory;
import manager.util.HibernateUtil;
import service.ShredMultipleService;
import service.ShredService;

public class ShredMultipleServiceImpl implements ShredMultipleService {
    
    private ShredServiceFileImpl fileImpl = new ShredServiceFileImpl();
    private ShredServiceFolderImpl folderImpl = new ShredServiceFolderImpl();
    
    private UserDAO userDAO = UserDAOFactory.getInstance("hibernate");
    
    
    
    @Override
    public JSONObject serve(JSONArray dataArr, long userID) {
        for (int i = 0; i < dataArr.size(); i++) {
            String type = dataArr.getJSONObject(i).getString("type");
            long id = dataArr.getJSONObject(i).getLongValue("id");
            
            ShredService shredService = switchImpl(type);
            if (shredService.serve(id)) {
                dataArr.getJSONObject(i).put("status", 1);// success
            } else {
                dataArr.getJSONObject(i).put("status", 2);// fail
            }
        }
        
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        UserDO user = userDAO.read(userID);
        session.getTransaction().commit();
        
        JSONObject result = new JSONObject();
        result.put("resultArr", dataArr);
        result.put("cap", user.getUsedCapacity());
        return result;
    }

    private ShredService switchImpl(String type) {
        if ("folder".equalsIgnoreCase(type)) {
            return folderImpl;
        } else if ("file".equalsIgnoreCase(type)) {
            return fileImpl;
        } else {
            return null;
        }
    }
}
