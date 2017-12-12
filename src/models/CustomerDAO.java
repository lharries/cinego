package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.SQLiteUtil;

import java.sql.ResultSet;
import java.sql.SQLException;


public class CustomerDAO {

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


    private static ObservableList<Customer> getCustomerObservableList() throws SQLException, ClassNotFoundException {
        ResultSet resultSetCustomers = SQLiteUtil.executeQuery("SELECT * FROM Customer", null);

        return getCustomerList(resultSetCustomers);
    }

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
