package model;

public class SuppliedDetails {
    private String medId;
    private String supId;
    private int qty;

    public SuppliedDetails() {
    }

    public SuppliedDetails(String medId, String supId, int qty) {
        this.setMedId(medId);
        this.setSupId(supId);
        this.setQty(qty);
    }

    public String getMedId() {
        return medId;
    }

    public void setMedId(String medId) {
        this.medId = medId;
    }

    public String getSupId() {
        return supId;
    }

    public void setSupId(String supId) {
        this.supId = supId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "SuppliedDetails{" +
                "medId='" + medId + '\'' +
                ", supId='" + supId + '\'' +
                ", qty=" + qty +
                '}';
    }
}
