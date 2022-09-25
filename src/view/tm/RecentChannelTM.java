package view.tm;

public class RecentChannelTM {
    private String id;
    private String patientName;
    private String doctorName;
    private double fee;
    private String time;

    public RecentChannelTM() {
    }

    public RecentChannelTM(String id, String patientName, String doctorName, double fee, String time) {
        this.setId(id);
        this.setPatientName(patientName);
        this.setDoctorName(doctorName);
        this.setFee(fee);
        this.setTime(time);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "RecentChannelTM{" +
                "id='" + id + '\'' +
                ", patientName='" + patientName + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", fee=" + fee +
                ", time='" + time + '\'' +
                '}';
    }
}
