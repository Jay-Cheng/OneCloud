package dao;

import po.User;

public class UserDAO {
    public boolean check(String username, String password) {
        if ("admin".equals(username) && "admin".equals(password)) {
            return true;
        }
        return false;
    }
    public boolean check(User user) {
        return check(user.getAccount(), user.getPassword());
    }
}
