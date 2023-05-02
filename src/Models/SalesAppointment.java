package Models;

import java.time.LocalDateTime;

public class SalesAppointment extends Appointment {
    private String tradeInVehicle;
    private String financingOptions;

    public SalesAppointment(int appointmentID, String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType, String tradeInVehicle, String financingOptions, LocalDateTime startDateTime, LocalDateTime endDateTime, int customerID, int userID, int contactID) {
        super(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation, appointmentType, startDateTime, endDateTime, customerID, userID, contactID);
        this.tradeInVehicle = tradeInVehicle;
        this.financingOptions = financingOptions;
    }

}
