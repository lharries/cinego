package utils;

import com.sun.rowset.CachedRowSetImpl;

import java.sql.*;

/**
 * Connects to the database and performs the key functions of execute, querying and updating the database.
 * Additionally converts queries into prepared statements to protect again SQL injection
 * Used by the DAO classes.
 * <p>
 * Sources:
 * - http://www.w3big.com/sqlite/sqlite-java.html
 * - http://www.sqlitetutorial.net/sqlite-java/sqlite-jdbc-driver/
 * - https://teamtreehouse.com/community/how-do-you-create-a-statement-and-execute-a-query-using-java-and-sql
 * - http://www.swtestacademy.com/database-operations-javafx/
 * - https://docs.oracle.com/javase/tutorial/jdbc/basics/prepared.html
 *
 * @author lukeharries kaiklasen
 */
public class SQLiteUtil {

    private static Connection connection;

    /**
     * connects to the sqlite database
     *
     * @throws SQLException           An exception that provides information on a database access
     *                                error or other errors
     * @throws ClassNotFoundException Unable to load the JDBC sqlite class
     */
    private static void connect() throws SQLException, ClassNotFoundException {

        String sqLiteClass = "org.sqlite.JDBC";
        Class.forName(sqLiteClass);

        String jdbcURL = "jdbc:sqlite:data.db";
        connection = DriverManager.getConnection(jdbcURL);

    }

    /**
     * Disconnects from the database
     *
     * @throws SQLException
     */
    private static void disconnect() throws SQLException {

        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    /**
     * Executes the SQL statement from a prepared statement formed
     * from the query and the arguments
     * <p>
     * e.g. execute("DELETE FROM film WHERE id = ?;",[1])
     *
     * @param query the query to execute
     * @param args  the prepared statement parameters
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void execute(String query, Object[] args) throws SQLException, ClassNotFoundException {

        PreparedStatement statement = null;

        try {

            connect();

            statement = connection.prepareStatement(query);

            if (args != null) {
                statement = createPreparedStatement(statement, args);
            }

            statement.execute();

        } catch (SQLException exception) {
            System.out.println("Unable to perform the query");
            System.out.println(query);
            throw exception;
        } finally {
            if (statement != null) {
                statement.close();
            }

            disconnect();
        }

    }

    /**
     * Executes the SQL statement query from a prepared statement formed
     * from the query and the arguments
     * <p>
     * e.g. executeQuery("SELECT * FROM film WHERE id = ?;",[1])
     *
     * @param query the query to execute
     * @param args  the arguments for the prepared statement
     * @return the results of the query
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static ResultSet executeQuery(String query, Object[] args) throws SQLException, ClassNotFoundException {

        PreparedStatement statement = null;
        ResultSet results = null;
        CachedRowSetImpl cachedRowSet;

        try {

            connect();

            statement = connection.prepareStatement(query);

            if (args != null) {
                statement = createPreparedStatement(statement, args);
            }
            results = statement.executeQuery();

            cachedRowSet = new CachedRowSetImpl();

            cachedRowSet.populate(results);

        } catch (SQLException exception) {
            System.out.println("Unable to perform the query");
            System.out.println(query);
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

    /**
     * Executes the SQL statement update from a prepared statement formed
     * from the query and the arguments
     * <p>
     * e.g. executeQuery("UPDATE film SET 'title' = ? WHERE id = ?",["King Kong", 1])
     *
     * @param query the query to execute
     * @param args  the arguments for the prepared statement
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void executeUpdate(String query, Object[] args) throws SQLException, ClassNotFoundException {


        PreparedStatement statement = null;

        try {
            connect();

            statement = connection.prepareStatement(query);

            if (args != null) {
                statement = createPreparedStatement(statement, args);
            }

            statement.executeUpdate();

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

    /**
     * creates the prepared statement given a the prepareStatement object where
     * the query has been set, and the arguments
     *
     * @param preparedStatement the preparedstatement to add in the arguments
     * @param args              an array of the objects which are to be added to the prepared statement
     * @return
     * @throws SQLException
     */
    private static PreparedStatement createPreparedStatement(PreparedStatement preparedStatement, Object[] args) throws SQLException {

        int parameterIndex = 1;

        for (Object arg : args) {
            if (arg.getClass().equals(Integer.class)) {
                preparedStatement.setInt(parameterIndex, (Integer) arg);
            } else if (arg.getClass().equals(String.class)) {
                preparedStatement.setString(parameterIndex, (String) arg);
            } else if (arg.getClass().equals(Boolean.class)) {
                preparedStatement.setBoolean(parameterIndex, (Boolean) arg);
            }
            parameterIndex++;
        }

        return preparedStatement;
    }

}