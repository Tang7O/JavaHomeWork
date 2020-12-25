package Dao;

import Entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao extends DataSource {
    //添加的方法
    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;   //  预编译对象
        String sql = "insert into User(UserName, Password, Type) values(?,?,?)";
        try {
            con = this.getCon();
            ps = con.prepareStatement(sql);
            //    设置占位符对应的参数
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getPassword());
            ps.setInt(3, user.getType());
            ps.executeUpdate();
        } finally {
            this.close(con, ps, null);
        }
    }


    //修改
    public void update(User user) throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;   //  预编译对象
        String sql = "update User set Password = ? where UserName = ?";
        try {
            con = this.getCon();
            ps = con.prepareStatement(sql);
            //    设置占位符对应的参数
            ps.setString(1, user.getPassword());
            ps.setString(2, user.getUserName());
            ps.executeUpdate();
        } finally {
            this.close(con, ps, null);
        }
    }


    public User findByName(String name) throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from User where UserName = ?";
        User s = null;
        try {
            con = this.getCon();
            ps = con.prepareStatement(sql);
            ps.setString(1, name);
            rs = ps.executeQuery();
            if (rs.next()) {
                s = new User(rs.getString(1), rs.getString(2), rs.getInt(3));
            }
        } finally {
            this.close(con, ps, rs);
        }
        return s;
    }
}
