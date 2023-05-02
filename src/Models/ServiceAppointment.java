package Models;

import java.time.LocalDateTime;

public class ServiceAppointment extends Appointment {


    public ServiceAppointment(int appointmentID, String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType, LocalDateTime startDateTime, LocalDateTime endDateTime, int customerID, int userID, int contactID) {
        super(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation, appointmentType, startDateTime, endDateTime, customerID, userID, contactID);
    }
}
