package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
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
import model.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.ValidationUtil;
import view.tm.CartTM;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class SellFormController {
    public AnchorPane loadContext;
    public JFXComboBox<String> cmbPatientId;
    public JFXTextField txtPatientName;
    public JFXComboBox<String> cmbMedicineId;
    public JFXTextField txtDescription;
    public JFXTextField txtQtyOnStock;
    public JFXTextField txtQtyNeed;
    public JFXTextField txtPrice;
    public JFXTextField txtSize;
    public TableView tblBill;
    public TableColumn colMedicineId;
    public TableColumn colDescription;
    public TableColumn colSize;
    public TableColumn colPrice;
    public TableColumn colQty;
    public TableColumn colTotalPrice;
    public String medicineId=null;

    public Label lblSubTotal;
    public Label lblTime;
    public Label lblDate;
    public Label lblOrderId;
    public JFXButton btnAddCart;


    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern namePattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern descriptionPattern = Pattern.compile("^([A-z ]{3,20})|([0-9]{0,})$");
    Pattern sizePattern = Pattern.compile("^[0-9]{1,4}$");
    Pattern qtyOnStockPattern = Pattern.compile("^[0-9]{1,4}$");
    Pattern pricePattern = Pattern.compile("^([0-9]{2,4})+[.]+([0-9]{1,})$");
    Pattern qtyNeedPattern = Pattern.compile("^[0-9]{1,4}$");


    public void initialize() {
        btnAddCart.setDisable(true);
        storeValidations();
        setLblOrderId();
        cmbPatientId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->{
            loadPatientsName(newValue);
        } );
        cmbMedicineId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->{
            loadMedicineDetails(newValue);

        } );

        loadDateAndTime();
        loadCombo();


        colMedicineId.setCellValueFactory(new PropertyValueFactory<>("medicineId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        tblBill.getColumns().setAll(colMedicineId,colDescription,colSize,colPrice,colQty,colTotalPrice);

    }

    private void storeValidations() {
        map.put(txtPatientName, namePattern);
        map.put(txtDescription, descriptionPattern);
        map.put(txtSize, sizePattern);
        map.put(txtQtyOnStock, qtyOnStockPattern);
        map.put(txtPrice, pricePattern);
        map.put(txtQtyNeed, qtyNeedPattern);
    }
    public void checkMedicine(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map,btnAddCart);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            }
        }
    }

    private void setLblOrderId(){
        try {
            lblOrderId.setText(new OrderController().getOrderId());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void loadPatientsName(String newValue){

        try {

            Patient patient = new PharmacistController().getPatient(newValue);
            if (patient==null){

            }else{
                txtPatientName.setText(patient.getName());
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }
    private void loadMedicineDetails(String newValue){

        try {

            Medicine medicine = new PharmacistController().getMedicine(newValue);
            if (medicine==null){

            }else{
                txtDescription.setText(medicine.getDescription());
                txtSize.setText(String.valueOf(medicine.getSize()));
                txtQtyOnStock.setText(String.valueOf(medicine.getQty()));
                txtPrice.setText(String.valueOf(medicine.getPrice()));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }
    private void loadCombo(){
        try {
            List<String> patientIds = new PharmacistController().getPatientIds();
            cmbPatientId.getItems().addAll(patientIds);
            List<String> medicineIds=new PharmacistController().getMedicineIds();
            cmbMedicineId.getItems().addAll(medicineIds);
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



    ObservableList<CartTM> obList= FXCollections.observableArrayList();
    public void addToTable(MouseEvent mouseEvent) {
        String medicineId=cmbMedicineId.getValue();
        String description=txtDescription.getText();
        int size=Integer.parseInt(txtSize.getText());
        double unitPrice=Double.parseDouble(txtPrice.getText());
        int qtyNeed=Integer.parseInt(txtQtyNeed.getText());
        double totalPrice=qtyNeed*unitPrice;
        int qtyOnStock=Integer.parseInt(txtQtyOnStock.getText());

        if (qtyOnStock<qtyNeed){
            new Alert(Alert.AlertType.WARNING,"Invalid QTY").show();
        }else{
            CartTM cartTM=new CartTM(
                    medicineId,
                    description,
                    size,
                    unitPrice,
                    qtyNeed,
                    totalPrice);

            int rowNumber=isExits(cartTM);
            if (rowNumber==-1){
                obList.add(cartTM);
            }else {
                CartTM tempTm=obList.get(rowNumber);
                CartTM newCartTm=new CartTM(
                        tempTm.getMedicineId(),
                        tempTm.getDescription(),
                        tempTm.getSize(),
                        tempTm.getUnitPrice(),
                        tempTm.getQty()+Integer.parseInt(txtQtyNeed.getText()),
                        tempTm.getTotalPrice()+totalPrice
                );
                obList.remove(rowNumber);
                obList.add(newCartTm);

            }
            tblBill.setItems(obList);
            calculateSubTotal();
        }
    }

    private void calculateSubTotal() {
        double subTotal=0;
        for (CartTM cartTm:obList) {
            subTotal+=cartTm.getTotalPrice();
        }
        lblSubTotal.setText(subTotal+" /=");
    }

    private int isExits(CartTM cartTM) {
        for (int i = 0; i < obList.size(); i++) {
            if (cartTM.getMedicineId().equals(obList.get(i).getMedicineId())){
                return i;
            }
        }
        return -1;
    }


    public void placeOrder(MouseEvent mouseEvent) {

        ArrayList<OrderDetails> orderDetails=new ArrayList<>();
        double subTotal=0;
        for (CartTM tempCart:obList) {
            subTotal+=tempCart.getTotalPrice();
            orderDetails.add(new OrderDetails(
                    tempCart.getMedicineId(),
                    tempCart.getDescription(),
                    tempCart.getQty(),
                    tempCart.getTotalPrice()));
        }
        Order order=new Order(lblOrderId.getText(),
                cmbPatientId.getValue(),
                txtPatientName.getText(),
                subTotal,
                lblTime.getText(),
                lblDate.getText(),
                orderDetails
                );
        if (new OrderController().placeOrder(order)){
            new Alert(Alert.AlertType.CONFIRMATION,"ORDER SAVED !").show();
            setReport();
            setLblOrderId();
            tblBill.getItems().clear();
        }else {
            new Alert(Alert.AlertType.WARNING,"TRY AGAIN !").show();
        }

    }

    public void clearSlectedItem(MouseEvent mouseEvent) {
        int cartSelectedRowForRemove=tblBill.getSelectionModel().getSelectedIndex();
        if (cartSelectedRowForRemove==-1){
            new Alert(Alert.AlertType.WARNING, "Please Select a row").show();
        }else{
            obList.remove(cartSelectedRowForRemove);
            calculateSubTotal();
            tblBill.refresh();
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
    private void load(Parent load){
        Scene scene = new Scene(load);
        Stage stage = (Stage) loadContext.getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }


    private void setReport(){
        LinkedHashMap map=new LinkedHashMap();
        map.put("OrderId",lblOrderId.getText());
        try {
            JasperDesign load = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/salesInvoice.jrxml"));
            JasperReport jasperReport = JasperCompileManager.compileReport(load);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
