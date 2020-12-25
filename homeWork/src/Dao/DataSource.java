package Dao;

import javax.swing.*;
import java.sql.*;

public class DataSource {

    //定义一个数据库的链接变量用于存放对象
    public Connection getCon() throws ClassNotFoundException {

        String url = "jdbc:mysql://localhost/equipment";
        String user = "root";
        String password = "123456";

        Connection con = null;
        try {
            con = DriverManager.getConnection(url, user, password);
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (SQLException throwables) {
            JOptionPane.showMessageDialog(null, "数据库链接失败\n"+throwables.getMessage(), "提示", JOptionPane.ERROR_MESSAGE);
        }
        return con;
    }

    public void close(Connection con, Statement st, ResultSet rs) throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (st != null) {
            st.close();
        }
        if (con != null) {
            con.close();
        }
    }
}