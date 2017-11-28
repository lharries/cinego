package utils;

import com.sun.rowset.CachedRowSetImpl;

import java.sql.*;

/**
 * Sources:
 * - http://www.w3big.com/sqlite/sqlite-java.html
 * - http://www.sqlitetutorial.net/sqlite-java/sqlite-jdbc-driver/
 * - https://teamtreehouse.com/community/how-do-you-create-a-statement-and-execute-a-query-using-java-and-sql
 * - http://www.swtestacademy.com/database-operations-javafx/
 *
 * @author lukeharries
 */
public class SQLiteConnection {

    public static Connection connection;

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        connect();
    }

    private static void connect() throws SQLException, ClassNotFoundException {

        String sqLiteClass = "org.sqlite.JDBC";
        Class.forName(sqLiteClass);

        String jdbcURL = "jdbc:sqlite:test.db";
        connection = DriverManager.getConnection(jdbcURL);

    }

    private static void disconnect() throws SQLException {

        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public static void execute(String query) throws SQLException, ClassNotFoundException {

        Statement statement = null;

        try {

            connect();

            statement = connection.createStatement();

            statement.execute(query);

        } catch (SQLException exception) {
            System.out.println("Unable to perform the query");
            throw exception;
        } finally {
            if (statement != null) {
                statement.close();
            }

            disconnect();
        }

    }

    public static ResultSet executeQuery(String query) throws SQLException, ClassNotFoundException {

        Statement statement = null;
        ResultSet results = null;
        CachedRowSetImpl cachedRowSet;

        try {

            connect();

            statement = connection.createStatement();

            results = statement.executeQuery(query);

            cachedRowSet = new CachedRowSetImpl();

            cachedRowSet.populate(results);

        } catch (SQLException exception) {
            System.out.println("Unable to perform the query");
            throw exception;
        } finally {
            if (results != null) {
                results.close();
            }
            if (statement != null) {
                statement.close();
            }

            disconnect();
        }

        return cachedRowSet;

    }

    public static int executeUpdate(String query) throws SQLException, ClassNotFoundException {


        Statement statement = null;
        int changesMade;

        try {

            connect();

            statement = connection.createStatement();

            changesMade = statement.executeUpdate(query);

        } catch (SQLException exception) {
            System.out.println("Unable to perform the query");
            throw exception;
        } finally {
            if (statement != null) {
                statement.close();
            }

            disconnect();
        }

        return changesMade;

    }

}