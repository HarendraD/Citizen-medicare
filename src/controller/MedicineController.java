package controller;

import db.DbConnection;
import model.Medicine;
import model.SuppliedDetails;
import view.tm.MedicineTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicineController {

    public List<String> getSupplierIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().
                getConnection().prepareStatement("SELECT supId FROM supplier").executeQuery();
        List<String> ids = new ArrayList<>();
        while (rst.next()){
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }

    public boolean saveMedicine(Medicine medicine ,SuppliedDetails details )  {
        Connection con=null;
        try {
            con = DbConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            PreparedStatement stm = con.prepareStatement("INSERT INTO medicine VALUES(?,?,?,?,?,?)");
            stm.setObject(1,medicine.getId());
            stm.setObject(2,medicine.getDescription());
            stm.setObject(3,medicine.getType());
            stm.setObject(4,medicine.getSize());
            stm.setObject(5,medicine.getPrice());
            stm.setObject(6,medicine.getQty());

            if (stm.executeUpdate() > 0){
                if (saveSupliedDetails(details)){
                    return true;
                }else{
                    con.rollback();
                    return false;
                }
            }else{
                con.rollback();
                return false;
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {

                con.setAutoCommit(true);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }


    private boolean saveSupliedDetails(SuppliedDetails details) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        con.setAutoCommit(false);
        PreparedStatement stm = con.prepareStatement("INSERT INTO suppliedDetails VALUES(?,?,?)");
        stm.setObject(1,details.getMedId());
        stm.setObject(2,details.getSupId());
        stm.setObject(3,details.getQty());

        return stm.executeUpdate()>0;

    }



    public static boolean updateMedicine(Medicine medicine, SuppliedDetails updateDetails)  {
        Connection con=null;
        try {
            con = DbConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            PreparedStatement stm = con.prepareStatement("UPDATE medicine SET description=?, type=?, size=?, price=?, qty=? WHERE mId=?");

            stm.setObject(1,medicine.getDescription());
            stm.setObject(2,medicine.getType());
            stm.setObject(3,medicine.getSize());
            stm.setObject(4,medicine.getPrice());
            stm.setObject(5,medicine.getQty());
            stm.setObject(6,medicine.getId());

            if (stm.executeUpdate() > 0){
                if (updateSuppliedDetails(updateDetails)){
                    return true;
                }else{
                    con.rollback();
                    return false;
                }
            }else{
                con.rollback();
                return false;
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {

                con.setAutoCommit(true);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }


        return false;
    }
    public static List<MedicineTM> getAllMedicine() throws SQLException, ClassNotFoundException {
        Connection con=DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("select medicine.mId,medicine.description,medicine.type,medicine.size,medicine.price,medicine.qty,suppliedDetails.supId from medicine left join suppliedDetails on medicine.mId=suppliedDetails.medicineId;");
        ResultSet rs = pstm.executeQuery();

        List<MedicineTM> medicines = new ArrayList<>();

        while (rs.next()) {
            medicines.add(new MedicineTM(
                    rs.getString("mId"),
                    rs.getString("description"),
                    rs.getString("type"),
                    rs.getInt("size"),
                    rs.getDouble("price"),
                    rs.getString("supId"),
                    rs.getInt("qty")

            ));
        }


        return medicines;
    }

    private static boolean updateSuppliedDetails(SuppliedDetails updateDetails) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        con.setAutoCommit(false);
        PreparedStatement stm = con.prepareStatement("UPDATE suppliedDetails SET qty=?, supId=? WHERE medicineId=?");
        stm.setObject(1,updateDetails.getQty());
        stm.setObject(2,updateDetails.getSupId());
        stm.setObject(3,updateDetails.getMedId());

        return stm.executeUpdate()>0;

    }
    public Medicine getMedicine(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT medicine.mId,medicine.description,medicine.type,medicine.size,medicine.price,medicine.qty,suppliedDetails.supId,supplier.name FROM suppliedDetails " +
                        "INNER JOIN medicine ON medicine.mId=suppliedDetails.medicineId " +
                        "INNER JOIN supplier ON supplier.supId=suppliedDetails.supId WHERE medicine.mId='"+id+"'");


        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Medicine(
                    rst.getString("mID"),
                    rst.getString("description"),
                    rst.getString("type"),
                    rst.getString("supId"),
                    rst.getString("name"),
                    rst.getInt("size"),
                    rst.getDouble("price"),
                    rst.getInt("qty")
            );

        } else {
            return null;
        }
    }
    public static boolean deleteMedicine(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("DELETE FROM medicine WHERE mId=?");
        stm.setObject(1, id);
        return stm.executeUpdate() > 0;
    }

    public static boolean isExits(String medicineId) throws SQLException, ClassNotFoundException {
        Connection con=DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT mId FROM medicine");

        ResultSet rst = pstm.executeQuery();

        String id;
        while (rst.next()){
            id = rst.getString("mId");
            if(id.equals(medicineId)){
                return  true;
            }
        }
        return false;
    }
}
