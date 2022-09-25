package model;

public class ChannelFee {
    private String cID;
    private String patientName;
    private String doctorName;
    private String time;
    private String date;
    private double fee;

    public ChannelFee() {
    }

    public ChannelFee(String cID, String patientName, String doctorName, String time, String date, double fee) {
        this.setcID(cID);
        this.setPatientName(patientName);
        this.setDoctorName(doctorName);
        this.setTime(time);
        this.setDate(date);
        this.setFee(fee);
    }

    public String getcID() {
        return cID;
    }

    public void setcID(String cID) {
        this.cID = cID;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
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

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    @Override
    public String toString() {
        return "ChannelFee{" +
                "cID='" + cID + '\'' +
                ", patientName='" + patientName + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", time='" + time + '\'' +
                ", date='" + date + '\'' +
                ", fee=" + fee +
                '}';
    }
}
