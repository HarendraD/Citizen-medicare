package model;

public class OrderDetails {
    private String mId;
    private String description;
    private int qtyNeed;
    private double totalPrice;

    public OrderDetails() {
    }

    public OrderDetails(String mId, String description, int qtyNeed, double totalPrice) {
        this.setmId(mId);
        this.setDescription(description);
        this.setQtyNeed(qtyNeed);
        this.setTotalPrice(totalPrice);
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
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

    @Override
    public String toString() {
        return "OrderDetails{" +
                "mId='" + mId + '\'' +
                ", description='" + description + '\'' +
                ", qtyNeed=" + qtyNeed +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
