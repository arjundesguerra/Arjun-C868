package Controllers;

import Database.*;
import Models.Contact;
import Models.Customer;
import Models.ServiceAppointment;
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
 * The EditAppointment class manages the functionality for editing an existing appointment in the database.
 */
public class EditAppointment {
    @FXML
    private RadioButton salesRadioButton;
    @FXML
    private RadioButton serviceRadioButton;
    private ToggleGroup appointmentTypeGroup;
    @FXML
    private TextField appointmentIDTextField;
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
    private Text vehicleOrCostText;
    @FXML
    private TextField vehicleOrCostTextField;
    @FXML
    private Text financingOrTypeText;
    @FXML
    private ComboBox financingOrTypeComboBox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox startTimeComboBox;
    @FXML
    private ComboBox endTimeComboBox;
    @FXML
    private Button submitButton;
    private int appointmentID;
    private String appointmentTitle;
    private String appointmentDescription;
    private String appointmentLocation;
    private String vehicleOrCost;
    private String financingOrType;
    private String appointmentType;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private int customerID;
    private int userID;
    private int contactID;

    /**
     * Initializes the form by setting focus on the submit button,
     * setting up the combo boxes, and setting up the time combo boxes.
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

        salesRadioButton.setOnAction(event -> setToSales());
        serviceRadioButton.setOnAction(event -> setToService());

        salesRadioButton.setSelected(true);
        ObservableList<String> options = FXCollections.observableArrayList("Cash", "Lease", "Loan");
        financingOrTypeComboBox.setItems(options);
    }

    /**
     * Sets the data for an appointment object and populates the appointment form fields with the given data.
     *
     * @param appointmentID          The ID of the appointment.
     * @param appointmentTitle       The title of the appointment.
     * @param appointmentDescription The description of the appointment.
     * @param appointmentLocation    The location of the appointment.
     * @param appointmentType        The type of the appointment.
     * @param startDateTime          The start date and time of the appointment.
     * @param endDateTime            The end date and time of the appointment.
     * @param customerID             The ID of the customer associated with the appointment.
     * @param userID                 The ID of the user associated with the appointment.
     * @param contactID              The ID of the contact associated with the appointment.
     * @throws SQLException if there is an error retrieving data from the database
     */

    public void setAppointmentData(int appointmentID, String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType, String vehicleOrCost,
                                   String financingOrType, LocalDateTime startDateTime, LocalDateTime endDateTime, int customerID, int userID, int contactID) throws SQLException {
        this.appointmentType = appointmentType;


        this.appointmentID = appointmentID;
        this.appointmentTitle = appointmentTitle;
        this.appointmentDescription = appointmentDescription;
        this.appointmentLocation = appointmentLocation;
        this.vehicleOrCost = vehicleOrCost;
        this.financingOrType = financingOrType;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;


        appointmentIDTextField.setText(Integer.toString(appointmentID));
        customerComboBox.setValue(CustomerHelper.getCustomerNameByID(customerID));
        userComboBox.setValue(UserHelper.getUserNameByID(userID));
        contactComboBox.setValue(ContactHelper.getContactNameByID(contactID));
        titleTextField.setText(appointmentTitle);
        descriptionTextField.setText(appointmentDescription);
        locationTextField.setText(appointmentLocation);
        vehicleOrCostTextField.setText(vehicleOrCost);
        datePicker.setValue(startDateTime.toLocalDate());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String startTimeString = startDateTime.format(formatter);
        startTimeComboBox.setValue(startTimeString);

        String endTimeString = endDateTime.format(formatter);
        endTimeComboBox.setValue(endTimeString);

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

    public void setToSales() {
        vehicleOrCostText.setText("Vehicle:");
        financingOrTypeText.setText("Financing Option:");
        ObservableList<String> options = FXCollections.observableArrayList("Cash", "Lease", "Loan");
        financingOrTypeComboBox.setItems(options);
        vehicleOrCostTextField.setText("");
    }

    public void setToService() {
        vehicleOrCostText.setText("Service Cost:");
        financingOrTypeText.setText("Service Type:");
        ObservableList<String> options = FXCollections.observableArrayList("Oil Change", "Tire Rotation", "Alignment");
        financingOrTypeComboBox.setItems(options);
        vehicleOrCostTextField.setText("");
    }

    /**
     * Validates and updates the database with the edited appointment.
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

        if (appointmentTypeGroup.getSelectedToggle() == null || customerComboBox.getValue() == null || userComboBox.getValue() == null || contactComboBox.getValue() == null ||
                titleTextField.getText().isEmpty() || descriptionTextField.getText().isEmpty() || locationTextField.getText().isEmpty() ||
                vehicleOrCostTextField.getText() == null || financingOrTypeComboBox.getValue() == null || datePicker.getValue() == null || startTimeComboBox.getValue() == null || endTimeComboBox.getValue() == null) {
            // display error message if any fields are empty
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill out all fields.");
            alert.showAndWait();
            return;
        }

        String vehicleOrCost = vehicleOrCostTextField.getText();
        String financingOrType = (String) financingOrTypeComboBox.getValue();

        String type = ((RadioButton) appointmentTypeGroup.getSelectedToggle()).getText();
        int appointmentID = Integer.parseInt(appointmentIDTextField.getText());
        int customerID = CustomerHelper.getCustomerIDByName(customerName);
        int userID = UserHelper.getUserIDByName(userName);
        int contactID = ContactHelper.getContactIDByName(contactName);
        String title = titleTextField.getText();
        String description = descriptionTextField.getText();
        String location = locationTextField.getText();
        LocalDate date = datePicker.getValue();
        String startTime = (String) startTimeComboBox.getValue();
        String endTime = (String) endTimeComboBox.getValue();

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

        if (salesRadioButton.isSelected()) {
            if (appointmentType.equals("Sales Appointment")) {
                SalesAppointmentHelper.deleteAppointment(appointmentID);
            } else if (appointmentType.equals("Service Appointment")) {
                ServiceAppointmentHelper.deleteAppointment(appointmentID);
            }
            AppointmentHelper.editAppointment(appointmentID, title, description, location, type, startDateTime, endDateTime, customerID, userID, contactID);
            SalesAppointmentHelper.createSalesAppointment(appointmentID, title, description, location, vehicleOrCost, financingOrType, startDateTime, endDateTime, customerID, userID, contactID);

        } else if (serviceRadioButton.isSelected()) {
            try {
                Double.parseDouble(vehicleOrCost);
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please input a valid price.");
                alert.showAndWait();
                return;
            }
            AppointmentHelper.editAppointment(appointmentID, title, description, location, type, startDateTime, endDateTime, customerID, userID, contactID);
            if (appointmentType.equals("Sales Appointment")) {
                SalesAppointmentHelper.deleteAppointment(appointmentID);
            } else if (appointmentType.equals("Service Appointment")) {
                ServiceAppointmentHelper.deleteAppointment(appointmentID);
            }
            AppointmentHelper.editAppointment(appointmentID, title, description, location, type, startDateTime, endDateTime, customerID, userID, contactID);
            ServiceAppointmentHelper.createServiceAppointment(appointmentID, title, description, location, Double.parseDouble(vehicleOrCost), financingOrType, startDateTime, endDateTime, customerID, userID, contactID);
        }
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