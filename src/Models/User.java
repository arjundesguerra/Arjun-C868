package Models;

/**
 * The User class represents a user.
 */
public class User {
    private int userID;
    private String username;
    private String password;

    /**
     * Constructs a User object with the specified parameters.
     *
     * @param userID the ID of the user
     * @param username the username of the user
     * @param password the password of the user
     */
    public User(int userID, String username, String password) {
        this.userID = userID;
        this.username = username;
        this.password = password;
    }

    /**
     * Returns the ID of the user.
     *
     * @return the ID of the user
     */
    public int getUserID() { return userID; }

    /**
     * Returns the username of the user.
     *
     * @return the username of the user
     */
    public String getUsername() { return username; }

    /**
     * Returns the password of the user.
     *
     * @return the password of the user
     */
    public String getPassword() { return password; }
}