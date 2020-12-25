package View.Base;

import Dao.BorrowDao;
import Dao.EquipmentDao;
import Entity.Borrow;
import Entity.Equipment;
import Entity.User;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class OperationBasicInterface extends JFrame {

    public OperationBasicInterface(String title, int num, String[] button, User user) {
        init(title, num, button, user);
    }

    void init(String title, int num, String[] button, User user) {
        JFrame frame = new JFrame(title);
        frame.setLayout(null);

        JTextArea result = new JTextArea();
        result.setBounds(50, 150, 700, 400);
        result.setEditable(false);
        frame.add(result);

        String sf;
        if (title.equals("管理员管理界面"))
            sf = "管理员";
        else sf = "医护人员";
        JLabel glStr = new JLabel(sf + "功能区：");
        glStr.setBounds(100, 30, 100, 25);
        frame.add(glStr);

        JButton[] function = new JButton[num];
        for (int i = 0; i < num; i++) {
            function[i] = new JButton(button[i]);
            function[i].setBounds(200 + i * (400) / num, 60, 100, 25);
            frame.add(function[i]);
        }

        JLabel helloStr = new JLabel("欢迎您: " + user.getUserName());
        helloStr.setBounds(590, 8, 100, 25);
        frame.add(helloStr);

        JButton buttonReturnMain = new JButton("注销");
        buttonReturnMain.setBounds(680, 8, 100, 25);
        frame.add(buttonReturnMain);

        JButton buttonChangePassword = new JButton("修改密码");
        buttonChangePassword.setBounds(680, 35, 100, 25);
        frame.add(buttonChangePassword);


        JLabel nameStr = new JLabel("设备名称:");
        nameStr.setBounds(40, 110, 100, 25);
        frame.add(nameStr);

        JTextField sName = new JTextField();
        sName.setBounds(100, 110, 150, 25);
        frame.add(sName);

        JButton buttonFindAll = new JButton("查看所有设备");
        buttonFindAll.setBounds(500, 110, 110, 25);
        frame.add(buttonFindAll);

        JButton buttonFindByName = new JButton("通过名称查询");
        buttonFindByName.setBounds(255, 110, 110, 25);
        frame.add(buttonFindByName);

        frame.setBounds(400, 100, 800, 640);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        buttonChangePassword.addActionListener(e -> {
            frame.setVisible(false);
            int[] n = {2, 2};
            String[] lab = {"原密码：", "新密码："};
            String[] butt = {"修改", "返回"};
            new EquipmentBaseInterface("修改密码", n, lab, butt, user);
        });

        buttonReturnMain.addActionListener(e -> {
            frame.setVisible(false);
            int[] n = {2, 2};
            String[] lab = {"用户名：", "密码："};
            String[] butt = {"登录", "注册"};
            new BaseInterface("登录", n, lab, butt);
        });

        buttonFindAll.addActionListener(e -> {
            List<Equipment> list;
            EquipmentDao equipmentDao = new EquipmentDao();
            try {
                list = equipmentDao.findAll();
                result.setText("");
                for (Equipment t : list) {
                    result.append(t.toString());
                }
            } catch (SQLException throwables) {
                JOptionPane.showMessageDialog(null, "查找失败\n" + throwables.getMessage(), "提示", JOptionPane.WARNING_MESSAGE);
            } catch (ClassNotFoundException classNotFoundException) {
                JOptionPane.showMessageDialog(null, "数据库链接失败" + classNotFoundException.getMessage(), "提示", JOptionPane.WARNING_MESSAGE);
            }
        });

        buttonFindByName.addActionListener(e -> {
            String name = sName.getText();
            Equipment equipment;
            EquipmentDao equipmentDao = new EquipmentDao();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(null, "设备名称不能为空\n", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                equipment = equipmentDao.findByName(name);
                result.setText("");
                if(equipment==null){
                    JOptionPane.showMessageDialog(null, "没有此设备\n", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                result.append(equipment.toString());
            } catch (Exception ee) {
                JOptionPane.showMessageDialog(null, "查询失败\n" + ee.getMessage(), "提示", JOptionPane.WARNING_MESSAGE);
            }
        });

        function[0].addActionListener(e -> {
            int[] n;
            String[] lab;
            String[] butt;
            if (0 == user.getType()) {
                n = new int[]{2, 2};
                lab = new String[]{"设备名称：", "数量："};
                butt = new String[]{"租借", "返回"};
            } else {
                n = new int[]{1, 2};
                lab = new String[]{"设备名称："};
                butt = new String[]{"维修", "返回"};
            }
            frame.setVisible(false);
            new EquipmentBaseInterface(butt[0], n, lab, butt, user);
        });

        function[1].addActionListener(e -> {
            int[] n;
            String[] lab;
            String[] butt;
            if (0 == user.getType()) {
                n = new int[]{2, 2};
                lab = new String[]{"设备名称：", "数量："};
                butt = new String[]{"归还", "返回"};
            } else {
                n = new int[]{1, 2};
                lab = new String[]{"设备名称："};
                butt = new String[]{"删除", "返回"};
            }
            frame.setVisible(false);
            new EquipmentBaseInterface(butt[0], n, lab, butt, user);
        });

        function[2].addActionListener(e -> {
            if(1 == user.getType()) {
                int[] n = {5, 2};
                String[] lab = {"设备名称：", "设备数量：", "设备类型：", "设备品牌：", "生产日期："};
                String[] butt = {"添加", "返回"};
                frame.setVisible(false);
                new EquipmentBaseInterface(butt[0], n, lab, butt, user);
            } else {
                List<Borrow> list;
                BorrowDao borrowDao = new BorrowDao();
                try{
                    list = borrowDao.findAll(user.getUserName());
                    result.setText("");
                    if(list.size()<1){
                        JOptionPane.showMessageDialog(null, "你没有租借任何设备\n", "提示", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    for(Borrow t:list){
                        result.append(t.toString());
                    }
                } catch (Exception ee) {
                    JOptionPane.showMessageDialog(null,"查询失败\n"+ee.getMessage(), "提示", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }
}