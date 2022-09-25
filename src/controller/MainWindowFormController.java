package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Login;
import util.ValidationUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class MainWindowFormController implements Initializable {
    public JFXTextField txtUserName;
    public JFXPasswordField txtPassword;
    public JFXButton btnLogin;
    public AnchorPane loginContext;
    public static String user;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern userNamePattern = Pattern.compile("^([A-z]{0,}+[0-9]{3})|([A-z]{2,})$");


    double x,y=8;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnLogin.setDisable(true);
        map.put(txtUserName,userNamePattern);




    }
    public void checkUserName(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map,btnLogin);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            }
        }

    }
    public void loginButton(MouseEvent mouseEvent) throws IOException {
        try {
            loginCheck();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }


    }
    private void loginCheck() throws SQLException, ClassNotFoundException, IOException {
        Login login =new LoginController().getlogin(txtUserName.getText());


        if (login == null) {
            new Alert(Alert.AlertType.ERROR,"Wrong UserName Or Password").show();

        } else if (txtUserName.getText().equalsIgnoreCase(login.getUserName()) && txtPassword.getText().equals(login.getPassword())) {
            user=txtUserName.getText();
            if (login.getUserRole().equalsIgnoreCase("Managment")) {
                Parent load = FXMLLoader.load(getClass().getResource("../view/HomeWindowForm.fxml"));
                load(load);


            } else if (login.getUserRole().equalsIgnoreCase("Doctor")) {
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/DoctorForm.fxml"));
                Parent parent = loader.load();
                DoctorFormController controller = loader.<DoctorFormController>getController();
                controller.lblDoctorId.setText(txtUserName.getText());
                Scene scene = new Scene(parent);
                Stage stage = (Stage) loginContext.getScene().getWindow();
                stage.setScene(scene);
                stage.show();

            } else if (login.getUserRole().equalsIgnoreCase("Reception")) {
                Parent load = FXMLLoader.load(getClass().getResource("../view/ReceptionDashBoardForm.fxml"));
                load(load);

            } else if (login.getUserRole().equalsIgnoreCase("Pharmacist")) {
                Parent load = FXMLLoader.load(getClass().getResource("../view/PharmacistForm.fxml"));
                load(load);

            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Login Succsess");
            alert.show();
        }
    }
    private void load(Parent load){
        Scene scene = new Scene(load);
        Stage stage = (Stage) loginContext.getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }



}




