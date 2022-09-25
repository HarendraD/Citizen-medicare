package view.tm;

public class PharmacistTM {
    private String id;
    private String description;
    private int qtyOnStock;
    private int qtyNeed;

    public PharmacistTM() {
    }

    public PharmacistTM(String id, String description, int qtyOnStock, int qtyNeed) {
        this.setId(id);
        this.setDescription(description);
        this.setQtyOnStock(qtyOnStock);
        this.setQtyNeed(qtyNeed);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQtyOnStock() {
        return qtyOnStock;
    }

    public void setQtyOnStock(int qtyOnStock) {
        this.qtyOnStock = qtyOnStock;
    }

    public int getQtyNeed() {
        return qtyNeed;
    }

    public void setQtyNeed(int qtyNeed) {
        this.qtyNeed = qtyNeed;
    }

    @Override
    public String toString() {
        return "PharmacistTM{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", qtyOnStock=" + qtyOnStock +
                ", qtyNeed=" + qtyNeed +
                '}';
    }
}
