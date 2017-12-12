package utils;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import models.FilmDAO;
import org.relique.jdbc.csv.CsvDriver;

import java.io.IOException;
import java.io.PrintStream;
import java.sql.SQLException;

public class CSVUtil {

    /**
     * Exports a list of relevant screening and movie statistics to directory: "../cinego/ScreeningsExport.csv"
     * Source:  - https://community.oracle.com/thread/2397100
     * - http://csvjdbc.sourceforge.net/
     *
     * @throws IOException
     */
    @FXML
    public static void exportToCSV() throws IOException, ClassNotFoundException, SQLException {

        //TODO: add further statistics to CSV file

        PrintStream file = new PrintStream("../cinego/ScreeningsExport.csv");
        boolean append = true;
        CsvDriver.writeToCsv(FilmDAO.getCSVResultSet(), file, append);

        //Informs user of successfully exporting CSV file
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cinego");
        alert.setHeaderText("CVS Export");
        alert.setContentText("Your csv export was successful, " + Main.user.getFirstName());

    }
}
