package Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class SalesAppointmentHelper {

    public static void createSalesAppointment(int appointmentID, String appointmentTitle, String appointmentDescription, String appointmentLocation, String vehicleName,
                                              String financingOption, LocalDateTime startDateTime, LocalDateTime endDateTime, int customerID, int userID, int contactID) throws SQLException {

        PreparedStatement statement = JDBC.getConnection().prepareStatement("INSERT INTO sales_appointments VALUES(?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP , 'user', CURRENT_TIMESTAMP, 'user', ?, ?, ?);");
        statement.setInt(1, appointmentID);
        statement.setString(2, appointmentTitle);
        statement.setString(3, appointmentDescription);
        statement.setString(4, appointmentLocation);
        statement.setString(5, vehicleName);
        statement.setString(6, financingOption);
        statement.setTimestamp(7, Timestamp.valueOf(startDateTime));
        statement.setTimestamp(8, Timestamp.valueOf(endDateTime));
        statement.setInt(9, customerID);
        statement.setInt(10, userID);
        statement.setInt(11, contactID);

        statement.execute();

    }

    public static void deleteAppointment(int appointmentID) throws SQLException {
        String sqlDC = "DELETE from sales_appointments WHERE Appointment_ID = ?";
        try (PreparedStatement psDC = JDBC.getConnection().prepareStatement(sqlDC)) {
            psDC.setInt(1, appointmentID);
            psDC.execute();
        }
    }
}
