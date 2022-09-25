package controller;

import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Login;
import view.tm.PatientTM;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DoctorFormController{
    public StackPane doctorContext;
    public Label lblTime;
    public Label lblDate;
    public TableView<PatientTM> tblPatients;
    public TableColumn colPatientId;
    public TableColumn colPatientName;
    public TableColumn colAddress;
    public TableColumn colBp;
    public TableColumn colTemp;
    public JFXTextField txtSearchPatient;
    public Label lblDoctorId;
    public Label lblAppointmentCount;


    public void initialize() {
        loadDateAndTime();

        lblDoctorId.setText(MainWindowFormController.user);
        colPatientId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPatientName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colBp.setCellValueFactory(new PropertyValueFactory<>("bloodPressure"));
        colTemp.setCellValueFactory(new PropertyValueFactory<>("temp"));

        tblPatients.getColumns().setAll(colPatientId,colPatientName,colAddress,colBp,colTemp);
        try {
            loadToTable();

            int count=ChannelController.getChannelCount2(lblDoctorId.getText());
            if (count<=9){
                lblAppointmentCount.setText("00"+String.valueOf(count));
            }else if (count<=99){
                lblAppointmentCount.setText("0"+String.valueOf(count));
            }else {
                lblAppointmentCount.setText(String.valueOf(count));
            }
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

    private void loadToTable() throws SQLException, ClassNotFoundException {
            List<PatientTM> allPatients = PatientController.getPatients(lblDoctorId.getText());
            ObservableList<PatientTM> patientTMS = FXCollections.observableArrayList();
            for (PatientTM patientTM : allPatients) {
                patientTMS.add(new PatientTM(
                        patientTM.getId(),
                        patientTM.getName(),
                        patientTM.getAddress(),
                        patientTM.getBloodPressure(),
                        patientTM.getTemp()
                ));
            }
            tblPatients.getItems().setAll(patientTMS);


    }

    public void logOutOnAction(MouseEvent mouseEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/MainWindowForm.fxml"));
        Scene scene = new Scene(load);
        Stage stage = (Stage) doctorContext.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void searchPatientOnAction(KeyEvent keyEvent) {
        try {
            List<PatientTM> patientTMS = PatientController.searchPatient2(txtSearchPatient.getText(),lblDoctorId.getText());
            setMedicineToTable((ArrayList<PatientTM>) patientTMS);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    private void setMedicineToTable(ArrayList<PatientTM> patients) {
        ObservableList<PatientTM> patientTMS= FXCollections.observableArrayList();
        patients.forEach(e -> {
            patientTMS.add(
                    new PatientTM(e.getId(), e.getName(), e.getAddress(), e.getBloodPressure(),e.getTemp()));
        });
        tblPatients.setItems(patientTMS);
    }

    public void deletePatientOnAction(MouseEvent mouseEvent) {
    }


}
