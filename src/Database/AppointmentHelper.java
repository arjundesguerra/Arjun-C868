package Database;

import Models.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

/**
 * This class provides helper methods to interact with the appointments table in the database
 */
public class AppointmentHelper {

    /**
     * Retrieves all appointments from the database.
     *
     * @return an observable list of appointments
     * @throws SQLException if there is an error retrieving data from the database
     */
    public static ObservableList<Appointment> fetchAppointments() throws SQLException {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        PreparedStatement statement = JDBC.getConnection().prepareStatement("SELECT * FROM appointments ");

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int appointmentID = resultSet.getInt("Appointment_ID");
            String appointmentTitle = resultSet.getString("Title");
            String appointmentDescription = resultSet.getString("Description");
            String appointmentLocation = resultSet.getString("Location");
            String appointmentType = resultSet.getString("Type");
            LocalDateTime startDateTime = resultSet.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endDateTime = resultSet.getTimestamp("End").toLocalDateTime();
            int customerID = resultSet.getInt("Customer_ID");
            int userID = resultSet.getInt("User_ID");
            int contactID = resultSet.getInt("Contact_ID");


            Appointment appointment = new Appointment(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation, appointmentType, startDateTime, endDateTime, customerID, userID, contactID);
            appointmentList.add(appointment);
        }

