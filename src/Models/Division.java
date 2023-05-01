package Models;

/**
 * The Division class represents a division.
 */
public class Division {

    private int divisionID;
    private String divisionName;

    /**
     * Constructs a Division object with the specified parameters.
     *
     * @param divisionID the ID of the division
     * @param divisionName the name of the division
     */
    public Division(int divisionID, String divisionName) {
        this.divisionID = divisionID;
        this.divisionName = divisionName;
    }

    /**
     * Returns the ID of the division.
     *
     * @return the ID of the division
     */
    public int getDivisionID() { return divisionID; }

    /**
     * Returns the name of the division.
     *
     * @return the name of the division
     */
    public String getDivisionName() { return divisionName; }

    /**
     * Returns the string representation of the division.
     *
     * @return the name of the division
     */
    @Override
    public String toString() { return divisionName; }

}
