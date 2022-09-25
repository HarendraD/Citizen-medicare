package view.tm;

public class ChannelTM {
    private String id;
    private String patientId;
    private String doctorId;
    private String speciality;
    private String nurseId;
    private String time;
    private double fee;

    public ChannelTM() {
    }

    public ChannelTM(String id, String patientId, String doctorId, String speciality, String nurseId, String time, double fee) {
        this.setId(id);
        this.setPatientId(patientId);
        this.setDoctorId(doctorId);
        this.setSpeciality(speciality);
        this.setNurseId(nurseId);
        this.setTime(time);
        this.setFee(fee);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    @Override
    public String toString() {
        return "ChannelTM{" +
                "id='" + id + '\'' +
                ", patientId='" + patientId + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", speciality='" + speciality + '\'' +
                ", nurseId='" + nurseId + '\'' +
                ", time='" + time + '\'' +
                ", fee=" + fee +
                '}';
    }
}
