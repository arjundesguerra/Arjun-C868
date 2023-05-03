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
}
