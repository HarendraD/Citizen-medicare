package controller;

import db.DbConnection;
import model.Patient;
import model.User;
import view.tm.PatientTM;
import view.tm.UserTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserController {
    public static boolean saveUser(User user) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("INSERT INTO loginDetails VALUES(?,?,?,?)");
        stm.setObject(1,user.getId());
        stm.setObject(2,user.getUserName());
        stm.setObject(3,user.getPassword());
        stm.setObject(4,user.getUserRole());

        return stm.executeUpdate()>0;
    }
    public static List<UserTM> getAllUsers() throws SQLException, ClassNotFoundException {
        Connection con=DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT * FROM loginDetails");
        ResultSet rs = pstm.executeQuery();

        List<UserTM> userTMS = new ArrayList<>();

        while (rs.next()) {
            userTMS.add(new UserTM(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4)

            ));
        }


        return userTMS;
    }

    public static boolean deleteUser(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("DELETE FROM loginDetails WHERE userId=?");
        stm.setObject(1, id);
        return stm.executeUpdate() > 0;
    }

    public static boolean isExits(String userId) throws SQLException, ClassNotFoundException {
        Connection con=DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT userId FROM loginDetails");

        ResultSet rst = pstm.executeQuery();

        String id;
        if (rst.next()){
            id = rst.getString("userId");
            if(id.equals(userId)){
                return  true;
            }
        }
        return false;
    }
}