        return appointmentList;
    }

    /**
     * Retrieves all appointments for the current month from the database.
     *
     * @return an observable list of appointments for the current month
     * @throws SQLException if there is an error retrieving data from the database
     */
    public static ObservableList<Appointment> fetchAppointmentsByMonth() throws SQLException {
        ObservableList<Appointment> monthAppointmentList = FXCollections.observableArrayList();

        LocalDate currentDate = LocalDate.now();
        LocalDate endDate = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), currentDate.lengthOfMonth());
        LocalDate startDate = currentDate.withDayOfMonth(1);

        PreparedStatement statement = JDBC.getConnection().prepareStatement("SELECT * FROM appointments WHERE Start >= ? AND Start <= ?");
        statement.setTimestamp(1, Timestamp.valueOf(startDate.atStartOfDay()));
        statement.setTimestamp(2, Timestamp.valueOf(endDate.plusDays(1).atStartOfDay()));

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int appointmentID = resultSet.getInt("Appointment_ID");
            String appointmentTitle = resultSet.getString("Title");
            String appointmentDescription = resultSet.getString("Description");
            String appointmentLocation = resultSet.getString("Location");
            String appointmentType = resultSet.getString("Type");
            LocalDateTime startDateTime = resultSet.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endDateTime = resultSet.getTimestamp("End").toLocalDateTime();
            int customerID = resultSet.getInt("Customer_ID");
            int userID = resultSet.getInt("User_ID");
            int contactID = resultSet.getInt("Contact_ID");

            Appointment appointment = new Appointment(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation, appointmentType, startDateTime, endDateTime, customerID, userID, contactID);
            monthAppointmentList.add(appointment);
        }

        return monthAppointmentList;
    }

    /**
     * Retrieves all appointments for the current week from the database.
     *
     * @return an observable list of appointments for the current week
     * @throws SQLException if there is an error retrieving data from the database
     */
    public static ObservableList<Appointment> fetchAppointmentsByWeek() throws SQLException {
        ObservableList<Appointment> weekAppointmentList = FXCollections.observableArrayList();

        LocalDate currentDate = LocalDate.now();
        LocalDate endDate = currentDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).minusDays(1);
        LocalDate startDate = currentDate;

        PreparedStatement statement = JDBC.getConnection().prepareStatement("SELECT * FROM appointments WHERE Start >= ? AND Start <= ?");
        statement.setTimestamp(1, Timestamp.valueOf(startDate.atStartOfDay()));
        statement.setTimestamp(2, Timestamp.valueOf(endDate.plusDays(1).atStartOfDay()));

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int appointmentID = resultSet.getInt("Appointment_ID");
            String appointmentTitle = resultSet.getString("Title");
            String appointmentDescription = resultSet.getString("Description");
            String appointmentLocation = resultSet.getString("Location");
            String appointmentType = resultSet.getString("Type");
            LocalDateTime startDateTime = resultSet.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endDateTime = resultSet.getTimestamp("End").toLocalDateTime();
            int customerID = resultSet.getInt("Customer_ID");
            int userID = resultSet.getInt("User_ID");
            int contactID = resultSet.getInt("Contact_ID");

            Appointment appointment = new Appointment(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation, appointmentType, startDateTime, endDateTime, customerID, userID, contactID);
            weekAppointmentList.add(appointment);
        }

        return weekAppointmentList;
    }

    /**
     * Retrieves all appointments between the specified start and end times from the database.
     *
     * @param start the start time of the appointments to retrieve
     * @param end the end time of the appointments to retrieve
     * @return an observable list of appointments between the specified start and end times
     * @throws SQLException if there is an error retrieving data from the database
     */
    public static ObservableList<Appointment> fetchAppointmentsByTime(LocalDateTime start, LocalDateTime end) throws SQLException {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        PreparedStatement statement = JDBC.getConnection().prepareStatement("SELECT * FROM appointments WHERE Start >= ? AND Start < ?");
        statement.setTimestamp(1, Timestamp.valueOf(start));
        statement.setTimestamp(2, Timestamp.valueOf(end));

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int appointmentID = resultSet.getInt("Appointment_ID");
            String appointmentTitle = resultSet.getString("Title");
            String appointmentDescription = resultSet.getString("Description");
            String appointmentLocation = resultSet.getString("Location");
            String appointmentType = resultSet.getString("Type");
            LocalDateTime startDateTime = resultSet.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endDateTime = resultSet.getTimestamp("End").toLocalDateTime();
            int customerID = resultSet.getInt("Customer_ID");
            int userID = resultSet.getInt("User_ID");
            int contactID = resultSet.getInt("Contact_ID");

            Appointment appointment = new Appointment(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation, appointmentType, startDateTime, endDateTime, customerID, userID, contactID);
            appointmentList.add(appointment);
        }

        return appointmentList;
    }

    /**
     * Retrieves all appointments associated with the specified contact name from the database.
     *
     * @param contactName the name of the contact to retrieve appointments for
     * @return an observable list of appointments associated with the specified contact name
     * @throws SQLException if there is an error retrieving data from the database
     */
    public static ObservableList<Appointment> fetchAppointmentsByContact(String contactName) throws SQLException {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        PreparedStatement statement = JDBC.getConnection().prepareStatement(
                "SELECT * FROM appointments " +
                        "JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID " +
                        "WHERE contacts.Contact_Name = ?");
        statement.setString(1, contactName);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int appointmentID = resultSet.getInt("Appointment_ID");
            String appointmentTitle = resultSet.getString("Title");
            String appointmentDescription = resultSet.getString("Description");
            String appointmentLocation = resultSet.getString("Location");
            String appointmentType = resultSet.getString("Type");
            LocalDateTime startDateTime = resultSet.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endDateTime = resultSet.getTimestamp("End").toLocalDateTime();
            int customerID = resultSet.getInt("Customer_ID");
            int userID = resultSet.getInt("User_ID");
            int contactID = resultSet.getInt("Contact_ID");

            Appointment appointment = new Appointment(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation, appointmentType, startDateTime, endDateTime, customerID, userID, contactID);
            appointmentList.add(appointment);
        }

        return appointmentList;
    }

    /**
     * Creates a new appointment in the database.
     *
     * @param appointmentID         the appointment ID
     * @param appointmentTitle      the appointment title
     * @param appointmentDescription the appointment description
     * @param appointmentLocation   the appointment location
     * @param appointmentType       the appointment type
     * @param startDateTime         the start date and time of the appointment
     * @param endDateTime           the end date and time of the appointment
     * @param customerID            the customer ID associated with the appointment
     * @param userID                the user ID associated with the appointment
     * @param contactID             the contact ID associated with the appointment
     * @throws SQLException if there is an error inserting data into the database
     */
    public static void createAppointment(int appointmentID, String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType,
                                         LocalDateTime startDateTime, LocalDateTime endDateTime, int customerID, int userID, int contactID) throws SQLException {

        PreparedStatement statement = JDBC.getConnection().prepareStatement("INSERT INTO appointments VALUES(?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP , 'user', CURRENT_TIMESTAMP, 'user', ?, ?, ?);");
        statement.setInt(1, appointmentID);
        statement.setString(2, appointmentTitle);
        statement.setString(3, appointmentDescription);
        statement.setString(4, appointmentLocation);
        statement.setString(5, appointmentType);
        statement.setTimestamp(6, Timestamp.valueOf(startDateTime));
        statement.setTimestamp(7, Timestamp.valueOf(endDateTime));
        statement.setInt(8, customerID);
        statement.setInt(9, userID);
        statement.setInt(10, contactID);

        statement.execute();

    }

    /**
     * Edits an existing appointment in the database.
     *
     * @param appointmentID         the appointment ID
     * @param appointmentTitle      the appointment title
     * @param appointmentDescription the appointment description
     * @param appointmentLocation   the appointment location
     * @param appointmentType       the appointment type
     * @param startDateTime         the start date and time of the appointment
     * @param endDateTime           the end date and time of the appointment
     * @param customerID            the customer ID associated with the appointment
     * @param userID                the user ID associated with the appointment
     * @param contactID             the contact ID associated with the appointment
     * @throws SQLException if there is an error updating data in the database
     */
    public static void editAppointment(int appointmentID, String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType,
                                         LocalDateTime startDateTime, LocalDateTime endDateTime, int customerID, int userID, int contactID) throws SQLException {

        PreparedStatement statement = JDBC.getConnection().prepareStatement("UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = CURRENT_TIMESTAMP, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?");
        statement.setString(1, appointmentTitle);
        statement.setString(2, appointmentDescription);
        statement.setString(3, appointmentLocation);
        statement.setString(4, appointmentType);
        statement.setTimestamp(5, Timestamp.valueOf(startDateTime));
        statement.setTimestamp(6, Timestamp.valueOf(endDateTime));
        statement.setInt(7, customerID);
        statement.setInt(8, userID);
        statement.setInt(9, contactID);
        statement.setInt(10, appointmentID);


        statement.execute();

    }

    /**
     * Deletes an appointment from the database.
     *
     * @param appointmentID         the appointment ID to delete
     * @throws SQLException        if there is an error executing the SQL statement
     */
    public static void deleteAppointment(int appointmentID) throws SQLException {
        String sqlDC = "DELETE from appointments WHERE Appointment_ID = ?";
        try (PreparedStatement psDC = JDBC.getConnection().prepareStatement(sqlDC)) {
            psDC.setInt(1, appointmentID);
            psDC.execute();
        }
    }

    /**
     * Determines whether an appointment overlaps with an existing appointment for the specified customer.
     *
     * @param startDateTime The start date and time of the appointment to be checked.
     * @param endDateTime The end date and time of the appointment to be checked.
     * @param customerID The ID of the customer whose existing appointments should be checked for overlap.
     * @param appointmentID The ID of the appointment being edited (if any).
     * @return true if the appointment overlaps with an existing appointment for the specified customer, false otherwise.
     * @throws SQLException if there is an error retrieving data from the database
     */
    public static boolean isAppointmentOverlap(LocalDateTime startDateTime, LocalDateTime endDateTime, int customerID, int appointmentID) throws SQLException {
        ObservableList<Appointment> appointments = fetchAppointments();

        for (Appointment appointment : appointments) {
            if (appointment.getCustomerID() == customerID && appointment.getAppointmentID() != appointmentID && appointment.getStartDateTime().isBefore(endDateTime) && startDateTime.isBefore(appointment.getEndDateTime())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Determines whether a customer has any appointments.
     *
     * @param customerID The ID of the customer to check for appointments.
     * @return true if the customer has any appointments, false otherwise.
     * @throws SQLException if there is an error retrieving data from the database
     */
    public static boolean customerHasAppointments(int customerID) throws SQLException {
        ObservableList<Appointment> appointments = fetchAppointments();

        for (Appointment appointment : appointments) {
            if (appointment.getCustomerID() == customerID) {
                return true;
            }
        }

        return false;
    }

    /**
     * Retrieves the highest ID currently in use for appointments in the database.
     * @return the highest ID currently in use for appointments in the database
     * @throws SQLException if there is an error retrieving data from the database
     */
    public static int maxID() throws SQLException {
        int appointmentID = 0;
        PreparedStatement statement = JDBC.getConnection().prepareStatement("SELECT MAX(Appointment_ID) FROM appointments");
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            appointmentID = resultSet.getInt(1) + 1;
        }
        return appointmentID;
    }

}
