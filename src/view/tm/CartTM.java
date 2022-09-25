package view.tm;

public class CartTM {
    private String medicineId;
    private String description;
    private int size;
    private double unitPrice;
    private int qty;
    private double totalPrice;

    public CartTM() {
    }

    public CartTM(String medicineId, String description, int size, double unitPrice, int qty, double totalPrice) {
        this.setMedicineId(medicineId);
        this.setDescription(description);
        this.setSize(size);
        this.setUnitPrice(unitPrice);
        this.setQty(qty);
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
