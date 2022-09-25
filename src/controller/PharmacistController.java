package controller;

import db.DbConnection;
import model.Medicine;
import model.Nurse;
import model.Patient;
import sun.tools.jar.Main;
import view.tm.MedicineTM;
import view.tm.PharmacistTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PharmacistController {
    public static List<PharmacistTM> getMedicine() throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT orderDetails.mId, orderDetails.description, orderDetails.qty AS qtyNeed, medicine.qty FROM orderDetails INNER JOIN medicine ON orderDetails.mId=medicine.mId");
        ResultSet rs = pstm.executeQuery();


        List<PharmacistTM> medicines = new ArrayList<>();

        while (rs.next()) {
            medicines.add(new PharmacistTM(
                    rs.getString("mId"),
                    rs.getString("description"),
                    rs.getInt("qtyNeed"),
                    rs.getInt("qty")

            ));
        }
        return medicines;
    }

    public static List<PharmacistTM> searchMedicine(String value) throws SQLException, ClassNotFoundException {

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("SELECT orderDetails.mId, orderDetails.description, orderDetails.qty AS qtyNeed, medicine.qty " +
                "FROM orderDetails INNER JOIN medicine ON orderDetails.mId=medicine.mId " +
                "WHERE orderDetails.description LIKE '%"+value+"%'");
        ResultSet resultSet = pstm.executeQuery();

        List<PharmacistTM> medicine = new ArrayList<>();
        while (resultSet.next()){
            medicine.add(new PharmacistTM(
                    resultSet.getString("mId"),
                    resultSet.getString("description"),
                    resultSet.getInt("qty"),
                    resultSet.getInt("qty")

            ));
        }
        return medicine;
    }

    public List<String> getPatientIds() throws SQLException, ClassNotFoundException {
        String id="pId";
        String tableName="patient";
        List<String> ids=getIds(id,tableName);
        return ids;
    }
    public List<String> getMedicineIds() throws SQLException, ClassNotFoundException {
        String id="mId";
        String tableName="medicine";
        List<String> ids=getIds(id,tableName);
        return ids;
    }
    private List<String> getIds(String id, String tableName) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().
                getConnection().prepareStatement("SELECT "+id+" FROM " +tableName).executeQuery();
        List<String> ids = new ArrayList<>();
        while (rst.next()){
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }
    public Patient getPatient(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT name FROM patient WHERE pId=?");
        stm.setObject(1, id);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Patient(
                    rst.getString("name")
            );

        } else {
            return null;
        }
    }

    public Medicine getMedicine(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT description,size,price,qty FROM medicine WHERE mId=?");
        stm.setObject(1, id);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Medicine(
                    rst.getString("description"),
                    rst.getInt("size"),
                    rst.getDouble("price"),
                    rst.getInt("qty")
            );

        } else {
            return null;
        }
    }
    public static int getCountOfMedicineSold() throws SQLException, ClassNotFoundException {
        PreparedStatement statement=DbConnection.getInstance().getConnection().prepareStatement("SELECT SUM(orderDetails.qty) AS SumOfMedicineSold FROM orderDetails");
        ResultSet rst=statement.executeQuery();

        int count=0;
        while (rst.next()){
            count=rst.getInt("SumOfMedicineSold");
        }

        return count;
    }
    public static int getOrdersCount() throws SQLException, ClassNotFoundException {
        PreparedStatement statement=DbConnection.getInstance().getConnection().prepareStatement("SELECT COUNT(*) FROM orders");
        ResultSet rst=statement.executeQuery();

        int count=0;
        while (rst.next()){
            count=rst.getInt("COUNT(*)");
        }

        return count;
    }

}
