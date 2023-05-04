package Models;

import java.time.LocalDateTime;

/**
 * The Appointment class represents an appointment.
 */
public abstract class Appointment {
    private int appointmentID;
    private String appointmentTitle;
    private String appointmentDescription;
    private String appointmentLocation;
    private String appointmentType;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private int customerID;
    private int userID;
    private int contactID;


    /**
     * Constructs an Appointment object with the specified parameters.
     *
     * @param appointmentID         the ID of the appointment
     * @param appointmentTitle      the title of the appointment
     * @param appointmentDescription the description of the appointment
     * @param appointmentLocation   the location of the appointment
     * @param appointmentType       the type of the appointment
     * @param startDateTime         the start date and time of the appointment
     * @param endDateTime           the end date and time of the appointment
     * @param customerID            the ID of the customer associated with the appointment
     * @param userID                the ID of the user associated with the appointment
     * @param contactID             the ID of the contact associated with the appointment
     */
    public Appointment(int appointmentID, String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType,
                       LocalDateTime startDateTime, LocalDateTime endDateTime, int customerID, int userID, int contactID) {

        this.appointmentID = appointmentID;
        this.appointmentTitle = appointmentTitle;
        this.appointmentDescription = appointmentDescription;
        this.appointmentLocation = appointmentLocation;
        this.appointmentType = appointmentType;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;

    }

    /**
     * Returns the ID of the appointment.
     *
     * @return the ID of the appointment
     */
    public int getAppointmentID() { return appointmentID; }

    /**
     * Returns the title of the appointment.
     *
     * @return the title of the appointment
     */
    public String getAppointmentTitle() { return appointmentTitle; }

    /**
     * Returns the description of the appointment.
     *
     * @return the description of the appointment
     */
    public String getAppointmentDescription() { return appointmentDescription; }

    /**
     * Returns the location of the appointment.
     *
     * @return the location of the appointment
     */
    public String getAppointmentLocation() { return appointmentLocation; }

    /**
     * Returns the type of the appointment.
     *
     * @return the type of the appointment
     */
    public String getAppointmentType() { return appointmentType; }

    /**
     * Returns the start date and time of the appointment.
     *
     * @return the start date and time of the appointment
     */
    public LocalDateTime getStartDateTime() { return startDateTime; }

    /**
     * Returns the end date and time of the appointment.
     *
     * @return the end date and time of the appointment
     */
    public LocalDateTime getEndDateTime() { return endDateTime; }

    /**
     * Returns the ID of the customer associated with the appointment.
     *
     * @return the ID of the customer associated with the appointment
     */
    public int getCustomerID() { return customerID; }

    /**
     * Returns the ID of the user associated with the appointment.
     *
     * @return the ID of the user associated with the appointment
     */
    public int getUserID() { return userID; }

    /**
     * Returns the ID of the contact associated with the appointment.
     * @return the ID of the contact associated with the appointment
     */
    public int getContactID() { return contactID; }


}
