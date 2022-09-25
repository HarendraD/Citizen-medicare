package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.Doctor;
import model.Nurse;
import util.LoadToTableEvent;
import util.ValidationUtil;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class UpdateNurseFormController {
    public JFXTextField txtId;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtContactNumber;
    public JFXButton btnUpdateNurse;

    private LoadToTableEvent event;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern idPattern = Pattern.compile("^(N)[0-9]{3,4}$");
    Pattern namePattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern addressPattern = Pattern.compile("^[A-z0-9/ ]{6,30}$");
    Pattern contactNumberPattern = Pattern.compile("^[0-9]{3}+(-)[0-9]{7}$");

    public void initialize() {
        btnUpdateNurse.setDisable(true);
        storeValidations();
    }
    public void checkNurse(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map,btnUpdateNurse);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            }
        }
    }
    private void storeValidations() {
        map.put(txtId, idPattern);
        map.put(txtName, namePattern);
        map.put(txtAddress, addressPattern);
        map.put(txtContactNumber, contactNumberPattern);
    }

    public void loadNurseDetails(ActionEvent actionEvent) {
        String nurseId=txtId.getText();

        try {
            Nurse nurse = new NurseController().getNurse(nurseId);
            if (nurse==null){
                new Alert(Alert.AlertType.ERROR,"Wrong Input !").show();
            }else{
                txtId.setText(nurse.getId());
                txtName.setText(nurse.getName());
                txtAddress.setText(nurse.getAddress());
                txtContactNumber.setText(nurse.getContactNumber());
            }


        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateNurseDetails(MouseEvent mouseEvent) {
        try {
            boolean isUpdated=NurseController.updateNurse(new Nurse(
                    txtId.getText(),
                    txtName.getText(),
                    txtAddress.getText(),
                    txtContactNumber.getText()
            ));
             if (isUpdated){
                 new Alert(Alert.AlertType.CONFIRMATION,"UPDATED !").show();
                 event.tableLoad();
                 txtId.clear();
                 txtName.clear();
                 txtAddress.clear();
                 txtContactNumber.clear();
             }else{
                 new Alert(Alert.AlertType.ERROR,"Try Again  !").show();
             }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
    public void setTable(LoadToTableEvent event){
        this.event = event;
    }

}
