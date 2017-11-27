package application;
import javax.xml.transform.Result;
import java.sql.*;


/**
 * SQLiteConnection class is used to connect to the database
 * More specifically the connector() method creates a connection to the database
 */

public class SQLiteConnection{

    public static Connection connector(){

        String query = "CREATE TABLE IF NOT EXISTS warehouses (\n"+ "id integer PRIMARY KEY,\n"
        + "    name text NOT NULL,\n"
        + "    capacity real\n"
        + ");";


        try{
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:User.db");
            Statement statement = conn.createStatement();
            statement.executeQuery(query);



            return conn;
        }
        catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
}