package models;

import utils.SQLiteConnection;

import java.sql.ResultSet;

public class EmployeeDAO {

    public static void main(String[] args) {
        login("employee","test");

    }

    private static boolean login(String username, String password) {
        try {
            // TODO: Switch to prepared statementsgit
            ResultSet results = SQLiteConnection.executeQuery("SELECT * FROM Employee WHERE username=\"" + username+"\" AND password=\""+password+"\"");
            results.next();
            System.out.println(results.getString(1));
            System.out.println(results.getString(2));

            return true;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("No user found with those login details");
        } catch (Exception e) {
            System.out.print(e);
        }

        return false;

    };
}