package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.Doctor;
import util.LoadToTableEvent;
import util.ValidationUtil;

import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddDoctorFormController implements Initializable {
    public JFXTextField txtId;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtContactNumber;
    public JFXTextField txtSpeciality;
    public JFXButton btnSaveDoctor;

    private LoadToTableEvent event;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern idPattern = Pattern.compile("^(D)[0-9]{3,4}$");
    Pattern namePattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern addressPattern = Pattern.compile("^[A-z0-9/ ]{6,30}$");
    Pattern contactNumberPattern = Pattern.compile("^[0-9]{3}+(-)[0-9]{7}$");
    Pattern typePattern = Pattern.compile("^[A-z ]{3,20}$");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnSaveDoctor.setDisable(true);
        storeValidations();

    }
    public void checkTextField(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map,btnSaveDoctor);

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
        map.put(txtSpeciality, typePattern);
    }

    public void saveDoctorDetails(MouseEvent mouseEvent) {
        String id=txtId.getText();
        try {
            boolean isExits=DoctorController.isExits(id);
            if (isExits){
                new Alert(Alert.AlertType.ERROR,"ID Already Exits !").show();
            }else{
                if (txtId.getText().trim().isEmpty()){
                    new Alert(Alert.AlertType.ERROR,"Please Enter Doctor Details").show();
                }else {
                    Doctor doctor= new Doctor(txtId.getText(),txtName.getText(),txtAddress.getText(),txtContactNumber.getText(),txtSpeciality.getText());
                    boolean saved=DoctorController.saveDoctor(doctor);
                    if (saved){
                        new Alert(Alert.AlertType.CONFIRMATION, "SAVED!").show();
                        event.tableLoad();
                        txtId.clear();
                        txtName.clear();
                        txtAddress.clear();
                        txtContactNumber.clear();
                        txtSpeciality.clear();

                    }else{
                        new Alert(Alert.AlertType.WARNING,"Try Again").show();
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
