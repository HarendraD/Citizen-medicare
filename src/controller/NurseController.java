package controller;

import db.DbConnection;
import model.Doctor;
import model.Medicine;
import model.Nurse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NurseController {
    public static boolean saveNurse(Nurse n1) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("INSERT INTO nurse VALUES(?,?,?,?)");
        stm.setObject(1,n1.getId());
        stm.setObject(2,n1.getName());
        stm.setObject(3,n1.getAddress());
        stm.setObject(4,n1.getContactNumber());
        return stm.executeUpdate()>0;
    }
    public static List<Nurse> getAllNurseDetails() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM nurse");
        ResultSet resultSet = stm.executeQuery();

        List<Nurse> nurseList = new ArrayList<>();

        while (resultSet.next()) {
            nurseList.add(new Nurse(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            ));
        }

        return nurseList;
    }
    public Nurse getNurse(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM nurse WHERE nurseId=?");
        stm.setObject(1, id);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Nurse(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)
            );

        } else {
            return null;
        }
    }
    public static boolean updateNurse(Nurse nurse) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("UPDATE nurse SET name=?, address=?, contactNumber=?WHERE nurseId=?");
        stm.setObject(1,nurse.getName());
        stm.setObject(2,nurse.getAddress());
        stm.setObject(3,nurse.getContactNumber());
        stm.setObject(4,nurse.getId());
        return stm.executeUpdate() > 0;
    }
    public static boolean deleteNurse(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("DELETE FROM nurse WHERE nurseId=?");
        stm.setObject(1, id);
        return stm.executeUpdate() > 0;
    }
    public static boolean isExits(String nurseId) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT nurseId FROM nurse");

        ResultSet rst = stm.executeQuery();

        String id;
        while (rst.next()){
            id = rst.getString("nurseId");
            if(id.equals(nurseId)){
                return  true;
            }
        }
        return false;
    }
    public static int getNurseCount() throws SQLException, ClassNotFoundException {
        PreparedStatement statement=DbConnection.getInstance().getConnection().prepareStatement("SELECT COUNT(*) FROM nurse");
        ResultSet rst=statement.executeQuery();

        int count=0;
        while (rst.next()){
            count=rst.getInt("count(*)");
        }

        return count;
    }

}
