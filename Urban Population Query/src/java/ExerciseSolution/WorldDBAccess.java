/*
 * This class operates database and includes many methods to 
 * get data from database
 * 
 * @author Qing Ge Phoenix
 */

package ExerciseSolution;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WorldDBAccess {

    private Connection connection = null;

    private PreparedStatement getCityInfoById;

    private Boolean class_exists = false;
    private String DB, DBuser, DBpassword;

    //Using context parameter to set Database, user, and password
    WorldDBAccess(String database, String user, String password) {
        this.DB = database.trim();
        this.DBuser = user.trim();
        this.DBpassword = password.trim();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.class_exists = true;
        } catch (ClassNotFoundException ex) {
            System.err.println("Error: mysql Drivers not found!");
        }
    }

    /**
     * 
     * @return Connect database, if successfully, it will return true.
     */
    public boolean connect() {

        if (!this.class_exists) {
            return false;
        }
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://dev.fast.sheridanc.on.ca:3306/" 
                            + this.DB 
                            + "?useSSL=false", this.DBuser, this.DBpassword);

            try {
                getCityInfoById = connection.prepareStatement(
                        "SELECT Name, CountryCode, District, Population "
                                + "FROM city where ID=?");
            } catch (SQLException ex1) {
                System.err.println("Error: cannot create prepared statement!" 
                        + ex1.getMessage());
                try {
                    connection.close();
                } catch (SQLException ex2) {
                    System.err.println("Error: couldn not close DB connection"
                            + ": " + ex2.getMessage());
                }
                return false;
            }

        } catch (SQLException e) {
            System.err.println("Error: cannot connect to database. " 
                    + e.getMessage());
            return false;
        }

        return true;

    }
    
    /**
     * 
     * @return close connection, if successfully, it will return true.
     */
    public boolean closeConnection() {
        boolean success = true;
        try {
            getCityInfoById.close();
        } catch (SQLException ex) {
            System.err.println("Error: could not close prepared statement: " 
                    + ex.getMessage());
            success = false;
        }

        try {
            connection.close();
        } catch (SQLException ex) {
            System.err.println("Error: could not close connection: " 
                    + ex.getMessage());
            success = false;
        }
        return success;
    }

    /**
     * This method is for displaying city information depending on city ID
     *
     * @param cityId receives city ID which user input.
     * @return object of city containing city information
     */
    public City getCityInfoById(int cityId) {
        City city = null;

        try {
            getCityInfoById.setInt(1, cityId);
            ResultSet result = getCityInfoById.executeQuery();

            if (result.first()) {
                city = new City(result.getString("name"), 
                        result.getString("countryCode"),
                        result.getString("district"), 
                        result.getInt("population"));
            }

        } catch (SQLException e) {
            System.out.println("Could not get city info");

        }
        return city;

    }

}
