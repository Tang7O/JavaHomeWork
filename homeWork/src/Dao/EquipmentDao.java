package Dao;

import Entity.Equipment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EquipmentDao extends DataSource{
    public void add(Equipment equipment) throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;   //  预编译对象
        String sql = "insert into Equipment(EName, EType, EBrand,ENumber,EDate,EMaintain) values(?,?,?,?,?,?)";
        try {
            con = this.getCon();
            ps = con.prepareStatement(sql);
            //    设置占位符对应的参数
            ps.setString(1,equipment.geteName());
            ps.setString(2, equipment.geteType());
            ps.setString(3,equipment.geteBrand());
            ps.setInt(4,equipment.geteNumber());
            ps.setString(5,equipment.geteDate());
            ps.setString(6,equipment.geteMaintain());
            ps.executeUpdate();
        } finally {
            this.close(con, ps, null);
        }
    }
    //修改
    public void update(Equipment equipment) throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;   //  预编译对象
        String sql = "update Equipment set ENumber = ?, EMaintain = ? where EName = ?";
        try {
            con = this.getCon();
            ps = con.prepareStatement(sql);
            //    设置占位符对应的参数
            ps.setInt(1, equipment.geteNumber());
            ps.setString(2, equipment.geteMaintain());
            ps.setString(3, equipment.geteName());
            ps.executeUpdate();
        } finally {
            this.close(con, ps, null);
        }
    }

    //查询
    public List<Equipment> findAll() throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Equipment> list = new ArrayList<>();
        String sql = "select * from Equipment";
        try {
            con = this.getCon();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Equipment s = new Equipment(rs.getString(1), rs.getString(2),
                        rs.getString(3),rs.getInt(4),rs.getString(5),
                        rs.getString(6));
                list.add(s);
            }
        } finally {
            this.close(con, ps, rs);
        }
        return list;
    }
    public Equipment findByName(String name) throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from Equipment where EName = ?";
        Equipment s = null;
        try {
            con = this.getCon();
            ps = con.prepareStatement(sql);
            ps.setString(1, name);
            rs = ps.executeQuery();
            while (rs.next()) {
                s = new Equipment(rs.getString(1), rs.getString(2),
                        rs.getString(3),rs.getInt(4),rs.getString(5),
                        rs.getString(6));
            }
        } finally {
            this.close(con, ps, rs);
        }
        return s;
    }
}
