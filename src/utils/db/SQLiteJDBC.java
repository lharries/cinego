package db;

import com.sun.rowset.CachedRowSetImpl;

import java.sql.*;

/**
 * Sources:
 * - http://www.w3big.com/sqlite/sqlite-java.html
 * - http://www.sqlitetutorial.net/sqlite-java/sqlite-jdbc-driver/
 * - https://teamtreehouse.com/community/how-do-you-create-a-statement-and-execute-a-query-using-java-and-sql
 *
 * @author lukeharries
 */
public class SQLiteJDBC {

    private static String sqLiteClass = "org.sqlite.JDBC";
    private static String jdbcURL = "jdbc:sqlite:test.db";

    public static Connection c;

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

            Class.forName(sqLiteClass);

            c = DriverManager.getConnection(jdbcURL);

            System.out.println("Opened database successfully");

            executeQuery("CREATE TABLE IF NOT EXISTS warehouses (\n"
                    + "	id integer PRIMARY KEY,\n"
                    + "	name text NOT NULL,\n"
                    + "	capacity real\n"
                    + ");");
    }

    private static void connect() {

    }


    public static ResultSet executeQuery(String query) throws SQLException, ClassNotFoundException {

        connect();

        Statement statement = c.createStatement();

        ResultSet results = statement.executeQuery(query);

//        CachedRowSetImpl cachedRowSet = new CachedRowSetImpl();
//
//        cachedRowSet.populate(results);

        return results;

    }

    public static int executeUpdate(String query) throws SQLException, ClassNotFoundException {

        connect();

        Statement statement = c.createStatement();

        return statement.executeUpdate(query);

    }

}
