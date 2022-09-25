package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.Medicine;
import model.Nurse;
import model.SuppliedDetails;
import util.LoadToTableEvent;
import util.ValidationUtil;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class UpdateMedicineFormController {
    public JFXTextField txtSize;
    public JFXTextField txtType;
    public JFXTextField txtDescription;
    public JFXTextField txtId;
    public JFXTextField txtPrice;
    public JFXComboBox <String>cmbSupplierId;
    public JFXTextField txtQty;
    public JFXButton btnUpdateMedicine;
    public JFXTextField txtSupplierId;
    public JFXTextField txtSupplierName;

    private LoadToTableEvent event;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern idPattern = Pattern.compile("^(M)[0-9]{3,4}$");
    Pattern descriptionPattern = Pattern.compile("^([A-z ]{3,20})|([0-9]{0,})$");
    Pattern typePattern = Pattern.compile("^[A-z]{1,10}$");
    Pattern sizePattern = Pattern.compile("^[0-9]{2,4}$");
    Pattern qtyPattern = Pattern.compile("^[0-9]{2,4}$");
    Pattern pricePattern = Pattern.compile("^([0-9]{2,4})+[.]+([0-9]{2})$");


    public void initialize() {
        btnUpdateMedicine.setDisable(true);
        storeValidations();
    }
    public void checkMedicine(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map,btnUpdateMedicine);

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

    public void loadMedicineDetails(ActionEvent actionEvent) {
        String medicineId=txtId.getText();

        try {
            Medicine medicine = new MedicineController().getMedicine(medicineId);
            if (medicine==null){
                new Alert(Alert.AlertType.ERROR,"Wrong Input !").show();
            }else{
                txtId.setText(medicine.getId());
                txtDescription.setText(medicine.getDescription());
                txtType.setText(medicine.getType());
                txtSize.setText(String.valueOf(medicine.getSize()));
                txtSupplierId.setText(medicine.getSupplierId());
                txtSupplierName.setText(medicine.getSupllierName());
                txtQty.setText(String.valueOf(medicine.getQty()));
                txtPrice.setText(String.valueOf(medicine.getPrice()));

            }


        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    public void updateMedicineDetails(MouseEvent mouseEvent) {
        Medicine medicine =new Medicine(
                txtId.getText(),
                txtDescription.getText(),
                txtType.getText(),
                Integer.parseInt(txtSize.getText()),
                Double.parseDouble(txtPrice.getText()),
                Integer.parseInt(txtQty.getText())
                );
        SuppliedDetails suppliedDetails= new SuppliedDetails(txtId.getText(),txtSupplierId.getText(),Integer.parseInt(txtQty.getText()));
        boolean isUpdated=MedicineController.updateMedicine(medicine,suppliedDetails);
        if (isUpdated){
            new Alert(Alert.AlertType.CONFIRMATION,"UPDATED !").show();
            txtId.clear();
            txtDescription.clear();
            txtType.clear();
            txtSize.clear();
            txtSupplierId.clear();
            txtSupplierName.clear();
            txtQty.clear();
            txtPrice.clear();
            event.tableLoad();
        }else{
            new Alert(Alert.AlertType.ERROR,"Try Again  !").show();
        }

    }

    public void setTable(LoadToTableEvent event){
        this.event=event;
    }


}
