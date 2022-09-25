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
import javafx.scene.control.Alert;
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
import model.OrderDetails;
import view.tm.MedicineTM;
import view.tm.OrderDetailsTM;
import view.tm.PharmacistTM;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReportsFormController {
    public AnchorPane homeContext;
    public Label lblTime;
    public Label lblDate;
    public TableView<Order> tblOrders;
    public TableColumn colOrderId;
    public TableColumn colPatientName;
    public TableColumn colSubTotal;
    public TableColumn colTime;
    public TableColumn colDate;
    public TableView tblMedicine;
    public TableColumn colDescription;
    public TableColumn colQty;
    public TableColumn colTotal;
    public Label lblDailyIncome;
    public Label lblMonthlyIncome;
    public Label lblYearlyIncome;
    public JFXTextField txtSearch;
    public TableColumn colMedicineId;
    public JFXButton btnView;


    public void initialize() {

        loadDateAndTime();
        setLblCount();

        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colPatientName.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        colSubTotal.setCellValueFactory(new PropertyValueFactory<>("subTotal"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));


        loadToTable1();

        colMedicineId.setCellValueFactory(new PropertyValueFactory<>("medicineId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qtyNeed"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        tblMedicine.getColumns().setAll(colMedicineId, colDescription, colQty, colTotal);

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

    private void setLblCount(){
        String id=lblDate.getText();
        try {
            int dailyIncome=ReportController.getDailyIncome(id);
            lblDailyIncome.setText(String.valueOf(dailyIncome));

            int monthlyIncome=ReportController.getMonthlyIncome();
            lblMonthlyIncome.setText(String.valueOf(monthlyIncome));

            int yearlyIncome=ReportController.getYearlyIncome();
            lblYearlyIncome.setText(String.valueOf(yearlyIncome));


        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }


    }

    public void checkOrders(KeyEvent keyEvent) {

        try {
            List<Order> orders = ReportController.searchOrder(txtSearch.getText());
            setOrdersToTable((ArrayList<Order>) orders);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }
    private void setOrdersToTable(ArrayList<Order> ordersToTable) {

        ObservableList<Order> orders= FXCollections.observableArrayList();
        ordersToTable.forEach(e -> {
            orders.add(
                    new Order(e.getOrderId(), e.getPatientName(), e.getSubTotal(), e.getTime(), e.getDate()));
        });
        tblOrders.setItems(orders);

    }

    private void loadToTable1(){
        try {
            List<Order> allOrders= new ReportController().getAllOrders();
            ObservableList<Order> orders= FXCollections.observableArrayList();
            allOrders.forEach(e -> {
                orders.add(
                        new Order(e.getOrderId(), e.getPatientName(), e.getSubTotal(),e.getTime(), e.getDate()));
            });
            tblOrders.setItems(orders);

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void viewOrderDetails(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {

             Order order=tblOrders.getSelectionModel().getSelectedItem();
             String orderId=order.getOrderId();
            if (order==null){
                btnView.setDisable(true);
                new Alert(Alert.AlertType.WARNING,"Please Select The Row on Table").show();

            }else {

                loadToTable2(orderId);
            }

    }

    private void loadToTable2(String orderId) throws SQLException, ClassNotFoundException {
        List<OrderDetailsTM> orderDetailsList=ReportController.getOrderDetails(orderId);
        ObservableList<OrderDetailsTM> orders= FXCollections.observableArrayList();
        for (OrderDetailsTM orderDetails:orderDetailsList) {
            orders.add(new OrderDetailsTM(
                    orderDetails.getMedicineId(),
                    orderDetails.getDescription(),
                    orderDetails.getQtyNeed(),
                    orderDetails.getTotalPrice()
            ));
        }
        tblMedicine.getItems().clear();
        tblMedicine.getItems().setAll(orders);
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
