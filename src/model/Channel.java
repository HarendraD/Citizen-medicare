package model;

public class Channel {
    private String id;
    private String time;
    private String patientId;
    private String doctorId;
    private String speciality;
    private String nurseId;
    private double fee;

    public Channel() {
    }

    public Channel(String id, String time, String patientId, String doctorId, String speciality, String nurseId, double fee) {
        this.setId(id);
        this.setTime(time);
        this.setPatientId(patientId);
        this.setDoctorId(doctorId);
        this.setSpeciality(speciality);
        this.setNurseId(nurseId);
        this.setFee(fee);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getNurseId() {
        return nurseId;
    }

    public void setNurseId(String nurseId) {
        this.nurseId = nurseId;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "id='" + id + '\'' +
                ", time='" + time + '\'' +
                ", patientId='" + patientId + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", speciality='" + speciality + '\'' +
                ", nurseId='" + nurseId + '\'' +
                ", fee=" + fee +
                '}';
    }
}