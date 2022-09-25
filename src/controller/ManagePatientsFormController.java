package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import model.Medicine;
import model.Patient;
import model.SuppliedDetails;
import util.ValidationUtil;
import view.tm.ChannelTM;
import view.tm.PatientTM;
import view.tm.PharmacistTM;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;
import java.util.regex.Pattern;

public class ManagePatientsFormController {
    public AnchorPane loadContext;
    public Label lblTime;
    public Label lblDate;
    public JFXTextField txtPatientId;
    public JFXTextField txtPatientName;
    public JFXTextField txtAddress;
    public JFXTextField txtContactNumber;
    public JFXTextField txtBP;
    public JFXTextField txtTemp;
    public TableView<PatientTM> tblPatients;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colContactNumber;
    public TableColumn colBp;
    public TableColumn colTemp;
    public JFXTextField txtSearchPatient;
    public JFXButton btnSavePatient;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern idPattern = Pattern.compile("^(P)[0-9]{3,4}$");
    Pattern namePattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern addressPattern = Pattern.compile("^[A-z0-9/ ]{6,30}$");
    Pattern contactNumberPattern = Pattern.compile("^[0-9]{3}+(-)[0-9]{7}$");
    Pattern bPPattern = Pattern.compile("^[0-9]{2,3}$");
    Pattern tempPattern = Pattern.compile("^[0-9]{2,3}$");

    public void initialize() {
        btnSavePatient.setDisable(true);
        storeValidations();
        loadDateAndTime();
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContactNumber.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        colBp.setCellValueFactory(new PropertyValueFactory<>("bloodPressure"));
        colTemp.setCellValueFactory(new PropertyValueFactory<>("temp"));

        tblPatients.getColumns().setAll(colId,colName,colAddress,colContactNumber,colBp,colTemp);
        loadToTable();

    }

    public void checkPatient(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map,btnSavePatient);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            }
        }
    }
    private void storeValidations() {
        map.put(txtPatientId, idPattern);
        map.put(txtPatientName, namePattern);
        map.put(txtAddress, addressPattern);
        map.put(txtContactNumber, contactNumberPattern);
        map.put(txtBP, bPPattern);
        map.put(txtTemp, tempPattern);
    }

    private void loadToTable() {
        try {
            List<PatientTM> allPatients = PatientController.getAllPAtients();
            ObservableList<PatientTM> patientTMS = FXCollections.observableArrayList();
            for (PatientTM patientTM : allPatients) {
                patientTMS.add(new PatientTM(
                        patientTM.getId(),
                        patientTM.getName(),
                        patientTM.getAddress(),
                        patientTM.getContactNumber(),
                        patientTM.getBloodPressure(),
                        patientTM.getTemp()
                ));
            }
            tblPatients.getItems().setAll(patientTMS);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

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

    public void openReceptionDashBoardForm(MouseEvent mouseEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/ReceptionDashBoardForm.fxml"));
        load(load);
    }

    public void openManagePatients(MouseEvent mouseEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/ManagePatientsForm.fxml"));
        load(load);
    }

    public void openManageChannelsForm(MouseEvent mouseEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/ManageChannelForm.fxml"));
        load(load);
    }

    public void backToMain(MouseEvent mouseEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/MainWindowForm.fxml"));
        load(load);
    }

    private void setMedicineToTable(ArrayList<PatientTM> patients) {

        ObservableList<PatientTM> patientTMS= FXCollections.observableArrayList();
        patients.forEach(e -> {
            patientTMS.add(
                    new PatientTM(e.getId(), e.getName(), e.getAddress(), e.getContactNumber(), e.getBloodPressure(),e.getTemp()));
        });
        tblPatients.setItems(patientTMS);

    }

    public void savePatientOnAction(MouseEvent mouseEvent) {
        String id=txtPatientId.getText();
        try {
            boolean isExits=PatientController.isExits(id);
            if (isExits){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("This Patient Is Alredy Exits");
                alert.setContentText("Do you want to Update This Patient's Details PRESS OK ");



                Optional<ButtonType> optional=alert.showAndWait();
                if (optional==null){
                    alert.close();
                }else if (optional.get()==ButtonType.OK){
                    updatePatientDetails();
                }else if (optional.get()==ButtonType.CANCEL){
                    alert.close();
                }
            }else{
                if (txtPatientId.getText().trim().isEmpty()){
                    new Alert(Alert.AlertType.ERROR,"Please Enter Patient Details").show();
                }else {
                    Patient patient = new Patient(txtPatientId.getText(),
                            txtPatientName.getText(),
                            txtAddress.getText(),
                            txtContactNumber.getText(),
                            Double.parseDouble(txtBP.getText()),
                            Double.parseDouble(txtTemp.getText()));

                    boolean isSave = PatientController.savePatient(patient);
                    if (isSave) {
                        new Alert(Alert.AlertType.CONFIRMATION, "SAVED !").show();
                        txtPatientId.clear();
                        txtPatientName.clear();
                        txtAddress.clear();
                        txtContactNumber.clear();
                        txtBP.clear();
                        txtTemp.clear();
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

    private void updatePatientDetails() {
        Patient patient =new Patient(
                txtPatientId.getText(),
                txtPatientName.getText(),
                txtAddress.getText(),
                txtContactNumber.getText(),
                Integer.parseInt(txtBP.getText()),
                Integer.parseInt(txtTemp.getText())
        );

        try {
            boolean isUpdated = PatientController.updatePatient(patient);
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION,"UPDATED !").show();
                loadToTable();
            }else{
                new Alert(Alert.AlertType.ERROR,"Try Again  !").show();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }


    }


    public void deleteSelectedPatient(MouseEvent mouseEvent) {

        try {
            String id =tblPatients.getSelectionModel().getSelectedItem().getId();
            boolean isDeleted =PatientController.deletePatients(id);
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

    private void load(Parent load){
        Scene scene = new Scene(load);
        Stage stage = (Stage) loadContext.getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    public void searchPatient(KeyEvent keyEvent) {
        try {
            List<PatientTM> patientTMS = PatientController.searchPatient(txtSearchPatient.getText());
            setMedicineToTable((ArrayList<PatientTM>) patientTMS);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }


}