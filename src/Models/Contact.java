package Models;

/**
 * The Contact class represents a contact.
 */
public class Contact {
    private int contactID;
    private String contactName;
    private String email;

    /**
     * Constructs a Contact object with the specified parameters.
     *
     * @param contactID the ID of the contact
     * @param contactName the name of the contact
     * @param email the email address of the contact
     */
    public Contact(int contactID, String contactName, String email) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.email = email;
    }

    /**
     * Returns the ID of the contact.
     *
     * @return the ID of the contact
     */
    public int getContactID() { return contactID; }

    /**
     * Returns the name of the contact.
     *
     * @return the name of the contact
     */
    public String getContactName() { return contactName; }

    /**
     * Returns the email address of the contact.
     *
     * @return the email address of the contact
     */
    public String getEmail() { return email; }


}
