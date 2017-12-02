package utils;

import com.sun.rowset.CachedRowSetImpl;
import models.PreparedStatementArg;

import java.sql.*;

/**
 * Sources:
 * - http://www.w3big.com/sqlite/sqlite-java.html
 * - http://www.sqlitetutorial.net/sqlite-java/sqlite-jdbc-driver/
 * - https://teamtreehouse.com/community/how-do-you-create-a-statement-and-execute-a-query-using-java-and-sql
 * - http://www.swtestacademy.com/database-operations-javafx/
 * - https://docs.oracle.com/javase/tutorial/jdbc/basics/prepared.html
 *
 * @author lukeharries
 */
public class SQLiteConnection {

    private static Connection connection;

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

    public static void execute(String query, PreparedStatementArg[] args) throws SQLException, ClassNotFoundException {

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
            throw exception;
        } finally {
            if (statement != null) {
                statement.close();
            }

            disconnect();
        }

    }

    public static ResultSet executeQuery(String query, PreparedStatementArg[] args) throws SQLException, ClassNotFoundException {

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

    public static int executeUpdate(String query, PreparedStatementArg[] args) throws SQLException, ClassNotFoundException {


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

    private static PreparedStatement setArgsPreparedStatement(PreparedStatement preparedStatement, PreparedStatementArg[] args) throws SQLException {

        int parameterIndex = 1;

        for (PreparedStatementArg arg : args) {
            if (arg.getType().equals("Integer")) {
                preparedStatement.setInt(parameterIndex, arg.getIntArg());
            } else if (arg.getType().equals("String")) {
                preparedStatement.setString(parameterIndex, arg.getStringArg());
            } else if (arg.getType().equals("Boolean")) {
                preparedStatement.setBoolean(parameterIndex, arg.getBooleanArg());
            }
            parameterIndex++;
        }

        return preparedStatement;
    }

}