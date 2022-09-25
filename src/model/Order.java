package model;

import java.util.ArrayList;

public class Order {
    private String orderId;
    private String pId;
    private String patientName;
    private double subTotal;
    private String time;
    private String date;
    private ArrayList<OrderDetails> medicines;

    public Order() {
    }

    public Order(String orderId, String patientName, double subTotal, String time, String date) {
        this.orderId = orderId;
        this.patientName = patientName;
        this.subTotal = subTotal;
        this.time = time;
        this.date = date;
    }

    public Order(String orderId, String patientName, double subTotal, String time) {
        this.orderId = orderId;
        this.patientName = patientName;
        this.subTotal = subTotal;
        this.time = time;
    }

    public Order(String orderId, String pId, String patientName, double subTotal, String time, String date, ArrayList<OrderDetails> medicines) {
        this.setOrderId(orderId);
        this.setpId(pId);
        this.setPatientName(patientName);
        this.setSubTotal(subTotal);
        this.setTime(time);
        this.setDate(date);
        this.setMedicines(medicines);
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<OrderDetails> getMedicines() {
        return medicines;
    }

    public void setMedicines(ArrayList<OrderDetails> medicines) {
        this.medicines = medicines;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", pId='" + pId + '\'' +
                ", patientName='" + patientName + '\'' +
                ", subTotal=" + subTotal +
                ", time='" + time + '\'' +
                ", date='" + date + '\'' +
                ", medicines=" + medicines +
                '}';
    }
}
