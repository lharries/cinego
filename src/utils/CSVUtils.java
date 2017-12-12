package utils;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import models.FilmDAO;
import org.relique.jdbc.csv.CsvDriver;
import sun.security.krb5.internal.crypto.Des;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.SQLException;

public class CSVUtils {

    /**
     * Exports a list of relevant screening and movie statistics to directory: "./ScreeningsExport.csv"
     * References:
     * - https://community.oracle.com/thread/2397100
     * - http://csvjdbc.sourceforge.net/
     *
     * @throws IOException
     */
    @FXML
    public static void exportToCSV() throws IOException, ClassNotFoundException, SQLException {

        // get the csv file
        File directory = new File(".");
        File csvFile = new File(directory.getAbsolutePath(), "ScreeningsExport.csv");
        csvFile.createNewFile();

        // write to the csv file
        PrintStream printStream = new PrintStream(csvFile);
        CsvDriver.writeToCsv(FilmDAO.getCSVResultSet(), printStream, true);

        // display the exported csv file if possible otherwise show an alert
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().open(csvFile);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Cinego");
            alert.setHeaderText("CVS Export");
            alert.setContentText("Your csv export was successful, " + Main.user.getFirstName());
            alert.showAndWait();
        }
    }
}