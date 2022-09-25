package view.tm;

public class MedicineTM {
    private String id;
    private String description;
    private String type;
    private int size;
    private double price;
    private String supId;
    private int qty;


    public MedicineTM() {
    }



    public MedicineTM(String id, String description, String type, int size, double price, String supId, int qty) {
        this.setId(id);
        this.setDescription(description);
        this.setType(type);
        this.setSize(size);
        this.setPrice(price);
        this.setSupId(supId);
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
}
