package Database;

import Models.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class provides helper methods to interact with the contacts table in the database.
 */
public class ContactHelper {

    /**
     * Retrieves all contacts in the database.
     *
     * @return An observable list of all contacts in the database.
     * @throws SQLException if there is an error retrieving data from the database
     */
    public static ObservableList<Contact> fetchContacts() throws SQLException {
        ObservableList<Contact> contactsList = FXCollections.observableArrayList();

        PreparedStatement statement = JDBC.getConnection().prepareStatement("SELECT * FROM contacts ");

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int contactID = resultSet.getInt("Contact_ID");
            String contactName = resultSet.getString("Contact_Name");
            String email = resultSet.getString("Email");

            Contact contact = new Contact(contactID, contactName, email);
            contactsList.add(contact);
        }

        return contactsList;
    }

    /**
     * Retrieves the contact ID for the given contact name.
     *
     * @param contactName The name of the contact to retrieve the ID for.
     * @return The contact ID for the given contact name.
     * @throws SQLException if there is an error retrieving data from the database
     */
    public static int getContactIDByName(String contactName) throws SQLException {
        PreparedStatement statement = JDBC.getConnection().prepareStatement("SELECT Contact_ID FROM contacts WHERE Contact_Name = ?");
        statement.setString(1, contactName);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("Contact_ID");
        } else {
            throw new SQLException(contactName + " not found");
        }
    }

    /**
     * Retrieves the contact name for the given contact ID.
     *
     * @param contactID The ID of the contact to retrieve the name for.
     * @return The contact name for the given contact ID.
     * @throws SQLException if there is an error retrieving data from the database
     */
    public static String getContactNameByID(int contactID) throws SQLException {
        PreparedStatement statement = JDBC.getConnection().prepareStatement("SELECT Contact_Name FROM contacts WHERE Contact_ID = ?");
        statement.setInt(1, contactID);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString("Contact_Name");
        } else {
            throw new SQLException(contactID + " not found");
        }
    }

}
