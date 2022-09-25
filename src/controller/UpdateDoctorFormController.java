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
import util.LoadToTableEvent;
import util.ValidationUtil;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class UpdateDoctorFormController {
    public JFXTextField txtId;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtContactNumber;
    public JFXTextField txtSpeciality;
    public JFXButton btnUpdateDoctor;

    private LoadToTableEvent event;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern idPattern = Pattern.compile("^(D)[0-9]{3,4}$");
    Pattern namePattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern addressPattern = Pattern.compile("^[A-z0-9/ ]{6,30}$");
    Pattern contactNumberPattern = Pattern.compile("^[0-9]{3}+(-)[0-9]{7}$");
    Pattern typePattern = Pattern.compile("^[A-z ]{3,20}$");


    public void initialize() {
        btnUpdateDoctor.setDisable(true);
        storeValidations();

    }
    public void checkDoctor(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map,btnUpdateDoctor);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            }
        }
    }
    public void loadDoctorDetails(ActionEvent actionEvent) {
        String doctorId = txtId.getText();


        try {
            Doctor d1 = new DoctorController().getDoctor(doctorId);
            if (d1==null) {
                new Alert(Alert.AlertType.WARNING, "Wrong Id !").show();
            } else {
                txtId.setText(d1.getId());
                txtName.setText(d1.getName());
                txtAddress.setText(d1.getAddress());
                txtContactNumber.setText(d1.getContactNumber());
                txtSpeciality.setText(d1.getType());
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }
    private void storeValidations() {
        map.put(txtId, idPattern);
        map.put(txtName, namePattern);
        map.put(txtAddress, addressPattern);
        map.put(txtContactNumber, contactNumberPattern);
        map.put(txtSpeciality, typePattern);
    }

    public void updateDoctorDetails(MouseEvent mouseEvent) {
        try {
            boolean isUpdated=DoctorController.updateDoctor(new Doctor(
                    txtId.getText(),
                    txtName.getText(),
                    txtAddress.getText(),
                    txtContactNumber.getText(),
                    txtSpeciality.getText()
            ));
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION,"UPDATED !").show();
                event.tableLoad();
            }else{
                new Alert(Alert.AlertType.ERROR,"Try Again !").show();
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
    public void setTable(LoadToTableEvent event){
        this.event = event;
    }


}
