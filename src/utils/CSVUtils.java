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

public class CSVUtils {

    /**
     * Exports a list of relevant screening and movie statistics to directory: "../cinego/ScreeningsExport.csv"
     * Source:  - https://community.oracle.com/thread/2397100
     *          - http://csvjdbc.sourceforge.net/
     *
     * @throws IOException
     */
    @FXML
    public static void exportToCSV() throws IOException, ClassNotFoundException, SQLException {

        PrintStream file = new PrintStream("../cinego/ScreeningsExport.csv");
        boolean append = true;
        CsvDriver.writeToCsv(FilmDAO.getCSVResultSet(), file, append);
    }
}
