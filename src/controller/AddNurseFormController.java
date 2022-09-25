package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.Nurse;
import util.LoadToTableEvent;
import util.ValidationUtil;
import view.tm.CartTM;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class AddNurseFormController {
    public JFXTextField txtId;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtContactNumber;
    public JFXButton btnSaveNurse;

    private LoadToTableEvent event;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern idPattern = Pattern.compile("^(N)[0-9]{3,4}$");
    Pattern namePattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern addressPattern = Pattern.compile("^[A-z0-9/ ]{6,30}$");
    Pattern contactNumberPattern = Pattern.compile("^[0-9]{3}+(-)[0-9]{7}$");

    public void initialize() {
        btnSaveNurse.setDisable(true);
        storeValidations();
    }
    public void checkNurse(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map,btnSaveNurse);

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


    public void saveNurseDetails(MouseEvent mouseEvent) {
        try {
            if (NurseController.isExits(txtId.getText())){
                new Alert(Alert.AlertType.ERROR,"ID Already Exits !").show();
            }else {
                if (txtId.getText().trim().isEmpty()) {
                    new Alert(Alert.AlertType.ERROR, "Please Enter Nurse Details").show();
                }else {
                    Nurse nurse=new Nurse(txtId.getText(),txtName.getText(),txtAddress.getText(),txtContactNumber.getText());
                    boolean isSaved=NurseController.saveNurse(nurse);
                    if (isSaved){
                        new Alert(Alert.AlertType.CONFIRMATION,"SAVED !").show();
                        event.tableLoad();
                        txtId.clear();
                        txtName.clear();
                        txtAddress.clear();
                        txtContactNumber.clear();

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
