package controller;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Doctor;
import util.LoadToTableEvent;
import view.tm.DoctorTM;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public class ManageDotorFormController implements LoadToTableEvent {

    public Label lblTime;
    public Label lblDate;
    public TableView<DoctorTM> tblDoctors;
    public TableColumn colDoctorId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colContactNumber;
    public TableColumn colSpecialityType;
    public AnchorPane loadContext;


    public void initialize() {
        loadDateAndTime();

        colDoctorId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContactNumber.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        colSpecialityType.setCellValueFactory(new PropertyValueFactory<>("type"));


        tblDoctors.getColumns().setAll(colDoctorId, colName, colAddress, colContactNumber, colSpecialityType);

        loadDataToTable();
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



    private void loadDataToTable(){

        try {
            List<Doctor> allDoctors = DoctorController.getAllDoctorsDetails();
            ObservableList<DoctorTM> obList= FXCollections.observableArrayList();
            for (Doctor doctor:allDoctors) {
                obList.add(new DoctorTM(
                        doctor.getId(),
                        doctor.getName(),
                        doctor.getAddress(),
                        doctor.getContactNumber(),
                        doctor.getType()
                ));
            }
            tblDoctors.getItems().setAll(obList);


        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
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

    public void logOutButton(MouseEvent mouseEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/MainWindowForm.fxml"));
        load(load);
    }

    public void openAddDoctorForm(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/AddDoctorForm.fxml"));
        Parent parent = loader.load();
        AddDoctorFormController controller = loader.<AddDoctorFormController>getController();
        controller.setTable(this);
        Scene scene=new Scene(parent);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void openUpdateDoctorForm(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/UpdateDoctorForm.fxml"));
        Parent parent = loader.load();
        UpdateDoctorFormController controller = loader.<UpdateDoctorFormController>getController();
        controller.setTable(this);
        Scene scene=new Scene(parent);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void deleteDoctor(MouseEvent mouseEvent) {

        try {
            String id = tblDoctors.getSelectionModel().getSelectedItem().getId();
            boolean isDeleted = DoctorController.deleteDoctor(id);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"Deleted !").show();
                loadDataToTable();
            }else{
                new Alert(Alert.AlertType.ERROR,"Try Again !").show();
            }



        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
    public void openManageUsers(MouseEvent mouseEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/ManageUsersForm.fxml"));
        load(load);
    }
    private void load(Parent load){
        Scene scene = new Scene(load);
        Stage stage = (Stage)loadContext.getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    @Override
    public void tableLoad() { loadDataToTable(); }


}
