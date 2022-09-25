package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Doctor;
import view.tm.DoctorTM;
import view.tm.PharmacistTM;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;


public class ReceptionDashBoardFormController {
    public AnchorPane loadContext;
    public Label lblTime;
    public Label lblDate;
    public TableView<DoctorTM> tblAvailableDoctors;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colSpeciality;
    public TableColumn colContactNumber;
    public Label lblPatientCount;
    public Label lblChannelCount;
    public Label lblNursesCount;


    public void initialize() {
        loadDateAndTime();
        setLblCount();

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContactNumber.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        colSpeciality.setCellValueFactory(new PropertyValueFactory<>("type"));

        loadToTable();
    }
    private void loadToTable(){
        try {
            List<DoctorTM> doctorTMS=DoctorController.getDoctorDetails();
            ObservableList<DoctorTM> doctorTMObservableList= FXCollections.observableArrayList();
            doctorTMS.forEach(e -> {
                doctorTMObservableList.add(
                        new DoctorTM(e.getId(), e.getName(), e.getContactNumber(), e.getType()));
            });
            tblAvailableDoctors.setItems(doctorTMObservableList);

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
    private void setLblCount(){
        try {

            int patientCount=PatientController.getPatientCount();
            int channelCount=ChannelController.getChannelCount(lblDate.getText());
            int nurseCount=NurseController.getNurseCount();
            if (nurseCount<=9|channelCount<=9|patientCount<=9){
                lblPatientCount.setText("00"+String.valueOf(patientCount));
                lblChannelCount.setText("00"+String.valueOf(channelCount));
                lblNursesCount.setText("00"+String.valueOf(nurseCount));
            }else if (nurseCount<=99|channelCount<=99|patientCount<=99){
                lblPatientCount.setText("0"+String.valueOf(patientCount));
                lblChannelCount.setText("0"+String.valueOf(channelCount));
                lblNursesCount.setText("0"+String.valueOf(nurseCount));
            }else {
                lblPatientCount.setText(String.valueOf(patientCount));
                lblChannelCount.setText(String.valueOf(channelCount));
                lblNursesCount.setText(String.valueOf(nurseCount));
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
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



    private void load(Parent load){
        Scene scene = new Scene(load);
        Stage stage = (Stage) loadContext.getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }
}
