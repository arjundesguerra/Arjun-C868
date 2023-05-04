package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class SearchAppointment {

    @FXML
    private Button backButton;

    public void closeWindow() throws IOException {
        Stage currentStage = (Stage) backButton.getScene().getWindow();
        currentStage.close();
    }

}
