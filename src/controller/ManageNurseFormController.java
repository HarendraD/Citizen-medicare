package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import model.Nurse;
import util.LoadToTableEvent;
import view.tm.DoctorTM;
import view.tm.NurseTM;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;


public class ManageNurseFormController implements LoadToTableEvent {

    public Label lblTime;
    public Label lblDate;
    public TableView<NurseTM> tblNurses;
    public TableColumn colNurseID;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colContactNumber;
    public AnchorPane loadContext;

    public void initialize() {
        loadDateAndTime();
        colNurseID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContactNumber.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));



        tblNurses.getColumns().setAll(colNurseID, colName, colAddress, colContactNumber);

        loadDataToTable();
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

    private void loadDataToTable() {
        try {
            List<Nurse> nurseList=NurseController.getAllNurseDetails();
            ObservableList<NurseTM> nurseTMS= FXCollections.observableArrayList();
            for (Nurse nurse:nurseList) {
                nurseTMS.add(new NurseTM(
                        nurse.getId(),
                        nurse.getName(),
                        nurse.getAddress(),
                        nurse.getContactNumber()

                ));
            }
            tblNurses.getItems().setAll(nurseTMS);


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

    public void openAddNurseForm(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/AddNurseForm.fxml"));
        Parent parent = loader.load();
        AddNurseFormController controller = loader.<AddNurseFormController>getController();
        controller.setTable(this);
        Scene scene=new Scene(parent);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void openUpdateNurseForm(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/UpdateNurseForm.fxml"));
        Parent parent = loader.load();
        UpdateNurseFormController controller = loader.<UpdateNurseFormController>getController();
        controller.setTable(this);
        Scene scene=new Scene(parent);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void deleteNurse(MouseEvent mouseEvent) {

        try {
            String id = tblNurses.getSelectionModel().getSelectedItem().getId();
            boolean isDeleted = NurseController.deleteNurse(id);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"DELETED !").show();
                loadDataToTable();
            }else {
                new Alert(Alert.AlertType.ERROR,"ERROR !").show();
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void load(Parent load) {
        Scene scene = new Scene(load);
        Stage stage = (Stage) loadContext.getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }
    public void openManageUsers(MouseEvent mouseEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/ManageUsersForm.fxml"));
        load(load);
    }
    @Override
    public void tableLoad() {
        loadDataToTable();
    }


}
