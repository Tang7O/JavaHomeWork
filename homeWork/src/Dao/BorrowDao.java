package Dao;

import Entity.Borrow;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BorrowDao extends DataSource {
    //添加的方法
    public void add(Borrow borrow) throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;   //  预编译对象
        String sql = "insert into Borrow(userName,eqName, num) values(?,?,?)";
        try {
            con = this.getCon();
            ps = con.prepareStatement(sql);
            //    设置占位符对应的参数
            ps.setString(1, borrow.getUserName());
            ps.setString(2, borrow.getEquipmentName());
            ps.setInt(3, borrow.getNum());
            ps.executeUpdate();
        } finally {
            this.close(con, ps, null);
        }
    }


    //修改
    public void update(Borrow borrow) throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;   //  预编译对象
        String sql = "update Borrow set num = ? where userName = ? and eqName = ?";
        try {
            con = this.getCon();
            ps = con.prepareStatement(sql);
            //    设置占位符对应的参数
            ps.setInt(1, borrow.getNum());
            ps.setString(2, borrow.getUserName());
            ps.setString(3, borrow.getEquipmentName());
            ps.executeUpdate();
        } finally {
            this.close(con, ps, null);
        }
    }


    public List<Borrow> findAll(String name) throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Borrow> list = new ArrayList<>();
        String sql = "select * from Borrow where userName = ?";
        try {
            con = this.getCon();
            ps = con.prepareStatement(sql);
            ps.setString(1,name);
            rs = ps.executeQuery();
            while (rs.next()) {
                Borrow s = new Borrow(rs.getString(1), rs.getString(2),
                        rs.getInt(3));
                list.add(s);
            }
        } finally {
            this.close(con, ps, rs);
        }
        return list;
    }

    public Borrow findByName(String userName,String equipmentName) throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from Borrow where userName = ? and eqName = ?";
        Borrow s = null;
        try {
            con = this.getCon();
            ps = con.prepareStatement(sql);
            ps.setString(1, userName);
            ps.setString(2, equipmentName);
            rs = ps.executeQuery();
            if (rs.next()) {
                s = new Borrow(rs.getString(1), rs.getString(2), rs.getInt(3));
            }
        } finally {
            this.close(con, ps, rs);
        }
        return s;
    }
}
