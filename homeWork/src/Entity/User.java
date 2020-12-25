package Entity;

public class User {
    private final String userName;
    private String password;
    private final int type;


    public User(String userName, String password, int type) {
        this.userName = userName;
        this.password = password;
        this.type = type;
    }


    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getType() {
        return type;
    }

}
