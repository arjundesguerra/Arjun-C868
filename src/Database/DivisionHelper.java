package Database;
import Models.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

/**
 * This class provides helper methods to interact with the divisions table in the database
 */
public class DivisionHelper {

    /**
     * Fetches all divisions for a given country ID from the database.
     * @param countryID the ID of the country to fetch divisions for
     * @return an ObservableList of Division objects representing the fetched divisions
     * @throws SQLException if an error occurs while accessing the database
     */
    public static ObservableList<Division> fetchDivisions(int countryID) throws SQLException {
        ObservableList<Division> divisionList = FXCollections.observableArrayList();

        PreparedStatement statement = JDBC.getConnection().prepareStatement(
                "SELECT d.Division_ID, d.Division " +
                        "FROM first_level_divisions d " +
                        "JOIN countries c ON d.COUNTRY_ID = c.Country_ID " +
                        "WHERE c.Country_ID = ? "
        );
        statement.setInt(1, countryID);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int divisionID = resultSet.getInt("Division_ID");
            String divisionName = resultSet.getString("Division");

            Division d = new Division(divisionID, divisionName);
            divisionList.add(d);
        }

        return divisionList;
    }

}

