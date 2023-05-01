package Database;

import Models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class provides helper methods to interact with the user table in the database
 */
public class UserHelper {

    /**
     * Fetches all users from the users table in the database.
     *
     * @return an ObservableList of User objects representing all users in the database.
     * @throws SQLException if there is an error retrieving data from the database
     */
    public static ObservableList<User> fetchUsers() throws SQLException {
        ObservableList<User> usersList = FXCollections.observableArrayList();

        PreparedStatement statement = JDBC.getConnection().prepareStatement("SELECT * FROM users ");
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int userID = resultSet.getInt("User_ID");
            String username = resultSet.getString("User_Name");
            String password = resultSet.getString("Password");

            User user = new User(userID, username, password);
            usersList.add(user);
        }

        return usersList;
    }

    /**
     * Validates whether a given username and password match a user in the database.
     *
     * @param username the username to validate.
     * @param password the password to validate.
     * @return true if a user with the given username and password is found in the database; false otherwise.
     * @throws SQLException if there is an error retrieving data from the database
     */
    public static boolean validateUser(String username, String password) throws SQLException {
        PreparedStatement statement = JDBC.getConnection().prepareStatement("SELECT User_ID FROM users WHERE User_Name = ? AND Password = ?");
        statement.setString(1, username);
        statement.setString(2, password);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    }

    /**
     * Fetches the user ID for a given username.
     *
     * @param userName the username to search for.
     * @return the user ID for the given username.
     * @throws SQLException if there is an error retrieving data from the database
     */
    public static int getUserIDByName(String userName) throws SQLException {
        PreparedStatement statement = JDBC.getConnection().prepareStatement("SELECT User_ID FROM users WHERE User_Name = ?");
        statement.setString(1, userName);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("User_ID");
        } else {
            throw new SQLException(userName + " not found");
        }
    }

    /**
     * Fetches the username for a given user ID.
     *
     * @param userID the user ID to search for.
     * @return the username for the given user ID.
     * @throws SQLException if there is an error retrieving data from the database
     */
    public static String getUserNameByID(int userID) throws SQLException {
        PreparedStatement statement = JDBC.getConnection().prepareStatement("SELECT User_Name FROM users WHERE User_ID = ?");
        statement.setInt(1, userID);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString("User_Name");
        } else {
            throw new SQLException(userID + " not found");
        }
    }

}
