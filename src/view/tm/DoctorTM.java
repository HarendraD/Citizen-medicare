package view.tm;

public class DoctorTM {
    private String id;
    private String name;
    private String address;
    private String contactNumber;
    private String type;

    public DoctorTM(String id, String name, String contactNumber, String type) {
        this.id = id;
        this.name = name;
        this.contactNumber = contactNumber;
        this.type = type;
    }

    public DoctorTM(String id, String name, String address, String contactNumber, String type) {
        this.setId(id);
        this.setName(name);
        this.setAddress(address);
        this.setContactNumber(contactNumber);
        this.setType(type);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
