package util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Test {

    public static void main(String[] args) throws SQLException {
        Statement stmt = DBUtil.getConnection().createStatement();
        String sql = "show tables;";
        ResultSet rst = stmt.executeQuery(sql);
        while (rst.next()) {
            String tb = rst.getString(1);
            System.out.println(tb);
        }
    }

}
