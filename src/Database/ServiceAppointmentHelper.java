package Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ServiceAppointmentHelper {

    public static void createServiceAppointment(int appointmentID, String appointmentTitle, String appointmentDescription, String appointmentLocation, double serviceCost,
                                                String serviceType, LocalDateTime startDateTime, LocalDateTime endDateTime, int customerID, int userID, int contactID) throws SQLException {

        PreparedStatement statement = JDBC.getConnection().prepareStatement("INSERT INTO service_appointments VALUES(?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP , 'user', CURRENT_TIMESTAMP, 'user', ?, ?, ?);");
        statement.setInt(1, appointmentID);
        statement.setString(2, appointmentTitle);
        statement.setString(3, appointmentDescription);
        statement.setString(4, appointmentLocation);
        statement.setDouble(5, serviceCost);
        statement.setString(6, serviceType);
        statement.setTimestamp(7, Timestamp.valueOf(startDateTime));
        statement.setTimestamp(8, Timestamp.valueOf(endDateTime));
        statement.setInt(9, customerID);
        statement.setInt(10, userID);
        statement.setInt(11, contactID);

        statement.execute();

    }

    public static void editServiceAppointment(int appointmentID, String appointmentTitle, String appointmentDescription, String appointmentLocation, double serviceCost,
                                              String serviceType, LocalDateTime startDateTime, LocalDateTime endDateTime, int customerID, int userID, int contactID) throws SQLException {

        PreparedStatement statement = JDBC.getConnection().prepareStatement("UPDATE service_appointments SET Title = ?, Description = ?, Location = ?, Service_Cost = ?, Service_Type = ?, Start = ?, End = ?, Last_Update = CURRENT_TIMESTAMP, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?");
        statement.setString(1, appointmentTitle);
        statement.setString(2, appointmentDescription);
        statement.setString(3, appointmentLocation);
        statement.setDouble(4, serviceCost);
        statement.setString(5, serviceType);
        statement.setTimestamp(6, Timestamp.valueOf(startDateTime));
        statement.setTimestamp(7, Timestamp.valueOf(endDateTime));
        statement.setInt(8, customerID);
        statement.setInt(9, userID);
        statement.setInt(10, contactID);
        statement.setInt(11, appointmentID);

        statement.execute();

    }

    public static void deleteAppointment(int appointmentID) throws SQLException {
        String sqlDC = "DELETE from service_appointments WHERE Appointment_ID = ?";
        try (PreparedStatement psDC = JDBC.getConnection().prepareStatement(sqlDC)) {
            psDC.setInt(1, appointmentID);
            psDC.execute();
        }
    }
}
