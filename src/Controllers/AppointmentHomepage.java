package Controllers;

import Database.AppointmentHelper;
import Models.Appointment;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

/**
 * The AppointmentHomepage class is responsible for displaying the list of appointments and their corresponding parts.
 */
public class AppointmentHomepage {

    @FXML private RadioButton allRadioButton;
    @FXML private RadioButton monthRadioButton;
    @FXML private RadioButton weekRadioButton;
    @FXML private TableView appointmentTable;
    @FXML private TableColumn appointmentIDColumn;
    @FXML private TableColumn appointmentTitleColumn;
    @FXML private TableColumn appointmentDescriptionColumn;
    @FXML private TableColumn appointmentLocationColumn;
    @FXML private TableColumn appointmentTypeColumn;
    @FXML private TableColumn appointmentStartColumn;
    @FXML private TableColumn appointmentEndColumn;
    @FXML private TableColumn appointmentCustomerID;
    @FXML private TableColumn appointmentUserID;
    @FXML private TableColumn appointmentContactID;
    @FXML private Button addAppointmentButton;
    private ToggleGroup appointmentToggleGroup;

    /**
     * Initializes the homepage by displaying the data retrieved from the database. Initializes the toggle group and
     * listens for a click on a radio button and displays appointments according to the clicked radio button.
     *
     * @throws SQLException if there is an error retrieving data from the database
     */
    public void initialize() throws SQLException {
        // default
        setAppointmentTableAll();

        appointmentToggleGroup = new ToggleGroup();
        allRadioButton.setToggleGroup(appointmentToggleGroup);
        monthRadioButton.setToggleGroup(appointmentToggleGroup);
        weekRadioButton.setToggleGroup(appointmentToggleGroup);

        allRadioButton.setOnAction(event -> {
            try {
                setAppointmentTableAll();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        monthRadioButton.setOnAction(event -> {
            try {
                setAppointmentTableMonth();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        weekRadioButton.setOnAction(event -> {
            try {
                setAppointmentTableWeek();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }


    /**
     * Loads the Add Appointment view and closes the current stage.
     *
     * @throws IOException If the FXML file for the Add Appointment view cannot be found.
     */
    public void goToAddAppointment() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXMLViews/AddAppointment.fxml"));
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setTitle("Add Appointment");
        newStage.setScene(scene);
        newStage.show();
        Stage currentStage = (Stage) addAppointmentButton.getScene().getWindow();
        currentStage.close();
    }

    /**
     * Loads the Edit Appointment view and closes the current stage.
     *
     * Retrieves the selected appointment from the table view and passes the appointment's data to the
     * Edit Appointment view. If no appointment is selected, an error message is displayed.
     *
     * @throws IOException If the FXML file for the Edit Appointment view cannot be found.
     *
     * @throws SQLException if there is an error retrieving data from the database
     */
    public void goToEditAppointment() throws IOException, SQLException {
        Appointment selectedAppointment = (Appointment) appointmentTable.getSelectionModel().getSelectedItem();

        if (selectedAppointment == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a customer to edit.", ButtonType.OK);
            alert.showAndWait();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLViews/EditAppointment.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setTitle("Edit Appointment");
            newStage.setScene(scene);

            EditAppointment editAppointment = loader.getController();
            editAppointment.setAppointmentData(selectedAppointment.getAppointmentID(), selectedAppointment.getAppointmentTitle(), selectedAppointment.getAppointmentDescription(),
                    selectedAppointment.getAppointmentLocation(),selectedAppointment.getAppointmentType(), selectedAppointment.getStartDateTime(), selectedAppointment.getEndDateTime(), selectedAppointment.getCustomerID(),
                    selectedAppointment.getUserID(), selectedAppointment.getContactID());

            newStage.show();
            Stage currentStage = (Stage) addAppointmentButton.getScene().getWindow();
            currentStage.close();
        }

    }

    /**
     * Deletes the selected appointment from the appointment table and the database.
     *
     * @throws SQLException if there is an error retrieving data from the database
     */
    public void deleteAppointment() throws SQLException {
        Appointment selectedAppointment = (Appointment) appointmentTable.getSelectionModel().getSelectedItem();
        if (selectedAppointment == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select an appointment to delete.", ButtonType.OK);
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this appointment?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                int deletedAppointmentID = selectedAppointment.getAppointmentID();
                String deletedAppointmentType = selectedAppointment.getAppointmentType();
                AppointmentHelper.deleteAppointment(deletedAppointmentID);
                appointmentTable.setItems(AppointmentHelper.fetchAppointments());
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Appointment ID: " + deletedAppointmentID + " with Appointment Type: " + deletedAppointmentType + "\nhas been deleted successfully.", ButtonType.OK);
                successAlert.showAndWait();
            }
        }
    }

    /**
     * Sets the appointment table to display all appointments.
     *
     * @throws SQLException if there is an error retrieving data from the database.
     */
    public void setAppointmentTableAll() throws SQLException {
        appointmentTable.setItems(AppointmentHelper.fetchAppointments());

        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        appointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        appointmentDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        appointmentLocationColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
        appointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        appointmentStartColumn.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        appointmentEndColumn.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        appointmentCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        appointmentUserID.setCellValueFactory(new PropertyValueFactory<>("userID"));
        appointmentContactID.setCellValueFactory(new PropertyValueFactory<>("contactID"));
    }

    /**
     * Sets the appointment table to display appointments by month.
     *
     * @throws SQLException if there is an error retrieving data from the database.
     */
    public void setAppointmentTableMonth() throws SQLException {
        appointmentTable.setItems(AppointmentHelper.fetchAppointmentsByMonth());

        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        appointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        appointmentDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        appointmentLocationColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
        appointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        appointmentStartColumn.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        appointmentEndColumn.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        appointmentCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        appointmentUserID.setCellValueFactory(new PropertyValueFactory<>("userID"));
        appointmentContactID.setCellValueFactory(new PropertyValueFactory<>("contactID"));
    }

    /**
     * Sets the appointment table to display appointments by week.
     *
     * @throws SQLException if there is an error retrieving data from the database.
     */
    public void setAppointmentTableWeek() throws SQLException {
        appointmentTable.setItems(AppointmentHelper.fetchAppointmentsByWeek());

        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        appointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        appointmentDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        appointmentLocationColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
        appointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        appointmentStartColumn.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        appointmentEndColumn.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        appointmentCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        appointmentUserID.setCellValueFactory(new PropertyValueFactory<>("userID"));
        appointmentContactID.setCellValueFactory(new PropertyValueFactory<>("contactID"));
    }

    /**
     * Loads the Homepage view and closes the current stage.
     *
     * @throws IOException If the FXML file for the Homepage view cannot be found.
     */
    public void goToHomepage() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXMLViews/Homepage.fxml"));
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setTitle("Homepage");
        newStage.setScene(scene);
        newStage.show();
        Stage currentStage = (Stage) addAppointmentButton.getScene().getWindow();
        currentStage.close();
    }
}
