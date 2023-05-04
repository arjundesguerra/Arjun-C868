package Database;

import Models.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

/**
 * This class provides helper methods to interact with the customer table in the database
 */
public class CustomerHelper {

    /**
     * Fetches all customer data from the database, including division and country data.
     * @return an ObservableList of Customer objects containing all customer data
     * @throws SQLException if there is an error retrieving data from the database
     */
    public static ObservableList<Customer> fetchCustomers() throws SQLException {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        PreparedStatement statement = JDBC.getConnection().prepareStatement("SELECT * FROM customers JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID "
                + "JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID ORDER BY Customer_ID ");

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int customerID = resultSet.getInt("Customer_ID");
            String customerName = resultSet.getString("Customer_Name");
            String customerPhoneNumber = resultSet.getString("Phone");
            String customerAddress = resultSet.getString("Address");
            String customerDivision = resultSet.getString("Division");
            String customerCountry = resultSet.getString("Country");
            String customerPostalCode = resultSet.getString("Postal_Code");

            Customer customer = new Customer(customerID, customerName, customerPhoneNumber, customerAddress, customerDivision, customerCountry, customerPostalCode);
            customerList.add(customer);
        }

        return customerList;
    }

    /**
     * Creates a new customer in the database.
     * @param customerID the ID for the new customer
     * @param customerName the name of the new customer
     * @param customerAddress the address of the new customer
     * @param customerPostalCode the postal code of the new customer
     * @param customerPhoneNumber the phone number of the new customer
     * @param customerDivisionID the ID of the division to which the new customer belongs
     * @throws SQLException if there is an error inserting data into the database
     */
    public static void createCustomer(int customerID, String customerName, String customerAddress, String customerPostalCode,
                                      String customerPhoneNumber, int customerDivisionID) throws SQLException {

        PreparedStatement statement = JDBC.getConnection().prepareStatement("INSERT INTO customers VALUES(?, ?, ?, ?, ?, CURRENT_TIMESTAMP, 'user', CURRENT_TIMESTAMP , 'user', ?);");
        statement.setInt(1, customerID);
        statement.setString(2, customerName);
        statement.setString(3, customerAddress);
        statement.setString(4, customerPostalCode);
        statement.setString(5, customerPhoneNumber);
        statement.setInt(6, customerDivisionID);

        statement.execute();

    }


    /**
     * Updates an existing customer in the database.
     * @param customerID the ID of the customer to update
     * @param customerName the updated name of the customer
     * @param customerAddress the updated address of the customer
     * @param customerPostalCode the updated postal code of the customer
     * @param customerPhoneNumber the updated phone number of the customer
     * @param customerDivisionID the updated ID of the division to which the customer belongs
     * @throws SQLException if there is an error updating data in the database
     */
    public static void editCustomer(int customerID, String customerName, String customerAddress, String customerPostalCode,
                                    String customerPhoneNumber, int customerDivisionID) throws SQLException {

        PreparedStatement statement = JDBC.getConnection().prepareStatement("UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ?, Last_Update = CURRENT_TIMESTAMP WHERE Customer_ID = ?;");
        statement.setString(1, customerName);
        statement.setString(2, customerAddress);
        statement.setString(3, customerPostalCode);
        statement.setString(4, customerPhoneNumber);
        statement.setInt(5, customerDivisionID);
        statement.setInt(6, customerID);
        statement.execute();

    }

    /**
     * Deletes a customer from the database.
     * @param customerID the ID of the customer to delete
     * @throws SQLException if there is an error deleting data from the database
     */
    public static void deleteCustomer(int customerID) throws SQLException {
        String sqlDC = "DELETE from customers WHERE Customer_ID = ?";
        try (PreparedStatement psDC = JDBC.getConnection().prepareStatement(sqlDC)) {
            psDC.setInt(1, customerID);
            psDC.execute();
        }
    }

    /**
     * Retrieves the highest ID currently in use for customers in the database.
     * @return the highest ID currently in use for customers in the database
     * @throws SQLException if there is an error retrieving data from the database
     */
    public static int maxID() throws SQLException {
        int customerID = 0;
        PreparedStatement statement = JDBC.getConnection().prepareStatement("SELECT MAX(Customer_ID) FROM customers");
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            customerID = resultSet.getInt(1) + 1;
        }
        return customerID;
    }

    /**
     * Retrieves the ID of a customer with a given name from the database.
     * @param customerName the name of the customer
     * @return the ID of the customer with the given name
     * @throws SQLException if there is an error retrieving data from the database.
     */
    public static int getCustomerIDByName(String customerName) throws SQLException {
        PreparedStatement statement = JDBC.getConnection().prepareStatement("SELECT Customer_ID FROM customers WHERE Customer_Name = ?");
        statement.setString(1, customerName);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("Customer_ID");
        } else {
            throw new SQLException(customerName + " not found");
        }
    }

    /**
     * Retrieves the name of a customer with a given ID from the database.
     * @param customerID the ID of the customer
     * @return the name of the customer with the given ID
     * @throws SQLException if there is an error retrieving data from the database.
     */
    public static String getCustomerNameByID(int customerID) throws SQLException {
        PreparedStatement statement = JDBC.getConnection().prepareStatement("SELECT Customer_Name FROM customers WHERE Customer_ID = ?");
        statement.setInt(1, customerID);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString("Customer_Name");
        } else {
            throw new SQLException(customerID + " not found");
        }
    }

    /**
     * Searches for customers by name in the database.
     * @param customerName the name of the customer to search for
     * @return an ObservableList of Customer objects containing all matching customers
     * @throws SQLException if there is an error retrieving data from the database
     */
    public static ObservableList<Customer> searchCustomers(String customerName) throws SQLException {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        PreparedStatement statement = JDBC.getConnection().prepareStatement("SELECT * FROM customers JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID "
                + "JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID WHERE Customer_Name LIKE ? ORDER BY Customer_ID ");
        statement.setString(1, "%" + customerName + "%");

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int customerID = resultSet.getInt("Customer_ID");
            String customerNameResult = resultSet.getString("Customer_Name");
            String customerPhoneNumber = resultSet.getString("Phone");
            String customerAddress = resultSet.getString("Address");
            String customerDivision = resultSet.getString("Division");
            String customerCountry = resultSet.getString("Country");
            String customerPostalCode = resultSet.getString("Postal_Code");

            Customer customer = new Customer(customerID, customerNameResult, customerPhoneNumber, customerAddress, customerDivision, customerCountry, customerPostalCode);
            customerList.add(customer);
        }

        return customerList;
    }

}
