package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.SQLiteUtil;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * The Data access object responsible for getting and saving the {@link Customer} data.
 * <p>
 * Based on the DAO design pattern.
 *
 * @author lukeharries kaiklasen
 * @version 1.0.0
 */
public class CustomerDAO {

    /**
     * get the customer by id
     *
     * @param id id of the customer
     * @return Customer
     * @throws SQLException           SQLException
     * @throws ClassNotFoundException ClassNotFoundException
     */
    public static Customer getById(int id) throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM Customer WHERE id=?";

        Object[] preparedStatementArgs = {id};

        ResultSet results = SQLiteUtil.executeQuery(query, preparedStatementArgs);

        if (results.next()) {
            Customer customer = new Customer();
            customer.setId(results.getInt("id"));
            customer.setFirstName(results.getString("firstName"));
            customer.setLastName(results.getString("lastName"));
            customer.setUsername(results.getString("username"));
            customer.setEmail(results.getString("email"));
            return customer;
        } else {
            return null;
        }
    }


    /**
     * Convert the customer result set to the customer object
     *
     * @param resultSet customer result set
     * @return Customer
     * @throws SQLException           SQLException
     * @throws ClassNotFoundException ClassNotFoundException
     */
    private static Customer getCustomerFromResultSet(ResultSet resultSet) throws SQLException, ClassNotFoundException {
        if (resultSet.next()) {
            Customer customer = new Customer();
            customer.setId(resultSet.getInt("id"));
            customer.setFirstName(resultSet.getString("firstName"));
            customer.setLastName(resultSet.getString("lastName"));
            customer.setUsername(resultSet.getString("username"));
            customer.setEmail(resultSet.getString("email"));
            return customer;
        } else {
            return null;
        }
    }

    /**
     * Get the customer with the following username and password
     *
     * @param username username
     * @param password password
     * @return Customer id
     * @throws SQLException           SQLException
     * @throws ClassNotFoundException ClassNotFoundException
     */
    public static Customer login(String username, String password) throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM Customer WHERE username=? AND password=?";

        Object[] preparedStatementArgs = {
                username,
                password
        };

        ResultSet results = SQLiteUtil.executeQuery(query, preparedStatementArgs);
        if (results != null) {
            return getCustomerFromResultSet(results);
        } else {
            return null;
        }
    }

    /**
     * Update the customer object
     *
     * @param firstName firstName
     * @param lastName  lastName
     * @param email     email
     * @param id        id
     * @throws SQLException           SQLException
     * @throws ClassNotFoundException ClassNotFoundException
     */
    public static void updateCustomerDetails(String firstName, String lastName, String email, int id) throws SQLException, ClassNotFoundException {

        String query = "UPDATE Customer SET firstName = ?, lastName = ? , email = ? WHERE id = ?";

        Object[] preparedStatementArgs = {
                firstName,
                lastName,
                email,
                id
        };
        SQLiteUtil.executeUpdate(query, preparedStatementArgs);
    }

    /**
     * Get the customer observable list
     *
     * @return customers
     * @throws SQLException           SQLException
     * @throws ClassNotFoundException ClassNotFoundException
     */
    private static ObservableList<Customer> getCustomerObservableList() throws SQLException, ClassNotFoundException {
        ResultSet resultSetCustomers = SQLiteUtil.executeQuery("SELECT * FROM Customer", null);

        return getCustomerList(resultSetCustomers);
    }

    /**
     * Convert the customer list to a results set
     *
     * @param resultSet resultSet
     * @return observable list of customer
     * @throws SQLException           SQLException
     * @throws ClassNotFoundException ClassNotFoundException
     */
    private static ObservableList<Customer> getCustomerList(ResultSet resultSet) throws SQLException, ClassNotFoundException {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        while (resultSet.next()) {
            Customer customer = new Customer();
            customer.setId(resultSet.getInt("id"));
            customer.setFirstName(resultSet.getString("firstName"));
            customer.setLastName(resultSet.getString("lastName"));
            customer.setEmail(resultSet.getString("email"));
            customer.setUsername(resultSet.getString("username"));
            customerList.add(customer);
        }

        return customerList;
    }
}
