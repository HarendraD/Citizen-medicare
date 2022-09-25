package controller;


import db.DbConnection;
import model.Nurse;
import model.Patient;
import view.tm.ChannelTM;
import view.tm.PatientTM;
import view.tm.PharmacistTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientController {
    public static boolean savePatient(Patient p1) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("INSERT INTO patient VALUES(?,?,?,?,?,?)");
        stm.setObject(1,p1.getId());
        stm.setObject(2,p1.getName());
        stm.setObject(3,p1.getAddress());
        stm.setObject(4,p1.getContactNumber());
        stm.setObject(5,p1.getBloodPressure());
        stm.setObject(6,p1.getTemp());

        return stm.executeUpdate()>0;
    }



    public static List<PatientTM> getAllPAtients() throws SQLException, ClassNotFoundException {
        Connection con=DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT * FROM patient");
        ResultSet rs = pstm.executeQuery();

        List<PatientTM> patientTMS = new ArrayList<>();

        while (rs.next()) {
            patientTMS.add(new PatientTM(
                    rs.getString("pId"),
                    rs.getString("name"),
                    rs.getString("address"),
                    rs.getString("contactNumber"),
                    rs.getDouble("bloodPressure"),
                    rs.getDouble("bodyTemperature")

            ));
        }


        return patientTMS;
    }

    public static boolean deletePatients(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("DELETE FROM patient WHERE pId=?");
        stm.setObject(1, id);
        return stm.executeUpdate() > 0;
    }
    public static boolean isExits(String patientId) throws SQLException, ClassNotFoundException {
        Connection con=DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT pId FROM patient");

        ResultSet rst = pstm.executeQuery();

        String id;
        while (rst.next()){
            id = rst.getString("pId");
            if(id.equals(patientId)){
                return  true;
            }
        }
        return false;
    }
    public static int getPatientCount() throws SQLException, ClassNotFoundException {
        PreparedStatement statement=DbConnection.getInstance().getConnection().prepareStatement("SELECT COUNT(*) FROM patient");
        ResultSet rst=statement.executeQuery();

        int count=0;
        while (rst.next()){
            count=rst.getInt("count(*)");
        }

        return count;
    }
    public static List<PatientTM> searchPatient(String value) throws SQLException, ClassNotFoundException {

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(
                "SELECT * FROM patient WHERE name LIKE '%"+value+"%'OR pId LIKE '%"+value+"%'OR address LIKE '%"+value+"%'");
        ResultSet resultSet = pstm.executeQuery();

        List<PatientTM> patientTMS = new ArrayList<>();
        while (resultSet.next()){
            patientTMS.add(new PatientTM(
                    resultSet.getString("pId"),
                    resultSet.getString("name"),
                    resultSet.getString("address"),
                    resultSet.getString("contactNumber"),
                    resultSet.getInt("bloodPressure"),
                    resultSet.getInt("bodyTemperature")

            ));
        }
        return patientTMS;
    }
    public static List<PatientTM> getPatients(String id) throws SQLException, ClassNotFoundException {
        List<PatientTM> patientTMS = new ArrayList<>();
        Connection con=DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("select channel.pId,patient.name,patient.address,patient.bloodPressure,patient.bodyTemperature from patient INNER JOIN channel ON channel.pId=patient.pId WHERE channel.doctorId='"+id+"'");
        ResultSet rs = pstm.executeQuery();

        while (rs.next()) {
            patientTMS.add(new PatientTM(
                    rs.getString("pId"),
                    rs.getString("name"),
                    rs.getString("address"),
                    rs.getDouble("bodyTemperature"),
                    rs.getDouble("bodyTemperature")

            ));
        }


        return patientTMS;
    }

    public static List<PatientTM> searchPatient2(String value,String id) throws SQLException, ClassNotFoundException {

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(
                "select channel.pId,patient.name,patient.address,patient.bloodPressure,patient.bodyTemperature from patient " +
                        "INNER JOIN channel ON channel.pId=patient.pId " +
                        "WHERE (patient.name LIKE '%"+value+"%' OR patient.address LIKE '%"+value+"%') AND channel.doctorId='"+id+"'");
        ResultSet resultSet = pstm.executeQuery();

        List<PatientTM> patientTMS = new ArrayList<>();
        while (resultSet.next()){
            patientTMS.add(new PatientTM(
                    resultSet.getString("pId"),
                    resultSet.getString("name"),
                    resultSet.getString("address"),
                    resultSet.getInt("bloodPressure"),
                    resultSet.getInt("bodyTemperature")

            ));
        }
        return patientTMS;
    }
    public static boolean updatePatient(Patient patient) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("UPDATE patient SET name=?, address=?, contactNumber=?, bloodPressure=?, bodyTemperature=? WHERE pId=?");
        stm.setObject(1,patient.getName());
        stm.setObject(2,patient.getAddress());
        stm.setObject(3,patient.getContactNumber());
        stm.setObject(4,patient.getBloodPressure());
        stm.setObject(5,patient.getTemp());
        stm.setObject(6,patient.getId());
        return stm.executeUpdate() > 0;
    }
}
