package Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ServiceAppointmentHelper {

    public static void createServiceAppointment(int appointmentID, String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType,
                                                double serviceCost, String serviceType, LocalDateTime startDateTime, LocalDateTime endDateTime, int customerID, int userID, int contactID) throws SQLException {

        PreparedStatement statement = JDBC.getConnection().prepareStatement("INSERT INTO service_appointments VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP , 'user', CURRENT_TIMESTAMP, 'user', ?, ?, ?);");
        statement.setInt(1, appointmentID);
        statement.setString(2, appointmentTitle);
        statement.setString(3, appointmentDescription);
        statement.setString(4, appointmentLocation);
        statement.setString(5, appointmentType);
        statement.setDouble(6, serviceCost);
        statement.setString(7, serviceType);
        statement.setTimestamp(8, Timestamp.valueOf(startDateTime));
        statement.setTimestamp(9, Timestamp.valueOf(endDateTime));
        statement.setInt(10, customerID);
        statement.setInt(11, userID);
        statement.setInt(12, contactID);

        statement.execute();

    }
}
