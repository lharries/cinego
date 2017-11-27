package application;
import java.sql.*;


/**
 * SQLiteConnection class is used to connect to the database
 * More specifically the connector() method creates a connection to the database
 */

public class SQLiteConnection{

    public static Connection connector(){

        try{
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:EmployeeDB.db");
            return conn;
        }
        catch (Exception e){
            return null;
        }
    }
}