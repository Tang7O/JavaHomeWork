package View.Base;

import Dao.BorrowDao;
import Dao.EquipmentDao;
import Dao.UserDao;
import Entity.Borrow;
import Entity.Equipment;
import Entity.User;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EquipmentBaseInterface extends JFrame {

    public EquipmentBaseInterface(String title, int[] num, String[] label, String[] button, User us) {
        init(title, num, label, button, us);
    }

    void init(String title, int[] num, String[] label, String[] button, User us) {
        JFrame frame = new JFrame(title);
        frame.setLayout(null);

        JLabel[] jLabel = new JLabel[num[0]];
        for (int i = 0; i < num[0]; i++) {
            jLabel[i] = new JLabel(label[i]);
            jLabel[i].setBounds(45, 50 + i * 50, 100, 25);
            frame.add(jLabel[i]);
        }

        JTextField[] jText = new JTextField[num[0]];
        for (int i = 0; i < num[0]; i++) {
            jText[i] = new JTextField();
            jText[i].setBounds(100, 50 + i * 50, 150, 25);
            frame.add(jText[i]);
        }

        JButton[] jButton = new JButton[num[1]];
        for (int i = 0; i < 2; ++i) {
            jButton[i] = new JButton(button[i]);
            jButton[i].setBounds(60 + i * 110, 50 + num[0] * 50, 90, 25);
            frame.add(jButton[i]);
        }
        frame.setBounds(400, 100, 300, 150 + num[0] * 50);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        jButton[0].addActionListener(e -> {
            String[] text = new String[num[0]];

            for (int i = 0; i < num[0]; i++) {
                text[i] = jText[i].getText();
            }

            if (title.equals("修改密码")) {
                UserDao userDao = new UserDao();
                if (text[0].isEmpty() || text[1].isEmpty()) {
                    JOptionPane.showMessageDialog(null, "密码不能为空\n", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (!text[0].equals(us.getPassword())) {
                    JOptionPane.showMessageDialog(null, "原密码不正确\n", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (text[1].equals(text[0])) {
                    JOptionPane.showMessageDialog(null, "新的密码不能和原密码相同\n", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                try {
                    us.setPassword(text[1]);
                    userDao.update(us);
                    JOptionPane.showMessageDialog(null, "修改成功，请重新登录\n", "提示", JOptionPane.WARNING_MESSAGE);
                    int[] n = {2, 2};
                    String[] lab = {"用户名：", "密码："};
                    String[] butt = {"登录", "注册"};
                    frame.setVisible(false);
                    new BaseInterface("登录", n, lab, butt);
                } catch (Exception ee) {
                    JOptionPane.showMessageDialog(null, "修改密码失败\n" + ee.getMessage(), "提示", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                int eNum = 0;
                if (text[0].isEmpty()) {
                    JOptionPane.showMessageDialog(null, "设备名称不能为空\n", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (!title.equals("维修") && !title.equals("删除")) {
                    try {
                        eNum = Integer.parseInt(text[1]);
                    } catch (NumberFormatException numberFormatException) {
                        JOptionPane.showMessageDialog(null, "设备数量必须为正整数\n", "提示", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }
                EquipmentDao equipmentDao = new EquipmentDao();
                Equipment equipment;

                try {
                    equipment = equipmentDao.findByName(text[0]);
                } catch (Exception ee) {
                    JOptionPane.showMessageDialog(null, title + "失败\n" + ee.getMessage(), "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (!title.equals("添加"))
                    if (equipment == null) {
                        JOptionPane.showMessageDialog(null, "没有找到此设备\n", "提示", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                if (0 == us.getType()) {
                    BorrowDao borrowDao = new BorrowDao();
                    Borrow borrow;

                    if (title.equals("租借")) {
                        borrow = new Borrow(us.getUserName(), text[0], eNum);
                        if (equipment.geteNumber() < eNum) {
                            JOptionPane.showMessageDialog(null, "没有如此多的 " + text[0], "提示", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        try {
                            borrowDao.add(borrow);
                            equipment.seteNumber(equipment.geteNumber() - eNum);
                            equipmentDao.update(equipment);
                            JOptionPane.showMessageDialog(null, "成功租借 " + eNum + "个" + text[0], "提示", JOptionPane.WARNING_MESSAGE);
                        } catch (Exception ee) {
                            JOptionPane.showMessageDialog(null, "租借失败\n" + ee.getMessage(), "提示", JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        try {
                            borrow = borrowDao.findByName(us.getUserName(), text[0]);
                        } catch (Exception ee) {
                            JOptionPane.showMessageDialog(null, "归还失败\n" + ee.getMessage(), "提示", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        if (borrow == null) {
                            JOptionPane.showMessageDialog(null, "你没有租借此设备 ", "提示", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        if (borrow.getNum() < eNum) {
                            JOptionPane.showMessageDialog(null, "你只租借了 " + borrow.getNum() + " 个" + text[0], "提示", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        try {
                            borrow.setNum(borrow.getNum() - eNum);
                            borrowDao.update(borrow);
                            equipment.seteNumber(equipment.geteNumber() + eNum);
                            System.out.println(equipment.geteNumber());
                            equipmentDao.update(equipment);
                            JOptionPane.showMessageDialog(null, "归还成功", "提示", JOptionPane.WARNING_MESSAGE);
                        } catch (Exception ee) {
                            JOptionPane.showMessageDialog(null, "归还失败\n" + ee.getMessage(), "提示", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                } else {
                    if (title.equals("维修")) {
                        Date d = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String dateNowStr = sdf.format(d);
                        equipment.seteMaintain(dateNowStr);
                        try {
                            equipmentDao.update(equipment);
                            JOptionPane.showMessageDialog(null, "维修成功,维修日期已更新为 " + dateNowStr, "提示", JOptionPane.WARNING_MESSAGE);
                        } catch (Exception ee) {
                            JOptionPane.showMessageDialog(null, "维修失败\n" + ee.getMessage(), "提示", JOptionPane.WARNING_MESSAGE);
                        }
                    } else if (title.equals("删除")) {
                        try {

                            equipment.seteNumber(0);
                            equipmentDao.update(equipment);
                            JOptionPane.showMessageDialog(null, text[0] + "已被删除", "提示", JOptionPane.WARNING_MESSAGE);
                        } catch (Exception ee) {
                            JOptionPane.showMessageDialog(null, "删除失败\n" + ee.getMessage(), "提示", JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        if(text[4].equals("")) text[4]=null;
                        Equipment now = new Equipment(text[0], text[2], text[3], eNum, text[4], text[4]);
                        if (text[2].isEmpty() || text[3].isEmpty()) {
                            JOptionPane.showMessageDialog(null, "设备类型、品牌不能为空", "提示", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        try {
                            equipmentDao.add(now);
                            JOptionPane.showMessageDialog(null, "添加成功", "提示", JOptionPane.WARNING_MESSAGE);
                            for (int i = 0; i < num[0]; i++)
                                jText[i].setText("");
                        } catch (Exception ee) {
                            JOptionPane.showMessageDialog(null, "添加失败\n" + ee.getMessage(), "提示", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }
            }
        });
        jButton[1].addActionListener(e -> {
            if (0 == us.getType()) {
                String[] function = {"租借设备","归还设备","已租借设备"};
                frame.setVisible(false);
                new OperationBasicInterface("设备租借界面", 3, function, us);
            } else {
                String[] function = {"维修设备", "删除设备", "添加设备"};
                frame.setVisible(false);
                new OperationBasicInterface("管理员管理界面", 3, function, us);
            }
        });
    }
}