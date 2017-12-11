package application;


//import com.sun.deploy.Environment;
import com.sun.javafx.tools.packager.Log;

import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.Film;
import models.FilmDAO;
import models.Screening;
import models.ScreeningDAO;
import org.omg.CORBA.Environment;

//import org.relique.jdbc.csv.CsvDriver;
import org.relique.jdbc.csv.CsvDriver;


//import sun.tools.java.Environment;


import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * The EmployeeHomeController implements functionality specific to the EmployeeHome.fxml file which is part
 * of the cinema booking system - 'Cinego'.
 *
 * @author Luke Harries, Kai Klasen
 * @version 1.0
 */

//@author (classes and interfaces only, required)
//@version (classes and interfaces only, required. See footnote 1)
//@param (methods and constructors only)
//@return (methods only)
//@exception (@throws is a synonym added in Javadoc 1.2)


public class EmployeeHomeController implements Initializable {

    //TODO IMPORTANT: disable past dates + taken timeslots for creating a screening!


    //TODO: add loggers to the validation for us

    //TODO: ASK LUKE ABOUT THE CORRECT DATE / TIME FORMAT
    //TODO: add tooltips to buttons in order to convey additional information w.r.t. their functionality source: https://stackoverflow.com/questions/25338873/is-there-a-simple-way-to-display-hint-texts-in-javafx

    private static final Logger LOGGER = Logger.getLogger(CustomerRootController.class.getName());


    @FXML
    private Button toSeatBooking, deleteBooking;

    @FXML
    private ImageView backgroundImg, moviePoster;

    @FXML
    private AnchorPane AnchorPane;

    @FXML
    private TableView ScreeningsTable;

    @FXML
    private TableView<Film> moviesTable;

    @FXML
    private TableView<Screening> screeningsTable;

    @FXML
    private ObservableList<Film> moviesData;

    @FXML
    private ObservableList<Screening> screeningsData;

    @FXML
    private HBox hBox;

    @FXML
    private TableColumn titleCol, urlCol, descriptCol, titleColScreenTab, dateColScreenTab, timeColScreenTab;

    @FXML
    private TextField addTitle;

    @FXML
    private TextArea addDescription;

    @FXML
    private ComboBox movieSelectionBox, timePicker;

    @FXML
    private DatePicker datePicker;

    //reused variables in validation and creation of movies and screenings

    private String title, movieFileName, description, screeningTime, screeningDate, movieTitle;
    private Date dateTime;
    private Film film;
    public static int selectedScreeningId;


