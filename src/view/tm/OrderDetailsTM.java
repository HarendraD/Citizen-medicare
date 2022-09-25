package view.tm;

public class OrderDetailsTM {
    private String medicineId;
    private String description;
    private int qtyNeed;
    private double totalPrice;

    public OrderDetailsTM() {
    }

    public OrderDetailsTM(String medicineId, String description, int qtyNeed, double totalPrice) {
        this.setMedicineId(medicineId);
        this.setDescription(description);
        this.setQtyNeed(qtyNeed);
        this.setTotalPrice(totalPrice);
    }

    public String getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(String medicineId) {
        this.medicineId = medicineId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQtyNeed() {
        return qtyNeed;
    }

    public void setQtyNeed(int qtyNeed) {
        this.qtyNeed = qtyNeed;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
