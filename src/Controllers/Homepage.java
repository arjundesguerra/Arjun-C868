package Controllers;

import Database.AppointmentHelper;
import Models.Appointment;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Optional;

/**
 * The Homepage class represents the main screen of the application, which allows users to navigate to other pages,
 * view their current location, and receive appointment notifications.
 */
public class Homepage {
    @FXML
    private Text locationText;
    @FXML
    private Button goToCustomersButton;

    /**
     * Initializes the Homepage by checking the user's current location.
     */
    public void initialize() {
        checkLocation();
    }

    /**
     * Checks for any upcoming appointments within the next 15 minutes, and displays a notification showing
     * appointment information if any are found.
     * @throws SQLException if there is an error retrieving data from the database
     */
    public static void appointmentNotification() throws SQLException {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime fifteenMinutesLater = now.plusMinutes(15);

        ObservableList<Appointment> appointments = AppointmentHelper.fetchAppointmentsByTime(now, fifteenMinutesLater);

        if (Locale.getDefault().getLanguage().equals("fr")) {
            if (appointments.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Alerte");
                alert.setHeaderText(null);
                alert.setContentText("Aucun rendez-vous n'est prévu dans les 15 prochaines minutes.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Alerte");
                alert.setHeaderText("Rendez-vous(s) à venir dans les 15 prochaines minutes: \n");
                String content = "";
                for (Appointment appointment : appointments) {
                    content += "ID de Rendez-vous: " + appointment.getAppointmentID() + "\n";
                    content += "Date: " + appointment.getStartDateTime().toLocalDate().toString() + "\n";
                    content += "Heure: " + appointment.getStartDateTime().toLocalTime().toString() + " - " + appointment.getEndDateTime().toLocalTime().toString() + "\n\n";
                }
                alert.setContentText(content);
                alert.showAndWait();
            }
        } else {
            if (appointments.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Alert");
                alert.setHeaderText(null);
                alert.setContentText("There are no appointments scheduled within the next 15 minutes.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Alert");
                alert.setHeaderText("Upcoming appointment(s) in the next 15 minutes: \n");
                String content = "";
                for (Appointment appointment : appointments) {
                    content += "Appointment ID: " + appointment.getAppointmentID() + "\n";
                    content += "Date: " + appointment.getStartDateTime().toLocalDate().toString() + "\n";
                    content += "Time: " + appointment.getStartDateTime().toLocalTime().toString() + " - " + appointment.getEndDateTime().toLocalTime().toString() + "\n\n";
                }
                alert.setContentText(content);
                alert.showAndWait();
            }
        }
    }

    /**
     * Checks the user's current location and displays it on the Homepage.
     */
    public void checkLocation() {
        ZoneId zone = ZoneId.systemDefault();
        locationText.setText("Location: " + zone);
    }

    /**
     * Loads the Customer Homepage view and closes the current stage.
     *
     * @throws IOException If the FXML file for the Customer Homepage view cannot be found.
     */
    public void goToCustomers() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXMLViews/CustomerHomepage.fxml"));
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setTitle("Customers Homepage");
        newStage.setScene(scene);
        newStage.show();
        Stage currentStage = (Stage) goToCustomersButton.getScene().getWindow();
        currentStage.close();
    }

    /**
     * Loads the Appointment Homepage view and closes the current stage.
     *
     * @throws IOException If the FXML file for the Appointment Homepage view cannot be found.
     */
    public void goToAppointments() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXMLViews/AppointmentHomepage.fxml"));
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setTitle("Appointments Homepage");
        newStage.setScene(scene);
        newStage.show();
        Stage currentStage = (Stage) goToCustomersButton.getScene().getWindow();
        currentStage.close();
    }

    /**
     * Loads the Report Homepage view and closes the current stage.
     *
     * @throws IOException If the FXML file for the Report Homepage view cannot be found.
     */
    public void goToReports() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXMLViews/ReportHomepage.fxml"));
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setTitle("Reports Homepage");
        newStage.setScene(scene);
        newStage.show();
        Stage currentStage = (Stage) goToCustomersButton.getScene().getWindow();
        currentStage.close();
    }

    /**
     * Loads the LoginForm view and closes the current stage.
     *
     * @throws IOException If the FXML file for the Appointment Homepage view cannot be found.
     */
    public void goToLogin() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Are you sure you want to log out?");
        alert.setContentText("Click OK to confirm, or Cancel to stay logged in.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/FXMLViews/LoginForm.fxml"));
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setTitle("Login");
            newStage.setScene(scene);
            newStage.show();
            Stage currentStage = (Stage) goToCustomersButton.getScene().getWindow();
            currentStage.close();
        }
    }


}
