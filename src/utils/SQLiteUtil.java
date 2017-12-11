package utils;

import com.sun.rowset.CachedRowSetImpl;

import java.sql.*;

/**
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

    public static Connection connection;

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        connect();
    }

    private static void connect() throws SQLException, ClassNotFoundException {

        String sqLiteClass = "org.sqlite.JDBC";
        Class.forName(sqLiteClass);

        String jdbcURL = "jdbc:sqlite:data.db";
        connection = DriverManager.getConnection(jdbcURL);

    }

    private static void disconnect() throws SQLException {

        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public static void execute(String query, Object[] args) throws SQLException, ClassNotFoundException {

        PreparedStatement statement = null;

        try {

            connect();

            statement = connection.prepareStatement(query);

            if (args != null) {
                statement = setArgsPreparedStatement(statement, args);
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

    public static ResultSet executeQuery(String query, Object[] args) throws SQLException, ClassNotFoundException {

        PreparedStatement statement = null;
        ResultSet results = null;
        CachedRowSetImpl cachedRowSet;

        try {

            connect();

            statement = connection.prepareStatement(query);

            if (args != null) {
                statement = setArgsPreparedStatement(statement, args);
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

    public static int executeUpdate(String query, Object[] args) throws SQLException, ClassNotFoundException {


        PreparedStatement statement = null;
        int changesMade;

        try {
            connect();

            statement = connection.prepareStatement(query);

            if (args != null) {
                statement = setArgsPreparedStatement(statement, args);
            }

            changesMade = statement.executeUpdate();

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

    private static PreparedStatement setArgsPreparedStatement(PreparedStatement preparedStatement, Object[] args) throws SQLException {

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