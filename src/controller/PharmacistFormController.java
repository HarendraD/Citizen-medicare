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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Order;
import view.tm.MedicineTM;
import view.tm.PharmacistTM;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PharmacistFormController {
    public AnchorPane loadContext;
    public TableView<PharmacistTM> tblMedicine;
    public TableColumn colMedicineId;
    public TableColumn colDescription;
    public TableColumn colStock;
    public TableColumn colQtyOfSell;
    public Label lblSoldMedicineCount;
    public Label lblIncome;
    public JFXTextField txtSearchMedicine;
    public Label lblTime;
    public Label lblDate;
    public Label lblCountOfOrders;
    public TableView<Order> tblOrders;
    public TableColumn colOrderId;
    public TableColumn colPatientName;
    public TableColumn colTotal;
    public TableColumn colTime;


    public void initialize() {
        loadDateAndTime();
        setLblCount();

        colMedicineId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("qtyNeed"));
        colQtyOfSell.setCellValueFactory(new PropertyValueFactory<>("qtyOnStock"));


        loadToTable();

        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colPatientName.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("subTotal"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));


        loadToTable2();
    }
    private void setLblCount(){
        try {

            int soldMedicineCount=PharmacistController.getCountOfMedicineSold();
            int ordersCount=PharmacistController.getOrdersCount();
            int income=OrderController.getIncomeFromMedicine(lblDate.getText());
            if (soldMedicineCount<=9|ordersCount<=9|income<=9){
                lblSoldMedicineCount.setText("00"+String.valueOf(soldMedicineCount));
                lblCountOfOrders.setText("00"+String.valueOf(ordersCount));
                lblIncome.setText("00"+String.valueOf(income));
            }else if (soldMedicineCount<=99|ordersCount<=99|income<=99){
                lblSoldMedicineCount.setText("0"+String.valueOf(soldMedicineCount));
                lblCountOfOrders.setText("0"+String.valueOf(ordersCount));
                lblIncome.setText("0"+String.valueOf(income));
            }else {
                lblSoldMedicineCount.setText(String.valueOf(soldMedicineCount));
                lblCountOfOrders.setText(String.valueOf(ordersCount));
                lblIncome.setText(String.valueOf(income));
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

    private void loadToTable(){
        try {
            List<PharmacistTM> allMedicines=PharmacistController.getMedicine();
            ObservableList<PharmacistTM> medicineList= FXCollections.observableArrayList();
            allMedicines.forEach(e -> {
                medicineList.add(
                        new PharmacistTM(e.getId(), e.getDescription(), e.getQtyOnStock(),e.getQtyNeed()));
            });
            tblMedicine.setItems(medicineList);

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void loadToTable2(){
        try {
            List<Order> allOrders=OrderController.getOrders();
            ObservableList<Order> orders= FXCollections.observableArrayList();
            allOrders.forEach(e -> {
                orders.add(
                        new Order(e.getOrderId(), e.getPatientName(), e.getSubTotal(),e.getTime()));
            });
            tblOrders.setItems(orders);

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void openPharmacistForm(MouseEvent mouseEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/PharmacistForm.fxml"));
        load(load);
    }

    public void openSellMedicineForm(MouseEvent mouseEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/SellForm.fxml"));
        load(load);
    }

    public void logOutOnAction(MouseEvent mouseEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/MainWindowForm.fxml"));
        load(load);
    }

    public void searchMedicine(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        List<PharmacistTM> medicine = PharmacistController.searchMedicine(txtSearchMedicine.getText());
        setMedicineToTable((ArrayList<PharmacistTM>) medicine);
    }

    private void setMedicineToTable(ArrayList<PharmacistTM> medicine) {

        ObservableList<PharmacistTM> medicineList= FXCollections.observableArrayList();
        medicine.forEach(e -> {
            medicineList.add(
                    new PharmacistTM(e.getId(), e.getDescription(), e.getQtyOnStock(), e.getQtyNeed()));
        });
        tblMedicine.setItems(medicineList);

    }
    private void load(Parent load){
        Scene scene = new Scene(load);
        Stage stage = (Stage) loadContext.getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }
}
