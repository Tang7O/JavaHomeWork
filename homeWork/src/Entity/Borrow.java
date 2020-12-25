package Entity;

public class Borrow {
    private final String userName;
    private final String equipmentName;
    private int num;

    public Borrow(String userName, String equipmentName, int num) {
        this.userName = userName;
        this.equipmentName = equipmentName;
        this.num = num;
    }

    public String getUserName() {
        return userName;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "设备名称："+equipmentName+"\t设备数量："+num+"\n";
    }
}
