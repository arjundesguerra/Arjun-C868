package Controllers;

import Database.CustomerHelper;
import Database.DivisionHelper;
import Models.Division;
import javafx.application.Platform;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The EditCustomer class manages the functionality for editing an existing customer in the database.
 */
public class EditCustomer {
    @FXML
    private TextField idField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField numberTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private TextField postalCodeTextField;
    @FXML
    private ComboBox countryComboBox;
    @FXML
    private ComboBox<Division> divisionComboBox;
    @FXML private Button submitButton;
    private int customerID;
    private String customerName;
    private String customerNumber;
    private String customerAddress;
    private String customerPostalCode;
    private String customerCountry;
    private String customerDivision;

    /**
     * Initializes the form by setting focus on the submit button.
     *
     * Creates event listeners for the country combo box to detect changes in selection and user interaction. Updates the division combo box with the
     * corresponding divisions when the selected country changes, and resets the division combo box when the user selects the same country again.
     *
     * Lambda expression to define the event handler for the country combo box.
     */
    public void initialize() {
        submitButton.setFocusTraversable(true);
        Platform.runLater(() -> submitButton.requestFocus());

        AtomicBoolean countryChangedByUser = new AtomicBoolean(false);

        countryComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (countryChangedByUser.get()) {
                    divisionComboBox.setValue(null);
                } else {
                    divisionSelector();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                divisionSelector();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        });

        countryComboBox.addEventHandler(EventType.ROOT, event -> {
            if (event.getEventType() == MouseEvent.MOUSE_CLICKED ||
                    event.getEventType() == KeyEvent.KEY_PRESSED) {
                countryChangedByUser.set(true);
            }
        });

    }

    /**
     * Sets the data for a customer object and populates the customer form fields with the given data.
     *
     * @param customerID       the ID of the customer
     * @param customerName     the name of the customer
     * @param customerNumber   the phone number of the customer
     * @param customerAddress  the address of the customer
     * @param customerPostalCode the postal code of the customer
     * @param customerCountry  the country of the customer
     * @param customerDivision the division of the customer
     */
    public void setCustomerData(int customerID, String customerName, String customerNumber, String customerAddress, String customerPostalCode,
                                String customerCountry, String customerDivision) {

        this.customerID = customerID;
        this.customerName = customerName;
        this.customerNumber = customerNumber;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerCountry = customerCountry;
        this.customerDivision = customerDivision;

        idField.setText(Integer.toString(customerID));
        nameTextField.setText(customerName);
        numberTextField.setText(customerNumber);
        addressTextField.setText(customerAddress);
        postalCodeTextField.setText(customerPostalCode);

        Division placeHolder = new Division(1, "placeHolder");
        int divisionID = placeHolder.getDivisionID();
        Division division = new Division(divisionID, customerDivision);

        divisionComboBox.setValue(division);

        if (customerCountry.equals("U.S")) {
            countryComboBox.setValue("United States");
        } else if (customerCountry.equals("UK")) {
            countryComboBox.setValue("United Kingdom");
        } else if (customerCountry.equals("Canada")) {
            countryComboBox.setValue("Canada");
        }

        try {
            divisionSelector();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Fetches the divisions for the country selected in the country combo box and populates the division combo box with
     * the fetched divisions.
     *
     * @throws SQLException if there is an error while fetching the divisions from the database.
     */
    public void divisionSelector() throws SQLException {
        int countryID;
        if (countryComboBox.getValue().equals("United States")) {
            countryID = 1;
            divisionComboBox.setItems(DivisionHelper.fetchDivisions(countryID));
        } else if (countryComboBox.getValue().equals("United Kingdom")) {
            countryID = 2;
            divisionComboBox.setItems(DivisionHelper.fetchDivisions(countryID));
        } else if (countryComboBox.getValue().equals("Canada")) {
            countryID = 3;
            divisionComboBox.setItems(DivisionHelper.fetchDivisions(countryID));
        }

    }

    /**
     * Validates and updates the database with the edited customer.
     * Returns to Customer Homepage after successful submission.
     *
     * @throws IOException  if there is an error with input/output
     * @throws SQLException if there is an error retrieving data from the database
     */
    public void submit() throws IOException, SQLException {
        int customerID = Integer.parseInt(idField.getText());
        String customerName = nameTextField.getText();
        String customerNumber = numberTextField.getText();
        String customerAddress = addressTextField.getText();
        String customerPostalCode = postalCodeTextField.getText();

        if (customerName.isEmpty() || customerNumber.isEmpty() || customerAddress.isEmpty() || customerPostalCode.isEmpty()
                || divisionComboBox.getValue() == null || countryComboBox.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill out all fields.", ButtonType.OK);
            alert.showAndWait();
        } else {
            int customerDivision = divisionComboBox.getValue().getDivisionID();
            CustomerHelper.editCustomer(customerID, customerName, customerAddress, customerPostalCode, customerNumber, customerDivision);
            goToCustomerHomepage();
        }
    }

    /**
     * Loads the Customer Homepage view and closes the current stage.
     *
     * @throws IOException If the FXML file for the Customer Homepage view cannot be found.
     */
    public void goToCustomerHomepage() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXMLViews/CustomerHomepage.fxml"));
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setTitle("Customers Homepage");
        newStage.setScene(scene);
        newStage.show();
        Stage currentStage = (Stage) nameTextField.getScene().getWindow();
        currentStage.close();
    }

}
