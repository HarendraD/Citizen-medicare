package model;

public class Medicine {
    private String id;
    private String description;
    private String type;
    private String supplierId;
    private String supllierName;
    private int size;
    private double price;
    private int qty;

    public Medicine(String description, int size, double price, int qty) {
        this.description = description;
        this.size = size;
        this.price = price;
        this.qty = qty;
    }

    public Medicine(String id, String description, String type, String supplierId, String supllierName, int size, double price, int qty) {
        this.id = id;
        this.description = description;
        this.type = type;
        this.setSupplierId(supplierId);
        this.setSupllierName(supllierName);
        this.size = size;
        this.price = price;
        this.qty = qty;
    }

    public Medicine(String id, String description, String type, int size, double price, int qty) {
        this.setId(id);
        this.setDescription(description);
        this.setType(type);
        this.setSize(size);
        this.setPrice(price);
        this.setQty(qty);
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupllierName() {
        return supllierName;
    }

    public void setSupllierName(String supllierName) {
        this.supllierName = supllierName;
    }

    @Override
    public String toString() {
        return "Medicine{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", supplierId='" + supplierId + '\'' +
                ", supllierName='" + supllierName + '\'' +
                ", size=" + size +
                ", price=" + price +
                ", qty=" + qty +
                '}';
    }
}
