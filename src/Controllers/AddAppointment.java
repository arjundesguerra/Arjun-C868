package Controllers;

import Database.AppointmentHelper;
import Database.ContactHelper;
import Database.CustomerHelper;
import Database.UserHelper;
import Models.Contact;
import Models.Customer;
import Models.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
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
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * The AddAppointment class manages the functionality for adding a new appointment to the database.
 */
public class AddAppointment {

    @FXML
    private RadioButton salesRadioButton;
    @FXML
    private RadioButton serviceRadioButton;
    private ToggleGroup appointmentTypeGroup;
    @FXML
    private ComboBox customerComboBox;
    @FXML
    private ComboBox userComboBox;
    @FXML
    private ComboBox contactComboBox;
    @FXML
    private TextField titleTextField;
    @FXML
    private TextField descriptionTextField;
    @FXML
    private TextField locationTextField;
    @FXML
    private TextField typeTextField;
    @FXML
    private Text tradeInVehicleText;
    @FXML
    private TextField tradeInVehicleTextField;
    @FXML
    private Text financingOptionsText;
    @FXML
    private TextField financingOptionsTextField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox startTimeComboBox;
    @FXML
    private ComboBox endTimeComboBox;
    @FXML
    private Button submitButton;

    /**
     * Initializes the form by setting focus on the submit button, setting up
     * the combo boxes, and setting up the time combo boxes.
     *
     * @throws SQLException if there is an error retrieving data from the database
     */
    public void initialize() throws SQLException {
        submitButton.setFocusTraversable(true);
        Platform.runLater(() -> submitButton.requestFocus());

        setComboBoxes();
        setTimeComboBoxes();

        appointmentTypeGroup = new ToggleGroup();
        salesRadioButton.setToggleGroup(appointmentTypeGroup);
        serviceRadioButton.setToggleGroup(appointmentTypeGroup);

        salesRadioButton.setSelected(true);
    }

    /**
     * Fetches data from the database and populates the corresponding ComboBoxes
     *
     * @throws SQLException if there is an error retrieving data from the database
     */
    public void setComboBoxes() throws SQLException {
        ObservableList<Customer> customerList = CustomerHelper.fetchCustomers();
        for (Customer customer : customerList) {
            customerComboBox.getItems().add(customer.getCustomerName());
        }
        ObservableList<User> usersList = UserHelper.fetchUsers();
        for (User user : usersList) {
            userComboBox.getItems().add(user.getUsername());
        }
        ObservableList<Contact> contactsList = ContactHelper.fetchContacts();
        for (Contact contact : contactsList) {
            contactComboBox.getItems().add(contact.getContactName());
        }
    }

    /**
     * Sets the start and end time combo boxes with time slots in 30-minute increments from 00:00 to 23:30.
     */
    public void setTimeComboBoxes() {
        LocalTime startTime = LocalTime.of(0, 0);
        LocalTime endTime = LocalTime.of(23, 30);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        List<String> timeList = new ArrayList<>();
        LocalTime currentTime = startTime;
        while (currentTime.isBefore(endTime)) {
            String timeStr = currentTime.format(formatter);
            timeList.add(timeStr);
            currentTime = currentTime.plusMinutes(30);
        }
        // separate 23:30 avoids heap failure
        timeList.add("23:30");

        ObservableList<String> timeObservableList = FXCollections.observableArrayList(timeList);
        startTimeComboBox.setItems(timeObservableList);
        endTimeComboBox.setItems(timeObservableList);
    }

    /**
     * Validates and submits a new appointment to the database.
     * Returns to Appointment Homepage after successful submission.
     *
     * @throws IOException  if there is an error with input/output
     * @throws SQLException if there is an error retrieving data from the database
     */
    public void submit() throws IOException, SQLException {
        // gets Strings for getIDbyName methods in helper classes
        String customerName = (String) customerComboBox.getValue();
        String userName = (String) userComboBox.getValue();
        String contactName = (String) contactComboBox.getValue();

        if (customerComboBox.getValue() == null || userComboBox.getValue() == null || contactComboBox.getValue() == null ||
                titleTextField.getText().isEmpty() || descriptionTextField.getText().isEmpty() || locationTextField.getText().isEmpty() ||
                appointmentTypeGroup.getSelectedToggle() == null || datePicker.getValue() == null || startTimeComboBox.getValue() == null || endTimeComboBox.getValue() == null) {

            // display error message if any fields are empty
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill out all fields.");
            alert.showAndWait();
            return;
        }

        String type = null;
        int appointmentID = AppointmentHelper.maxID();
        int customerID = CustomerHelper.getCustomerIDByName(customerName);
        int userID = UserHelper.getUserIDByName(userName);
        int contactID = ContactHelper.getContactIDByName(contactName);
        String title = titleTextField.getText();
        String description = descriptionTextField.getText();
        String location = locationTextField.getText();
        LocalDate date = datePicker.getValue();
        String startTime = (String) startTimeComboBox.getValue();
        String endTime = (String) endTimeComboBox.getValue();

        if (salesRadioButton.isSelected()) {
            type = "Sales Appointment";
        } else if (serviceRadioButton.isSelected()) {
            type = "Service Appointment";
        }

        // turns time strings to date time objects
        LocalDateTime startDateTime = LocalDateTime.parse(date.toString() + " " + startTime + ":00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime endDateTime = LocalDateTime.parse(date.toString() + " " + endTime + ":00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // checks if end time is after start time
        if (endDateTime.isBefore(startDateTime)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("End time cannot be before start time.");
            alert.showAndWait();
            return;
        }

        // checks if start and end times are equal
        if (endDateTime.isEqual(startDateTime)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("An appointment cannot share start and end times.");
            alert.showAndWait();
            return;
        }

        // check if the selected date is before the current date
        LocalDate currentDate = LocalDate.now();
        if (date.isBefore(currentDate)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Appointments cannot be scheduled for dates before the current date.");
            alert.showAndWait();
            return;
        }

        // checks if the new appointment overlaps with existing appointments with the customer
        if (AppointmentHelper.isAppointmentOverlap(startDateTime, endDateTime, customerID, appointmentID)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("This appointment overlaps with an existing appointment with this customer.");
            alert.showAndWait();
            return;
        }

        // checks if appointment starts before 8AM or ends after 10PM in EST timezone
        ZonedDateTime startInEST = startDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("America/New_York"));
        ZonedDateTime endInEST = endDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("America/New_York"));
        if (startInEST.toLocalTime().isBefore(LocalTime.of(8, 0)) ||
                endInEST.toLocalTime().isAfter(LocalTime.of(22, 0)) ||
                endInEST.toLocalDate().isAfter(startInEST.toLocalDate()) && endInEST.toLocalTime().isBefore(LocalTime.of(8, 0))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Appointments can only be scheduled between 8AM and 10PM EST.");
            alert.showAndWait();
            return;
        }

        AppointmentHelper.createAppointment(appointmentID, title, description, location, type, startDateTime, endDateTime, customerID, userID, contactID);
        goToAppointmentHomepage();

    }

    /**
     * Loads the Appointment Homepage view and closes the current stage.
     *
     * @throws IOException If the FXML file for the Appointment Homepage view cannot be found.
     */
    public void goToAppointmentHomepage() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXMLViews/AppointmentHomepage.fxml"));
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setTitle("Appointments Homepage");
        newStage.setScene(scene);
        newStage.show();
        Stage currentStage = (Stage) titleTextField.getScene().getWindow();
        currentStage.close();
    }


}
