package controller;

import db.DbConnection;
import model.Doctor;
import view.tm.DoctorTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DoctorController {
    public static boolean saveDoctor(Doctor d1) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        String query="INSERT INTO doctor VALUES(?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,d1.getId());
        stm.setObject(2,d1.getName());
        stm.setObject(3,d1.getAddress());
        stm.setObject(4,d1.getContactNumber());
        stm.setObject(5,d1.getType());
        return stm.executeUpdate()>0;
    }

    public static List<Doctor> getAllDoctorsDetails() throws SQLException, ClassNotFoundException {
        Connection con=DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT * FROM doctor");
        ResultSet rs = pstm.executeQuery();

        List<Doctor> doctors = new ArrayList<>();

        while (rs.next()) {
            doctors.add(new Doctor(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5)

            ));
        }

        return doctors;
    }
    public Doctor getDoctor(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM doctor WHERE doctorId=?");
        stm.setObject(1, id);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Doctor(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );

        } else {
            return null;
        }
    }
    public static boolean updateDoctor(Doctor doctor) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("UPDATE doctor SET name=?, address=?, contactNumber=?, type=? WHERE doctorId=?");
        pstm.setObject(1,doctor.getName());
        pstm.setObject(2,doctor.getAddress());
        pstm.setObject(3,doctor.getContactNumber());
        pstm.setObject(4,doctor.getType());
        pstm.setObject(5,doctor.getId());
        return pstm.executeUpdate() > 0;
    }

    public static boolean isExits(String doctorId) throws SQLException, ClassNotFoundException {
        Connection con=DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT doctorId FROM doctor");

        ResultSet rst = pstm.executeQuery();

        String id;
        while (rst.next()){
            id = rst.getString("doctorId");
            if(id.equals(doctorId)){
                return  true;
            }
        }
        return false;
    }

    public static boolean deleteDoctor(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("DELETE FROM doctor WHERE doctorId=?");
        stm.setObject(1, id);
        return stm.executeUpdate() > 0;
    }

    public static List<DoctorTM> getDoctorDetails() throws SQLException, ClassNotFoundException {
        Connection con=DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT doctorId,name,contactNumber,type FROM doctor");
        ResultSet rs = pstm.executeQuery();

        List<DoctorTM> doctorTMS = new ArrayList<>();

        while (rs.next()) {
            doctorTMS.add(new DoctorTM(
                    rs.getString("doctorId"),
                    rs.getString("name"),
                    rs.getString("contactNumber"),
                    rs.getString("type")

            ));
        }

        return doctorTMS;
    }
    public static int getDoctorCount() throws SQLException, ClassNotFoundException {
        PreparedStatement statement=DbConnection.getInstance().getConnection().prepareStatement("SELECT COUNT(*) FROM doctor");
        ResultSet rst=statement.executeQuery();

        int count=0;
        while (rst.next()){
            count=rst.getInt("count(*)");
        }

        return count;
    }
}

