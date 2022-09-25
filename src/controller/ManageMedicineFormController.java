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
import util.LoadToTableEvent;
import view.tm.DoctorTM;
import view.tm.MedicineTM;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public class ManageMedicineFormController implements LoadToTableEvent {
    public AnchorPane loadContext;
    public TableView<MedicineTM> tblMedicine;
    public TableColumn colID;
    public TableColumn colDescription;
    public TableColumn colType;
    public TableColumn colSize;
    public TableColumn colSupplierId;
    public TableColumn colPrice;
    public TableColumn colQty;
    public Label lblTime;
    public Label lblDate;



    public void initialize() {
        loadDateAndTime();

        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supId"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));

        tblMedicine.getColumns().setAll(colID, colDescription, colType, colSize, colPrice,colSupplierId,colQty);
        loadToTable();
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

    private void loadToTable(){
        try {
            List<MedicineTM> allMedicines=MedicineController.getAllMedicine();
            ObservableList<MedicineTM> medicineList= FXCollections.observableArrayList();
            for (MedicineTM medicine:allMedicines) {
                medicineList.add(new MedicineTM(
                        medicine.getId(),
                        medicine.getDescription(),
                        medicine.getType(),
                        medicine.getSize(),
                        medicine.getPrice(),
                        medicine.getSupId(),
                        medicine.getQty()
                ));
            }
            tblMedicine.getItems().clear();
            tblMedicine.getItems().setAll(medicineList);

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
    public void openHomeForm(MouseEvent mouseEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/HomeWindowForm.fxml"));
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

    public void openManageDoctorForm(MouseEvent mouseEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/ManageDoctorForm.fxml"));
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


    public void openAddMedicineForm(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/AddMedicineForm.fxml"));
        Parent parent = loader.load();
        AddMedicineFormController controller = loader.<AddMedicineFormController>getController();
        controller.setTable(this);
        Scene scene=new Scene(parent);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.show();
    }
    public void openUpdateMedicineForm(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/UpdateMedicineForm.fxml"));
        Parent parent = loader.load();
        UpdateMedicineFormController controller = loader.<UpdateMedicineFormController>getController();
        controller.setTable(this);
        Scene scene=new Scene(parent);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.show();

    }
    public void deleteMedicine(MouseEvent mouseEvent) {

        try {
            String id = tblMedicine.getSelectionModel().getSelectedItem().getId();
            boolean isDeleted = MedicineController.deleteMedicine(id);
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


    public void openManageUsers(MouseEvent mouseEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/ManageUsersForm.fxml"));
        load(load);
    }

    private void load(Parent load){
        Scene scene = new Scene(load);
        Stage stage = (Stage) loadContext.getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }


    @Override
    public void tableLoad() {loadToTable();}


}
