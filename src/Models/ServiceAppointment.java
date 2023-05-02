package Models;

import java.time.LocalDateTime;

public class ServiceAppointment extends Appointment {
    private String serviceType;
    private double serviceCost;

    public ServiceAppointment(int appointmentID, String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType, String serviceType, double serviceCost, LocalDateTime startDateTime, LocalDateTime endDateTime, int customerID, int userID, int contactID) {
        super(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation, appointmentType, startDateTime, endDateTime, customerID, userID, contactID);
        this.serviceType = serviceType;
        this.serviceCost = serviceCost;
    }

    public String getServiceType() { return serviceType; }

    public double getServiceCost() { return serviceCost; }

}