    /**
     * Sets the column titles of both tables and populates them with data from the database,
     * intialises the movieSelectionBox with the latest movies and renderes the background Image of the view
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //set moviesTable headers - 'moviesTable' + populates table
        titleCol.setCellValueFactory(new PropertyValueFactory<Film, String>("title"));
//        urlCol.setCellValueFactory(new PropertyValueFactory<Film, String>("imagePath"));
        descriptCol.setCellValueFactory(new PropertyValueFactory<Film, String>("description"));
        populateMoviesTable();

        //set screeningTable headers - 'screeningsTable' + populates movieTable & movieSelectBox
        titleColScreenTab.setCellValueFactory(new PropertyValueFactory<Screening, String>("filmTitle"));
        dateColScreenTab.setCellValueFactory(new PropertyValueFactory<Screening, String>("date"));
        timeColScreenTab.setCellValueFactory(new PropertyValueFactory<Screening, String>("date"));
        populateScreeningsTable();
        populateMovieSelectionBox();

    }

    /**
     * Allows the employee to upload a movie poster by copying the image into a local file and naming it
     * after the chosen movie title
     * Sources:
     * - http://java-buddy.blogspot.co.uk/2013/01/use-javafx-filechooser-to-open-image.html
     * - https://www.dyclassroom.com/image-processing-project/how-to-read-and-write-image-file-in-java
     */
    @FXML
    private void uploadMovieImage(Event event) {

        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

        //read image file & check if file not null
        File chosenFile = fileChooser.showOpenDialog(null);

        if (chosenFile != null) {

            // This seems to work but puts it in the wrong movies folder
            // locate the moviesDir
            String nameOfMovie = "name-of-new-movie.jpg";
            File directory = new File(".");
            File moviesDirectory = new File(directory.getAbsolutePath(), "movie-images");
            File newMovie = new File(moviesDirectory, nameOfMovie);
            System.out.println(moviesDirectory);

//
            try {
                newMovie.createNewFile();
                Files.copy(chosenFile.toPath(), newMovie.toPath(), StandardCopyOption.REPLACE_EXISTING);
                movieFileName = nameOfMovie;
                System.out.println(newMovie.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }


//            System.out.println(path);
//
////            URL f = getClass().getResource("resources");
////            System.out.println(f);
//
////            try {
//
//            File currentDir = new File (".");
//            System.out.println(currentDir.toPath());
//            File parentDir = currentDir.getParentFile();
//            System.out.println(parentDir.toPath());

//
//            File f = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
//            Path parent = f.toPath().getParent();
//            System.out.println(parent.toString());
//            Path resources = Paths.get(parent.toString(), "/resources");
//
//            movieFileName = "turtle.png";
////            System.out.println("resources");
////            System.out.println(resources);
////            File movieImages = new File(resources.toString());
////            System.out.println(movieImages);
////            System.out.println(Arrays.toString(movieImages.list()));
////            URL string = getClass().getResource("../resources");
//
////            URL movieImages = getClass().getResource("../../movie-images");
////            System.out.println(movieImages);
//
////            String resourcesPath = string.getPath();
////
//            // TODO: Switch to random string
//            File newFile = new File(resources.toString() + "/turtle.png");
//            System.out.println(newFile.getAbsolutePath().toString());
////
//            try {
//                newFile.createNewFile();
//                System.out.println(newFile);
//                Files.copy(chosenFile.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

            //got the source file, need to get the resources directory adjcaent to it, then create the new file in that

//            String newFilePath = f.getParent() + "/hello.jpg";
//            File newFile = new File(newFilePath);
//            try {
//
//                String[] list  = f.list((dir, name) -> {
//                    return Objects.equals(name, "resources");
//                });
//                System.out.println(Arrays.toString(list));
//                newFile.createNewFile();
//                System.out.println(newFile);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
////
//                System.out.println(f.getAbsolutePath());
//                image = ImageIO.read(chosenFile);
//                file = new File("/resources/hello.jpg");
//                System.out.println(file.getAbsolutePath());
//                file.createNewFile();
//                Files.copy(chosenFile.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
//
//                ImageIO.write(image, "jpg", file);
//                System.out.println(file.getAbsolutePath());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

//
//                movieFileName = "helloooooo" + ".jpg";
//                file = new File(movieFileName);
//                ImageIO.write(image, "jpg", file);
//                ImageIO.write(image, "jpg", file);
        }
    }

    /**
     * Validates user input before creating a movie.
     * Informs the user of outcome of test and only creates the movie when input is correct
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @FXML
    private void movieValidation() throws SQLException, ClassNotFoundException {

        //creates alert to be used in both cases: correct & incorrect inputs
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Stage popup = (Stage) alert.getDialogPane().getScene().getWindow();
        alert.setTitle("Cinego");
        popup.getIcons().add(new Image(this.getClass().getResource("/resources/cinestar.png").toString()));

        //gets the input values and checks if they're correctly filled in

        title = addTitle.getText();
        description = addDescription.getText();
        if (title.isEmpty() || description.isEmpty() || movieFileName == null) {
            alert.setHeaderText("Error: invalid input fields");
            alert.setContentText("Please fill in all required fields, " + Main.user.getFirstName());
        } else {
            alert.setHeaderText("Success: movie created");
            alert.setContentText("Your movie was successfully created, " + Main.user.getFirstName());
            PauseTransition delay = new PauseTransition(Duration.seconds(4));
            delay.setOnFinished(e -> popup.hide());
            popup.show();
            delay.play();

            createMovie();
        }
        PauseTransition delay = new PauseTransition(Duration.seconds(4));
        delay.setOnFinished(e -> popup.hide());
        popup.show();
        delay.play();

    }

    /**
     * Method that creates a new movie by inserting the user input into the database and re-populating
     * the movieTable from the database
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @FXML
    private void createMovie() throws SQLException, ClassNotFoundException {

        //store input in local variables to used for TableView and database input
        title = addTitle.getText();
        description = addDescription.getText();

        //adds the newly created movie to the database
        FilmDAO.insertFilm(title, description, movieFileName);

        //resets input fields to default + updates moviesTable & movieSelectionBox
        addTitle.clear();
        addDescription.clear();
        populateMoviesTable();
        populateMovieSelectionBox();
    }

    /**
     * Tests if input variables to create a screening are correct.
     * Informs the user of outcome of test and only creates screening when input is correct
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @FXML
    private void validateScreening() throws SQLException, ClassNotFoundException {

        //creates alert to be used in both cases: correct & incorrect inputs
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Stage popup = (Stage) alert.getDialogPane().getScene().getWindow();
        alert.setTitle("Cinego");
        popup.getIcons().add(new Image(this.getClass().getResource("/resources/cinestar.png").toString()));

        //get input values and check for validity
        movieTitle = String.valueOf(movieSelectionBox.getValue());
        screeningTime = String.valueOf(timePicker.getValue());
        screeningDate = String.valueOf(datePicker.getValue());


        // convert the dateTime to the correct format
        try {
            String dateTimeString = screeningDate + " " + screeningTime;
            dateTime = new SimpleDateFormat("yyyy-MM-DD HH:mm").parse(dateTimeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (movieTitle == null || screeningTime == null || dateTime == null) {
            alert.setHeaderText("Error: invalid input fields");
            alert.setContentText("Please fill in all required fields, " + Main.user.getFirstName());

        } else {
            alert.setHeaderText("Success: screening created");
            alert.setContentText("Your screening was successfully added, " + Main.user.getFirstName());
            PauseTransition delay = new PauseTransition(Duration.seconds(4));
            delay.setOnFinished(e -> popup.hide());
            popup.show();
            delay.play();

            createScreening();
        }
        PauseTransition delay = new PauseTransition(Duration.seconds(4));
        delay.setOnFinished(e -> popup.hide());
        popup.show();
        delay.play();


    }


    /**
     * Creates a new screening to inserting the user data into the database and re-populating the screeningTable
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @FXML
    private void createScreening() throws SQLException, ClassNotFoundException {

        //TODO: input validation - only when all three fields used + correct input then activate button

        //access input values & create date-time
        String date = dateTime.toString();
        film = (Film) movieSelectionBox.getSelectionModel().getSelectedItem();
        int movieID = film.getId();

//        DateTimeStringConverter date2 = new DateTimeStringConverter();
//        date2.fromString(date);


        //adds the newly created screening to the database
        ScreeningDAO.insertScreening(movieID, date, movieTitle);


        //resets input values to default + update screeningTable
        movieSelectionBox.setValue(null);
        timePicker.setValue(null);
        datePicker.setValue(null);
        populateScreeningsTable();
    }

    /**
     * Exports a list of relevant screening and movie statistics to directory: "../cinego/ScreeningsExport.csv"
     * Source:  - https://community.oracle.com/thread/2397100
     *          - http://csvjdbc.sourceforge.net/
     *
     * @throws IOException
     */
    @FXML
    private void exportToCSV() throws IOException, ClassNotFoundException, SQLException {

        //TODO: add further statistics to CSV file

        PrintStream file = new PrintStream("../cinego/ScreeningsExport.csv");
        boolean append = true;
        CsvDriver.writeToCsv(FilmDAO.getCSVResultSet(), file, append);

        //Informs user of successfully exporting CSV file
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Stage popup = (Stage) alert.getDialogPane().getScene().getWindow();
        alert.setTitle("Cinego");
        popup.getIcons().add(new Image(this.getClass().getResource("/resources/cinestar.png").toString()));
        alert.setHeaderText("CVS Export");
        alert.setContentText("Your csv export was successful, " + Main.user.getFirstName());


    }

    /**
     * Extracts the screeningsID upon selecting a screening from the screeningsTable
     */
    @FXML
    private void getScreeningID() {
        selectedScreeningId = screeningsTable.getSelectionModel().getSelectedItem().getId();
        toSeatBooking.setDisable(false);
        deleteBooking.setDisable(false);
    }


    @FXML
    private void deleteScreening() {

        //TODO: add checking if no booking functionality! Ability to delete screening or edit screening unless customers have booked a ticket for the movie

        //Prompts user to confirm deleting the selected screening
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Stage popup = (Stage) alert.getDialogPane().getScene().getWindow();
        alert.setTitle("Cinego");
        popup.getIcons().add(new Image(this.getClass().getResource("/resources/cinestar.png").toString()));
        alert.setHeaderText("Warning: deleting screening");
        alert.setContentText("Are you sure you want to delete this screening, " + Main.user.getFirstName() + " ?");

        //Deletes movie depending on user response
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                ScreeningDAO.deleteScreening(selectedScreeningId);
                populateScreeningsTable();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                LOGGER.logp(Level.WARNING, "EmployeeHomeController", "deleteScreening", "Failed to delete screening from screeningTable. See: " + e);
            }
        }
    }

    /**
     * @param event
     */
    @FXML
    private void openSeatsBooked(ActionEvent event) {
        //TODO: @Luke: open a movie's specific "seats booked overview" +


        try {
            EmployeeBookingController.selectedScreening = ScreeningDAO.getScreeningById(selectedScreeningId);
            EmployeeRootController emplRootController = new EmployeeRootController();
            emplRootController.openBookingView(event);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        toSeatBooking.setDisable(true);
    }

    /**
     * Updates the moviesTable with movie specific data from the database
     */
    private void populateMoviesTable() {
        try {
            moviesData = FilmDAO.getFilmObservableList();
            moviesTable.setItems(moviesData);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            LOGGER.logp(Level.WARNING, "EmployeeHomeController", "populateMoviesTable", "Failed to load data to populate the movies table. See: " + e);
        }
    }

    /**
     * Updates the screeningsTable with screening specific data from the database
     */
    private void populateScreeningsTable() {
        try {
            screeningsData = ScreeningDAO.getScreeningObservableList();
            screeningsTable.setItems(screeningsData);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            LOGGER.logp(Level.WARNING, "EmployeeHomeController", "populateScreeningsTable", "Failed to load data to populate the screenings table. See: " + e);
        }
    }

    /**
     * Updates the movieSelectionBox with the latest Movie Titles from the database
     */
    private void populateMovieSelectionBox() {
        try {
            movieSelectionBox.getItems().addAll(FilmDAO.getFilmObservableList());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            LOGGER.logp(Level.WARNING, "EmployeeHomeController", "populateMovieSelectionBox", "Failed to load data to populate the movieSelectionBox. See: " + e);
        }
    }

}
