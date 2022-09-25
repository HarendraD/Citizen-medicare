package controller;

import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import view.tm.ChannelTM;
import view.tm.RecentChannelTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class HomeWindowFormController{
    public AnchorPane homeContext;
    public Label lblTime;
    public Label lblDate;
    public AnchorPane loadContext;
    public Label lblPatientCount;
    public Label lblDoctorsCount;
    public Label lblIcomeFromChannaling;
    public Label lblIncomeFormMedicine;
    public Label lblNursesCount;
    public TableView<RecentChannelTM> tblAppointment;
    public TableColumn colId;
    public TableColumn colPatientName;
    public TableColumn colDoctorName;
    public TableColumn colTime;
    public Label lblChannelCount;
    public TableColumn colFee;

    public void initialize() {
        loadDateAndTime();
        setLblCount();
        try {
            int icomeFromChannels=ChannelController.getChanelIncome(lblDate.getText());
            lblIcomeFromChannaling.setText(String.valueOf(icomeFromChannels));
            int incomeFromSellMedicine=OrderController.getIncomeFromMedicine(lblDate.getText());
            lblIncomeFormMedicine.setText(String.valueOf(incomeFromSellMedicine));
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPatientName.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        colDoctorName.setCellValueFactory(new PropertyValueFactory<>("doctorName"));
        colFee.setCellValueFactory(new PropertyValueFactory<>("fee"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));


        tblAppointment.getColumns().setAll(colId,colPatientName,colDoctorName,colFee,colTime);
        loadToTable();
    }


    private void loadToTable(){
        try {
            List<RecentChannelTM> allRecentChannels=ChannelController.getRecentChannel();
            ObservableList<RecentChannelTM> channelTMS= FXCollections.observableArrayList();
            for (RecentChannelTM recentChannelTM:allRecentChannels) {
                channelTMS.add(new RecentChannelTM(
                        recentChannelTM.getId(),
                        recentChannelTM.getPatientName(),
                        recentChannelTM.getDoctorName(),
                        recentChannelTM.getFee(),
                        recentChannelTM.getTime()

                ));
            }
            tblAppointment.getItems().setAll(channelTMS);


        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }


    }
    private void countIncomeFromChannels(){
        int income=0;

    }
    private void setLblCount(){
        try {

            int patientCount=PatientController.getPatientCount();
            int channelCount=ChannelController.getChannelCount(lblDate.getText());
            int nurseCount=NurseController.getNurseCount();
            int doctorCount=DoctorController.getDoctorCount();
            if (nurseCount<=9|channelCount<=9|patientCount<=9|doctorCount<=9){
                lblPatientCount.setText("00"+String.valueOf(patientCount));
                lblChannelCount.setText("00"+String.valueOf(channelCount));
                lblDoctorsCount.setText("00"+String.valueOf(doctorCount));
                lblNursesCount.setText("00"+String.valueOf(nurseCount));
            }else if (nurseCount<=99|channelCount<=99|patientCount<=99|doctorCount<=99){
                lblPatientCount.setText("0"+String.valueOf(patientCount));
                lblChannelCount.setText("0"+String.valueOf(channelCount));
                lblDoctorsCount.setText("0"+String.valueOf(doctorCount));
                lblNursesCount.setText("0"+String.valueOf(nurseCount));
            }else {
                lblPatientCount.setText(String.valueOf(patientCount));
                lblChannelCount.setText(String.valueOf(channelCount));
                lblDoctorsCount.setText(String.valueOf(doctorCount));
                lblNursesCount.setText(String.valueOf(nurseCount));
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

    public void openHomeForm(MouseEvent mouseEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/HomeWindowForm.fxml"));
        load(load);
    }

    public void openManageDoctorForm(MouseEvent mouseEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/ManageDoctorForm.fxml"));
        load(load);
    }

    public void openManageNurseForm(MouseEvent mouseEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/ManageNurseForm.fxml"));
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
        Stage stage = (Stage) homeContext.getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }


}
