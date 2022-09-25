package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Patient;
import model.User;
import util.ValidationUtil;
import view.tm.PatientTM;
import view.tm.UserTM;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class ManageUsersFormController {
    public AnchorPane loadContext;
    public Label lblTime;
    public Label lblDate;
    public JFXTextField txtUserId;
    public JFXTextField txtUserName;
    public JFXTextField txtPassword;
    public JFXTextField txtUserRole;
    public TableView<UserTM> tblUsers;
    public TableColumn colUserId;
    public TableColumn colUserName;
    public TableColumn colPassword;
    public TableColumn colUserRole;
    public JFXButton btnSaveUser;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern idPattern = Pattern.compile("^(U)[0-9]{3,4}$");
    Pattern userNamePattern = Pattern.compile("^([A-z]{1,}+[0-9]{3})|([A-z]{2,})$");
    Pattern passwordPattern = Pattern.compile("^([A-z]{1,})|[0-9]{3,}$");
    Pattern userRolePattern = Pattern.compile("^[A-z]{3,}$");

    public void initialize() {
        btnSaveUser.setDisable(true);
        storeValidations();
        loadDateAndTime();
        colUserId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colUserRole.setCellValueFactory(new PropertyValueFactory<>("userRole"));

        tblUsers.getColumns().setAll(colUserId, colUserName, colPassword, colUserRole);
        loadToTable();

    }
    public void checkUsers(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map,btnSaveUser);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            }
        }

    }
    private void storeValidations() {
        map.put(txtUserId, idPattern);
        map.put(txtUserName, userNamePattern);
        map.put(txtPassword, passwordPattern);
        map.put(txtUserRole, userRolePattern);
    }
    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(d.format(date));

        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime nowtime = LocalTime.now();
            lblTime.setText(
                    nowtime.getHour() + ":" + nowtime.getMinute() + ":" + nowtime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }




    public void saveUserOnAction(MouseEvent mouseEvent) {
        String id=txtUserId.getText();
        try {
            boolean isExits=UserController.isExits(id);
            if (isExits){
                new Alert(Alert.AlertType.ERROR,"ID Already Exits !").show();
            }else{
                if (txtUserId.getText().trim().isEmpty()){
                    new Alert(Alert.AlertType.ERROR,"Please Enter User Details").show();
                }else {
                    User user = new User(txtUserId.getText(),
                            txtUserName.getText(),
                            txtPassword.getText(),
                            txtUserRole.getText());
                    boolean isSave = UserController.saveUser(user);
                    if (isSave) {
                        new Alert(Alert.AlertType.CONFIRMATION, "SAVED !").show();
                        txtUserId.clear();
                        txtUserName.clear();
                        txtPassword.clear();
                        txtUserRole.clear();
                        loadToTable();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Try Again !").show();
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
        }
    }

    private void loadToTable(){
        try {
            List<UserTM> allUsers=UserController.getAllUsers();
            ObservableList<UserTM> userList= FXCollections.observableArrayList();
            for (UserTM userTM : allUsers) {
                userList.add(new UserTM(
                        userTM.getId(),
                        userTM.getUserName(),
                        userTM.getPassword(),
                        userTM.getUserRole()
                ));
            }
            tblUsers.getItems().setAll(userList);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteUser(MouseEvent mouseEvent) {
        try {
            String id =tblUsers.getSelectionModel().getSelectedItem().getId();
            boolean isDeleted =UserController.deleteUser(id);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"DELETED !").show();
                loadToTable();
            }else {
                new Alert(Alert.AlertType.ERROR,"ERROR !").show();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    public void openManageNurseForm(MouseEvent mouseEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/ManageNurseForm.fxml"));
        load(load);
    }

    public void openHomeForm(MouseEvent mouseEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/HomeWindowForm.fxml"));
        load(load);
    }

    public void openManageDoctorForm(MouseEvent mouseEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/ManageDoctorForm.fxml"));
        load(load);
    }

    public void openManageMedicineForm(MouseEvent mouseEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/ManageMedicineForm.fxml"));
        load(load);
    }

    public void openReportsForm(MouseEvent mouseEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/ReportsForm.fxml"));
        load(load);
    }

    public void openManageUsers(MouseEvent mouseEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/ManageUsersForm.fxml"));
        load(load);
    }

    public void logOutButton(MouseEvent mouseEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/MainWindowForm.fxml"));
        load(load);
    }

    private void load(Parent load){
        Scene scene = new Scene(load);
        Stage stage = (Stage) loadContext.getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }



}
