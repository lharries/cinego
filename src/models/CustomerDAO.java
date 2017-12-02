package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.SQLiteConnection;

import java.sql.ResultSet;
import java.sql.SQLException;


public class CustomerDAO {

    public static void main(String[] args) {


        // Example of how to use the login
        try {
            Customer customer = login("customer", "customerpassword");
            if (customer != null) {
                System.out.println(customer.getFirstName());
                System.out.println(customer.getLastName());
                System.out.println(customer.getEmail());
                System.out.println(customer.getUsername());
            } else {
                System.out.println("Unable to login");

            }
        } catch (Exception e) {
            System.out.println("Printing the error");
            System.err.print(e);
        }
    }


    private static Customer getCustomerFromResultSet(ResultSet resultSet) throws SQLException, ClassNotFoundException {
        if (resultSet.next()) {
            Customer customer = new Customer();
            customer.setId(resultSet.getInt("id"));
            customer.setFirstName(resultSet.getString("firstName"));
            customer.setLastName(resultSet.getString("lastName"));
            customer.setEmail(resultSet.getString("email"));
            customer.setUsername(resultSet.getString("username"));
            return customer;
        } else {
            return null;
        }
    }

    public static Customer login(String username, String password) throws SQLException, ClassNotFoundException {
        // TODO: Switch to prepared statements?
        String query = "SELECT * FROM Customer WHERE username=? AND password=?";

        PreparedStatementArg[] preparedStatementArgs = new PreparedStatementArg[]{
                new PreparedStatementArg(null, username),
                new PreparedStatementArg(null, password)
        };

        ResultSet results = SQLiteConnection.executeQuery(query,preparedStatementArgs);
        if (results != null) {
            return getCustomerFromResultSet(results);
        } else {
            return null;
        }
    }

    private static ObservableList<Customer> getCustomerObservableList() throws SQLException, ClassNotFoundException {
        ResultSet resultSetCustomers = SQLiteConnection.executeQuery("SELECT * FROM Customer", null);

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
