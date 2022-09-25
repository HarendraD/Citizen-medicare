package util;

import com.jfoenix.controls.JFXButton;
import javafx.scene.control.TextField;

import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ValidationUtil {

    public static Object validate(LinkedHashMap<TextField,Pattern> map, JFXButton btn) {
        btn.setDisable(true);
        for (TextField textFieldKey : map.keySet()) {
            Pattern patternValue = map.get(textFieldKey);
            if (!patternValue.matcher(textFieldKey.getText()).matches()) {
                if (!textFieldKey.getText().isEmpty()){
                    textFieldKey.setStyle("-fx-border-color: red; -fx-background-radius: 10; -fx-border-radius: 10; -fx-border-width: 2;");
                }
                return textFieldKey;
            }
            textFieldKey.setStyle("-fx-border-color: green; -fx-background-radius: 10; -fx-border-radius: 10; -fx-border-width: 2;");
        }
        btn.setDisable(false);
        return true;
    }
}
