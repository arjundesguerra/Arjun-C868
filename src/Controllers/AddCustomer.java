package Controllers;

import Database.CustomerHelper;
import Database.DivisionHelper;
import Models.Division;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

/**
 * The AddCustomer class manages the functionality for adding a new customer to the database.
 */
public class AddCustomer {
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
    @FXML
    private Button submitButton;


    /**
     * Initializes the form by setting focus on the submit button.
     *
     * Registers an event handler for the country combo box which invokes the divisionSelector method.
     * Lambda expression to define the event handler for the country combo box.
     *
     * @throws SQLException if there is an error retrieving data from the database
     */
    public void initialize() {
        submitButton.setFocusTraversable(true);
        Platform.runLater(() -> submitButton.requestFocus());

        countryComboBox.setOnAction(event -> {
            try {
                divisionSelector();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

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
     * Validates and submits a new customer to the database.
     * Returns to Customer Homepage after successful submission.
     *
     * @throws IOException  if there is an error with input/output
     * @throws SQLException if there is an error retrieving data from the database
     */
    public void submit() throws IOException, SQLException {
        int customerID = CustomerHelper.maxID();
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
            CustomerHelper.createCustomer(customerID, customerName, customerAddress, customerPostalCode, customerNumber, customerDivision);
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
