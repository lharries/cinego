package application;

import java.sql.*;


public class LoginModel{
    Connection connection;

    public LoginModel(){
        connection = SQLiteConnection.connector();
        if(connection == null){
            System.exit(1);
        }
    }

    public boolean isDbConnected(){
        try{
            return !connection.isClosed();
        }
        catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}