package view.tm;

public class PatientTM {
    private String id;
    private String name;
    private String address;
    private String contactNumber;
    private double bloodPressure;
    private double temp;

    public PatientTM() {
    }

    public PatientTM(String id, String name, String address, double bloodPressure, double temp) {
        this.setId(id);
        this.setName(name);
        this.setAddress(address);
        this.setBloodPressure(bloodPressure);
        this.setTemp(temp);
    }

    public PatientTM(String id, String name, String address, String contactNumber, double bloodPressure, double temp) {
        this.setId(id);
        this.setName(name);
        this.setAddress(address);
        this.setContactNumber(contactNumber);
        this.setBloodPressure(bloodPressure);
        this.setTemp(temp);
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

    public double getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(double bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }
}
