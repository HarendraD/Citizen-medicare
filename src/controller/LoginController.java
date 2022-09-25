package controller;

import db.DbConnection;
import model.Login;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {


    public Login getlogin(String userName) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT userName,password,userRole FROM loginDetails WHERE userName=?");
        stm.setObject(1, userName);
        ResultSet rst = stm.executeQuery();
        if (rst.next()) {

            return new Login(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3)
            );

        } else {
            return null;
        }
    }

}
