package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import model.Channel;
import model.ChannelFee;
import model.Doctor;
import model.Patient;
import util.ValidationUtil;
import view.tm.ChannelTM;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class ManageChannelFormController {
    public AnchorPane loadContext;
    public Label lblTime;
    public Label lblDate;
    public TableView<ChannelTM> tblChannel;
    public TableColumn colId;
    public TableColumn colPatientId;
    public TableColumn colDoctorId;
    public TableColumn colDoctorSpeciality;
    public TableColumn colNurseId;
    public TableColumn colTime;
    public TableColumn colFee;
    public JFXTextField txtChannelId;
    public JFXComboBox<String> cmbPatientId;
    public JFXComboBox<String> cmbDoctorId;
    public JFXTextField txtSpeciality;
    public JFXComboBox<String> cmbNurseId;
    public JFXTextField txtFee;
    public JFXButton btnSaveChannel;
    private String patientName=null;
    private String doctorName=null;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern idPattern = Pattern.compile("^(C)[0-9]{3,4}$");
    Pattern specialityPattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern feePattern = Pattern.compile("^[0-9 ]{1,4}$");



    public void initialize() {
        btnSaveChannel.setDisable(true);
        storeValidations();
        loadDateAndTime();
        loadCombo();

        cmbDoctorId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->{
            setDoctorName(newValue);
        } );
        cmbPatientId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->{
            setPatientName(newValue);
        } );

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPatientId.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        colDoctorId.setCellValueFactory(new PropertyValueFactory<>("doctorId"));
        colNurseId.setCellValueFactory(new PropertyValueFactory<>("nurseId"));
        colDoctorSpeciality.setCellValueFactory(new PropertyValueFactory<>("speciality"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colFee.setCellValueFactory(new PropertyValueFactory<>("fee"));

        tblChannel.getColumns().setAll(colId,colPatientId,colDoctorId,colDoctorSpeciality,colNurseId,colTime,colFee);
        loadToTable();

        cmbDoctorId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->{
            loadDoctorSpeciality(newValue);
        } );


    }
    public void checkChannel(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map,btnSaveChannel);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            }
        }
    }
    private void storeValidations() {
        map.put(txtChannelId, idPattern);
        map.put(txtSpeciality, specialityPattern);
        map.put(txtFee, feePattern);
    }

    private void loadDoctorSpeciality(String newValue) {
        try {
            Doctor doctor = new ChannelController().getDoctor(newValue);
            if (doctor==null){

            }else{
                txtSpeciality.setText(doctor.getType());
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }
    private void setDoctorName(String newValue) {
        try {
            Doctor doctor = new ChannelController().getDoctor(newValue);
            if (doctor==null){

            }else{
                doctorName=doctor.getName();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }
    private void setPatientName(String newValue) {
        try {
            Patient patient = new ChannelController().getPatientName(newValue);
            if (patient==null){

            }else{
                patientName=patient.getName();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    private void loadToTable(){
        try {
            List<ChannelTM> allChannels=ChannelController.getAllChannels();
            ObservableList<ChannelTM> channelTMS= FXCollections.observableArrayList();
            for (ChannelTM channelTM:allChannels) {
                channelTMS.add(new ChannelTM(
                        channelTM.getId(),
                        channelTM.getPatientId(),
                        channelTM.getDoctorId(),
                        channelTM.getSpeciality(),
                        channelTM.getNurseId(),
                        channelTM.getTime(),
                        channelTM.getFee()
                ));
            }
            tblChannel.getItems().setAll(channelTMS);


        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }


    }
    public void saveChannelOnAction(MouseEvent mouseEvent) {
        String id=txtChannelId.getText();

        try {
            boolean isExits = ChannelController.isExits(id);
            if (isExits) {
                new Alert(Alert.AlertType.ERROR, "ID Already Exits !").show();
            } else {
                if (txtChannelId.getText().trim().isEmpty()) {
                    new Alert(Alert.AlertType.ERROR, "Please Enter Channel Details").show();
                } else {
                    Channel channel=new Channel(
                            txtChannelId.getText(),
                            lblTime.getText(),
                            cmbPatientId.getValue(),
                            cmbDoctorId.getValue(),
                            txtSpeciality.getText(),
                            cmbNurseId.getValue(),
                            Double.parseDouble(txtFee.getText()));
                    ChannelFee channelFee=new ChannelFee(
                            txtChannelId.getText(),
                            patientName,
                            doctorName,
                            lblTime.getText(),
                            lblDate.getText(),
                            Double.parseDouble(txtFee.getText()));

                    boolean isSaved=ChannelController.saveChannel(channel);

                    if (isSaved){

                        txtChannelId.clear();
                        txtFee.clear();
                        txtSpeciality.clear();
                        tblChannel.getItems().clear();

                        boolean isSavedFee=ChannelController.saveChannelFee(channelFee);
                        if (isSavedFee){
                            new Alert(Alert.AlertType.CONFIRMATION,"SAVED !").show();
                            loadToTable();
                        }
                    }else {
                        new Alert(Alert.AlertType.ERROR,"Try Again !").show();
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    private void loadCombo(){
        try {
            List<String> pIds = new ChannelController().getPatientIds();
            cmbPatientId.getItems().addAll(pIds);
            List<String> doctorIds=new ChannelController().getDoctorIds();
            cmbDoctorId.getItems().addAll(doctorIds);
            List<String> nurseIds=new ChannelController().getNurseIds();
            cmbNurseId.getItems().addAll(nurseIds);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
    private void loadDateAndTime() {
        Date date= new Date();
        SimpleDateFormat d=new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(d.format(date));

        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e->{
            LocalTime nowtime=LocalTime.now();
            lblTime.setText(
                    nowtime.getHour()+":"+nowtime.getMinute()+":"+ nowtime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }
    public void deleteSelectedChannel(MouseEvent mouseEvent) {
        try {
            String id = tblChannel.getSelectionModel().getSelectedItem().getId();
            boolean isDeleted=ChannelController.clearChannel(id);
            if(isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"DELETED !").show();
                loadToTable();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateChannelDetails(MouseEvent mouseEvent) {
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

    private void load(Parent load){
        Scene scene = new Scene(load);
        Stage stage = (Stage) loadContext.getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }



}
