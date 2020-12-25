package Entity;

public class Equipment {
    private final String eName;
    private final String eType;
    private final String eBrand;
    private int eNumber;
    private final String eDate;
    private String eMaintain;

    public Equipment(String eName, String eType, String eBrand, int eNumber, String eDate, String eMaintain) {
        this.eName = eName;
        this.eType = eType;
        this.eBrand = eBrand;
        this.eNumber = eNumber;
        this.eDate = eDate;
        this.eMaintain = eMaintain;
    }

    public String geteName() {
        return eName;
    }

    public String geteType() {
        return eType;
    }

    public String geteBrand() {
        return eBrand;
    }

    public int geteNumber() {
        return eNumber;
    }

    public void seteNumber(int eNumber) {
        this.eNumber = eNumber;
    }

    public String geteDate() {
        return eDate;
    }

    public String geteMaintain() {
        return eMaintain;
    }

    public void seteMaintain(String eMaintain) {
        this.eMaintain = eMaintain;
    }

    @Override
    public String toString() {
        return "名称："+ eName +"\t类型："+eType+"\t品牌："+ eBrand+"\t数量："+eNumber+"\t生产日期："+ eDate+
                "\t维护日期："+eMaintain+"\n";
    }
}
