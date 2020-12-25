package View.Base;

import Dao.UserDao;
import Entity.User;

import javax.swing.*;
import java.awt.*;

public class BaseInterface extends JFrame {

    public BaseInterface(String title, int[] num, String[] label, String[] button) {
        init(title, num, label, button);
    }

    void init(String title, int[] num, String[] label, String[] button) {
        JFrame frame = new JFrame(title);
        frame.setLayout(null);

        if(title.equals("登录")){
            JLabel fon = new JLabel("欢迎使用Tang7O医疗设备管理系统");
            fon.setFont(new Font("Dialog", Font.BOLD,16));
            fon.setBounds(25,5,290,30);
            frame.add(fon);
        }

        JLabel[] jLabel = new JLabel[num[0]];
        for (int i = 0; i < num[0]; i++) {
            jLabel[i] = new JLabel(label[i]);
            jLabel[i].setBounds(45, 50 + i * 50, 100, 25);
            frame.add(jLabel[i]);
        }
        JTextField sName = new JTextField();
        sName.setBounds(100, 50, 150, 25);
        frame.add(sName);

        JPasswordField[] passwordField = new JPasswordField[num[0] - 1];
        for (int i = 1; i < num[0]; i++) {
            passwordField[i - 1] = new JPasswordField();
            passwordField[i - 1].setBounds(100, 50 + i * 50, 150, 25);
            frame.add(passwordField[i - 1]);
        }

        JButton[] jButton = new JButton[num[1]];
        for (int i = 0; i < 2; ++i) {
            jButton[i] = new JButton(button[i]);
            jButton[i].setBounds(60 + i * 110, 50+num[0]*50, 90, 25);
            frame.add(jButton[i]);
        }
        frame.setBounds(400, 100, 300, 150+num[0]*50);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        jButton[0].addActionListener(e -> {
            String name = sName.getText();
            String password = passwordField[0].getText();
            String password1;
            if (name.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "用户名或密码为空\n", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (title.equals("注册")) {
                password1 = passwordField[1].getText();
                if (!password.equals(password1)) {
                    JOptionPane.showMessageDialog(null, "两次输入的密码不一致\n", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
            User user = new User(name, password,0);
            User temp;
            UserDao userDao = new UserDao();
            if (title.equals("注册")) {
                try{
                    if(userDao.findByName(name)!=null){
                        JOptionPane.showMessageDialog(null, "该用户已存在\n", "提示", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    userDao.add(user);
                    JOptionPane.showMessageDialog(null,"注册成功\n", "提示", JOptionPane.WARNING_MESSAGE);
                    int[] n={2,2};
                    String[] lab = {"用户名：","密码："};
                    String[] butt = {"登录","注册"};
                    frame.setVisible(false);
                    new BaseInterface("登录",n,lab,butt);
                } catch (Exception ee) {
                    JOptionPane.showMessageDialog(null,"注册失败\n"+ee.getMessage(), "提示", JOptionPane.WARNING_MESSAGE);
                }
            } else if(title.equals("登录")){
                try{
                    temp = userDao.findByName(name);
                    if(temp == null){
                        JOptionPane.showMessageDialog(null,"用户名不存在\n", "提示", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    if(!temp.getPassword().equals(user.getPassword())){
                        JOptionPane.showMessageDialog(null,"用户名或密码错误\n", "提示", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    frame.setVisible(false);
                    if(temp.getType()==1){
                        //管理员
                        String[] function = {"维修设备","删除设备","添加设备"};
                        frame.setVisible(false);
                        new OperationBasicInterface("管理员管理界面",3,function,temp);
                    } else {
                        // 医护
                        String[] function = {"租借设备","归还设备","已租借设备"};
                        frame.setVisible(false);
                        new OperationBasicInterface("设备租借界面",3,function,temp);
                    }
                } catch (Exception ee) {
                    JOptionPane.showMessageDialog(null,"登录失败\n"+ee.getMessage(), "提示", JOptionPane.WARNING_MESSAGE);
                }
            }

        });
        jButton[1].addActionListener(e -> {
            if(title.equals("登录")){
                int[] n={3,2};
                String[] lab = {"用户名：","密码：","确认密码"};
                String[] butt = {"注册","返回"};
                frame.setVisible(false);
                new BaseInterface("注册",n,lab,butt);
            } else if(title.equals("注册")){
                int[] n={2,2};
                String[] lab = {"用户名：","密码："};
                String[] butt = {"登录","注册"};
                frame.setVisible(false);
                new BaseInterface("登录",n,lab,butt);
            }
        });
    }
}