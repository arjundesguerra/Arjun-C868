package Controllers;
import Database.AppointmentHelper;
import Database.CustomerHelper;
import Models.Customer;
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
 * The CustomerHomepage class is responsible for displaying the list of customers and their corresponding parts.
 */
public class CustomerHomepage {
    @FXML private TextField customerSearch;
    @FXML private Button addCustomerButton;
    @FXML private TableView customerTable;
    @FXML private TableColumn customerIdColumn;
    @FXML private TableColumn customerNameColumn;
    @FXML private TableColumn customerNumberColumn;
    @FXML private TableColumn customerAddressColumn;
    @FXML private TableColumn customerDivisionColumn;
    @FXML private TableColumn customerCountryColumn;
    @FXML private TableColumn customerPostalCodeColumn;

    /**
     * Initializes the Customer Homepage view by displaying the data retrieved from the database using the CustomerHelper class.
     *
     * @throws SQLException if there is an error retrieving data from the database.
     */
    public void initialize() throws SQLException {
        customerTable.setItems(CustomerHelper.fetchCustomers());

        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerNumberColumn.setCellValueFactory(new PropertyValueFactory<>("customerPhoneNumber"));
        customerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        customerDivisionColumn.setCellValueFactory(new PropertyValueFactory<>("customerDivision"));
        customerCountryColumn.setCellValueFactory(new PropertyValueFactory<>("customerCountry"));
        customerPostalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));

    }

    /**
     * Loads the Add Customer view and closes the current stage.
     *
     * @throws IOException If the FXML file for the Add Customer view cannot be found.
     */
    public void goToAddCustomer() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXMLViews/AddCustomer.fxml"));
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setTitle("Add Customer");
        newStage.setScene(scene);
        newStage.show();
        Stage currentStage = (Stage) addCustomerButton.getScene().getWindow();
        currentStage.close();
    }


    /**
     * Loads the Edit Customer view and closes the current stage.
     *
     * Retrieves the selected customer from the table view and passes the customer's data to the
     * Edit Customer view. If no customer is selected, an error message is displayed.
     *
     * @throws IOException If the FXML file for the Edit Customer view cannot be found.
     *
     */
    public void goToEditCustomer() throws IOException {
        Customer selectedCustomer = (Customer) customerTable.getSelectionModel().getSelectedItem();

        if (selectedCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a customer to edit.", ButtonType.OK);
            alert.showAndWait();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLViews/EditCustomer.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setTitle("Edit Customer");
            newStage.setScene(scene);

            EditCustomer editCustomer = loader.getController();
            editCustomer.setCustomerData(selectedCustomer.getCustomerID(), selectedCustomer.getCustomerName(), selectedCustomer.getCustomerPhoneNumber(),
                    selectedCustomer.getCustomerAddress(), selectedCustomer.getCustomerPostalCode(), selectedCustomer.getCustomerCountry(), selectedCustomer.getCustomerDivision());

            newStage.show();
            Stage currentStage = (Stage) addCustomerButton.getScene().getWindow();
            currentStage.close();
        }
    }

    /**
     * Deletes the selected customer from the customer table and the database.
     *
     * @throws SQLException if there is an error retrieving data from the database
     */
    public void deleteCustomer() throws SQLException {
        Customer selectedCustomer = (Customer) customerTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a customer to delete.", ButtonType.OK);
            alert.showAndWait();
        } else {
            boolean hasAppointments = AppointmentHelper.customerHasAppointments(selectedCustomer.getCustomerID());
            if (hasAppointments) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Customer cannot be deleted because there are appointments associated with this customer.", ButtonType.OK);
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this customer?", ButtonType.YES, ButtonType.NO);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES) {
                    CustomerHelper.deleteCustomer(selectedCustomer.getCustomerID());
                    customerTable.setItems(CustomerHelper.fetchCustomers());
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Customer deleted successfully.", ButtonType.OK);
                    successAlert.showAndWait();
                }
            }
        }
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
        Stage currentStage = (Stage) addCustomerButton.getScene().getWindow();
        currentStage.close();
    }

}
