package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.Medicine;
import model.SuppliedDetails;
import util.LoadToTableEvent;
import util.ValidationUtil;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddMedicineFormController{
    public JFXTextField txtId;
    public JFXTextField txtDescription;
    public JFXTextField txtType;
    public JFXTextField txtSize;
    public JFXTextField txtPrice;
    public JFXTextField txtQty;
    public JFXComboBox <String>cmbSupplierId;
    public JFXButton btnSaveMedicine;

    private LoadToTableEvent event;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern idPattern = Pattern.compile("^(M)[0-9]{3,4}$");
    Pattern descriptionPattern = Pattern.compile("^([A-z ]{3,20})|([0-9]{0,})$");
    Pattern typePattern = Pattern.compile("^[A-z]{1,10}$");
    Pattern sizePattern = Pattern.compile("^[0-9]{2,4}$");
    Pattern pricePattern = Pattern.compile("^([0-9]{2,4})+[.]+([0-9]{2})$");
    Pattern qtyPattern = Pattern.compile("^[0-9]{2,4}$");

    public void initialize() {
        btnSaveMedicine.setDisable(true);
        storeValidations();
        try {
            List<String> supIds=new MedicineController().getSupplierIds();
            cmbSupplierId.getItems().addAll(supIds);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }
    public void checkMedicine(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map,btnSaveMedicine);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            }
        }

    }
    private void storeValidations() {
        map.put(txtId, idPattern);
        map.put(txtDescription, descriptionPattern);
        map.put(txtType, typePattern);
        map.put(txtSize, sizePattern);
        map.put(txtQty, qtyPattern);
        map.put(txtPrice, pricePattern);

    }
    public void saveMedicineDetails(MouseEvent mouseEvent) {
        String id=txtId.getText();
        try {
            boolean isExits = MedicineController.isExits(id);
            if (isExits){
                new Alert(Alert.AlertType.ERROR,"ID Already Exits !").show();
            }else {
                if (txtId.getText().trim().isEmpty()) {
                    new Alert(Alert.AlertType.ERROR, "Please Enter Medicine Details").show();
                }else {
                    Medicine medicine =new Medicine(
                            txtId.getText(),
                            txtDescription.getText(),
                            txtType.getText(),
                            Integer.parseInt(txtSize.getText()),
                            Double.parseDouble(txtPrice.getText()),
                            Integer.parseInt(txtQty.getText())
                    );
                    SuppliedDetails suppliedDetails= new SuppliedDetails(txtId.getText(),cmbSupplierId.getValue(),Integer.parseInt(txtQty.getText()));

                    boolean isSaved=new MedicineController().saveMedicine(medicine,suppliedDetails);
                    if (isSaved){
                        new Alert(Alert.AlertType.CONFIRMATION,"SAVED !").show();
                        txtId.clear();
                        txtDescription.clear();
                        txtType.clear();
                        txtSize.clear();
                        txtQty.clear();
                        txtPrice.clear();
                        event.tableLoad();
                    }else {
                        new Alert(Alert.AlertType.ERROR,"Try Again !").show();
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }


    }

    public void setTable(LoadToTableEvent event){
        this.event=event;
    }


}
