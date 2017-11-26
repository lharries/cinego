package main;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String userName;

    public User(String firstName, String lastName, String email, String password, String userName) {
        this.firstName = firstName;
        this.lastName = lastName;
        // TODO: Validate email
        this.email = email;
        // TODO: Validate password difficulty;
        this.password = password;

        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public static void login(String username, String password) {
        Path p = Paths.get("../../data/users.txt");

        try {
            List<String> lines = Files.readAllLines(p);

            for (String line : lines) {
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logout() {

    }

    public void setPassword(String password) {
        this.password = password;
    }
}
