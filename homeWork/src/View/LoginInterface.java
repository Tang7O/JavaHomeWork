package View;

import View.Base.BaseInterface;

public class LoginInterface {
    public static void main(String[] args) {
        int[] num={2,2};
        String[] lable = {"用户名：","密码："};
        String[] button = {"登录","注册"};
        new BaseInterface("登录",num,lable,button);
    }
}
