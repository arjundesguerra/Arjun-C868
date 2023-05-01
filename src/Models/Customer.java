package Models;

/**
 * The Customer class represents a customer.
 */
public class Customer {
    private int customerID;
    private String customerName;
    private String customerPhoneNumber;
    private String customerAddress;
    private String customerDivision;
    private String customerCountry;
    private String customerPostalCode;

    /**
     * Constructs a Customer object with the specified parameters.
     *
     * @param customerID the ID of the customer
     * @param customerName the name of the customer
     * @param customerPhoneNumber the phone number of the customer
     * @param customerAddress the address of the customer
     * @param customerDivision the division of the customer
     * @param customerCountry the country of the customer
     * @param customerPostalCode the postal code of the customer
     */
    public Customer(int customerID, String customerName, String customerPhoneNumber, String customerAddress, String customerDivision, String customerCountry, String customerPostalCode) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerPhoneNumber = customerPhoneNumber;
        this.customerAddress = customerAddress;
        this.customerDivision = customerDivision;
        this.customerCountry = customerCountry;
        this.customerPostalCode = customerPostalCode;
    }

    /**
     * Returns the ID of the customer.
     *
     * @return the ID of the customer
     */
    public int getCustomerID() { return customerID; }

    /**
     * Returns the name of the customer.
     *
     * @return the name of the customer
     */
    public String getCustomerName() { return customerName; }

    /**
     * Returns the phone number of the customer.
     *
     * @return the phone number of the customer
     */
    public String getCustomerPhoneNumber() { return customerPhoneNumber; }

    /**
     * Returns the address of the customer.
     *
     * @return the address of the customer
     */
    public String getCustomerAddress() { return customerAddress; }

    /**
     * Returns the division of the customer.
     *
     * @return the division of the customer
     */
    public String getCustomerDivision() { return customerDivision; }

    /**
     * Returns the country of the customer.
     *
     * @return the country of the customer
     */
    public String getCustomerCountry() { return customerCountry; }

    /**
     * Returns the postal code of the customer.
     *
     * @return the postal code of the customer
     */
    public String getCustomerPostalCode() { return customerPostalCode; }

}