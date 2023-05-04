package Models;

import java.time.LocalDateTime;

public class ServiceAppointment extends Appointment {
    private double serviceCost;
    private String serviceType;

    public ServiceAppointment(int appointmentID, String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType, double serviceCost, String serviceType, LocalDateTime startDateTime, LocalDateTime endDateTime, int customerID, int userID, int contactID) {
        super(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation, appointmentType, startDateTime, endDateTime, customerID, userID, contactID);
        this.serviceCost = serviceCost;
        this.serviceType = serviceType;
    }

    public String getServiceType() { return serviceType; }

    public double getServiceCost() { return serviceCost; }

}
