package Models;

import java.time.LocalDateTime;

public class SalesAppointment extends Appointment {
    private String vehicle;
    private String financingOptions;

    public SalesAppointment(int appointmentID, String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType, String vehicle, String financingOptions, LocalDateTime startDateTime, LocalDateTime endDateTime, int customerID, int userID, int contactID) {
        super(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation, appointmentType, startDateTime, endDateTime, customerID, userID, contactID);
        this.vehicle = vehicle;
        this.financingOptions = financingOptions;
    }

    public String getVehicle() { return vehicle; }

    public String getFinancingOptions() { return financingOptions; }

}
