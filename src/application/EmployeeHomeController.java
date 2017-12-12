package application;


//import com.sun.deploy.Environment;

import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import models.*;

//import org.relique.jdbc.csv.CsvDriver;
import org.relique.jdbc.csv.CsvDriver;


//import sun.tools.java.Environment;


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
    private Button toSeatBooking, deleteScreening, editMovieButton, updateMovieButton, createMovieButton, moviePosterBttn;

//    @FXML
//    private ImageView backgroundImg, moviePoster;
//
//    @FXML
//    private AnchorPane AnchorPane;
//
//    @FXML
//    private TableView ScreeningsTable;

    @FXML
    private TableView<Film> moviesTable;

    @FXML
    private TableView<Screening> screeningsTable;

    @FXML
    private ObservableList<Film> moviesData;

    @FXML
    private ObservableList<Screening> screeningsData;

//    @FXML
//    private HBox hBox;

    @FXML
    private TableColumn titleCol, urlCol, descriptCol, titleColScreenTab, dateColScreenTab;

    @FXML
    private TextField addTitle, trailerURL;

    @FXML
    private TextArea addDescription;

    @FXML
    private ComboBox movieSelectionBox, timePicker;

    @FXML
    private DatePicker datePicker;

    //reused variables in validation and creation of movies and screenings

    private String title, movieFileName, description, screeningTime, screeningDate, movieTitle, movieTrailerURL;
    private Date dateTime;
    private Film film;
    public static int selectedScreeningId, selectedMovieId;

    FileChooser fileChooser;
    File chosenFile;



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

//        moviesTable.setStyle("-fx-font: blue");
//                textLabel.textColor = [UIColor redColor];
        descriptCol.setCellValueFactory(new PropertyValueFactory<Film, String>("description"));
        populateMoviesTable();

        //set screeningTable headers - 'screeningsTable' + populates movieTable & movieSelectBox
        titleColScreenTab.setCellValueFactory(new PropertyValueFactory<Screening, String>("filmTitle"));
        dateColScreenTab.setCellValueFactory(new PropertyValueFactory<Screening, String>("date"));
        populateScreeningsTable();
        populateMovieSelectionBox();

    }

    /**
     * Allows the employee to select a movie poster to be uploaded by copying the image into a local file and naming it
     * after the chosen movie title. Upload takes place only after 'createMovieButton' is clicked
     *
     * Sources:
     * - http://java-buddy.blogspot.co.uk/2013/01/use-javafx-filechooser-to-open-image.html
     * - https://www.dyclassroom.com/image-processing-project/how-to-read-and-write-image-file-in-java
     */
    @FXML
    private void uploadMovieImage(Event event) {

        fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

        //read image file & check if file not null
        chosenFile = fileChooser.showOpenDialog(null);
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
        movieTrailerURL = trailerURL.getText();
        if (title.isEmpty() || description.isEmpty() || movieFileName == null || movieTrailerURL.isEmpty()) {
            alert.setHeaderText("Error: invalid input fields");
            alert.setContentText("Please fill in all required fields, " + Main.user.getFirstName());

            //if inputs are valid: copies the image to project directory + calls createMovie()
        } else {

            if (chosenFile != null) {

                String fileType = (chosenFile.toString().substring(chosenFile.toString().length() - 4, chosenFile.toString().length()));
                Random rnd = new Random();
                int rndNum = 1000000 + rnd.nextInt(9000000);
                String rndNumPosterName = Integer.toString(rndNum) + fileType;

                // locate the moviesDir
                File directory = new File(".");
                File moviesDirectory = new File(directory.getAbsolutePath(), "movie-images");
                File newMovie = new File(moviesDirectory, rndNumPosterName);

                try {
                    newMovie.createNewFile();
                    Files.copy(chosenFile.toPath(), newMovie.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    movieFileName = rndNumPosterName;
//                System.out.println(newMovie.getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            alert.setHeaderText("Success: movie created");
            alert.setContentText("Your movie was successfully created, " + Main.user.getFirstName());
//            PauseTransition delay = new PauseTransition(Duration.seconds(4));
//            delay.setOnFinished(e -> popup.hide());
//            popup.show();
//            delay.play();

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

        //adds the newly created movie to the database
        FilmDAO.insertFilm(title, description, movieFileName, movieTrailerURL);

        //resets input fields to default + updates moviesTable & movieSelectionBox + resets chosenFile to null
        addTitle.clear();
        addDescription.clear();
        trailerURL.clear();
        chosenFile = null;

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

        //get input values
        movieTitle = movieSelectionBox.getSelectionModel().isEmpty() ? null :  String.valueOf(movieSelectionBox.getValue());
        screeningTime = String.valueOf(timePicker.getValue());
        screeningDate = String.valueOf(datePicker.getValue());
        Date now = new Date();

        // convert the dateTime to the correct format
        try {
            String dateTimeString = screeningDate + " " + screeningTime;
            dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dateTimeString);
        } catch (ParseException e) {
            // date is noted as invalid
            LOGGER.logp(Level.INFO, "CustomerBookingController",
                    "validateScreening", "unable to parse date as invalid input. See Google exception handling style guide." + e);
        }
        //perform validity checks: empty & dateSelected < now
        if (movieTitle != null && screeningTime != null && dateTime != null && now.compareTo(dateTime) < 0){
            alert.setHeaderText("Success: screening created");
            alert.setContentText("Your screening was successfully added, " + Main.user.getFirstName());
            createScreening();

        }else if(movieTitle == null || screeningTime == null || dateTime == null) {
            alert.setHeaderText("Error: invalid input fields");
            alert.setContentText("Please fill in all required fields, " + Main.user.getFirstName());
        }else if(now.compareTo(dateTime) > 0){
            alert.setHeaderText("Error: screening date in the past");
            alert.setContentText("Please select a future screening date, " + Main.user.getFirstName());

        }

//
//
//
//        if (movieTitle == null || screeningTime == null || dateTime == null) {
//            alert.setHeaderText("Error: invalid input fields");
//            alert.setContentText("Please fill in all required fields, " + Main.user.getFirstName());
//        }
//        else if(now.compareTo(dateTime) > 0){
//            alert.setHeaderText("Error: screening date in the past");
//            alert.setContentText("Please select a future screening date, " + Main.user.getFirstName());
//
//        }
//        else {
//            alert.setHeaderText("Success: screening created");
//            alert.setContentText("Your screening was successfully added, " + Main.user.getFirstName());
//
//            createScreening();
//        }

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

        //access input values & create date-time
        String date = dateTime.toString();
        film = (Film) movieSelectionBox.getSelectionModel().getSelectedItem();
        int movieID = film.getId();

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
        deleteScreening.setDisable(false);
    }


    @FXML
    private void deleteScreening() {

        boolean noBookingsForScreening = false;

        try {

            Booking booking = BookingDAO.getBookingsByScreeningId(selectedScreeningId);

            if(booking == null) {
                noBookingsForScreening = true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.logp(Level.WARNING, "EmployeeHomeController", "deleteScreening", "Failed to get Bookings with screening id from screeningTable. See: " + e);
            e.printStackTrace();
        }

        if(!noBookingsForScreening){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            Stage popup = (Stage) alert.getDialogPane().getScene().getWindow();
            alert.setTitle("Cinego");
            popup.getIcons().add(new Image(this.getClass().getResource("/resources/cinestar.png").toString()));
            alert.setHeaderText("Warning: screening is booked");
            alert.setContentText("You can't delete a screening with existing bookings, " + Main.user.getFirstName() + " !");
            alert.showAndWait();
        }
        else{
            //Prompts user to confirm deleting the selected screening
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            Stage popup = (Stage) alert.getDialogPane().getScene().getWindow();
            alert.setTitle("Cinego");
            popup.getIcons().add(new Image(this.getClass().getResource("/resources/cinestar.png").toString()));
            alert.setHeaderText("Warning: deleting screening");
            alert.setContentText("Are you sure you want to delete this screening, " + Main.user.getFirstName() + " ?");
//            alert.showAndWait();

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



    @FXML
    private void getMovieID(){
        selectedMovieId = moviesTable.getSelectionModel().getSelectedItem().getId();
        System.out.println("\n\n\n"+selectedMovieId+"\n\n\n");
        editMovieButton.setDisable(false);
    }


    @FXML
    private void editMovie() {

        //enable and disable buttons
        createMovieButton.setDisable(true);
        updateMovieButton.setDisable(false);

//        try {
//            movieValidation();
//        } catch (SQLException | ClassNotFoundException e) {
//            LOGGER.logp(Level.WARNING, "EmployeeHomeController", "editMovie", "Input field validation returns a null value. See: " + e);
//            e.printStackTrace();
//        }

        //fill components with movie's data
        try {
            Film film = FilmDAO.getFilmById(selectedMovieId);
            addTitle.setText(film.getTitle());
            addDescription.setText(film.getDescription());
            trailerURL.setText(film.getTrailerURL());

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            LOGGER.logp(Level.WARNING, "EmployeeHomeController", "editMovie", "Failed to load movie's data into film object. See: " + e);
        }
    }

   @FXML
   private void updateMovie(){

        //read out and store new values from components if changed
       title = addTitle.getText();
       description = addDescription.getText();
       movieTrailerURL = trailerURL.getText();

       try {
           FilmDAO.updateMovieDetails(title, description,movieTrailerURL,selectedMovieId);
       } catch (SQLException | ClassNotFoundException e) {
           LOGGER.logp(Level.WARNING, "EmployeeHomeController", "updateMovie", "Failed to update database with edited movie data. See: " + e);
           e.printStackTrace();
       }
       editMovieButton.setDisable(true);
       updateMovieButton.setDisable(true);
       createMovieButton.setDisable(false);


       addTitle.clear();
       addDescription.clear();
       trailerURL.clear();

//       custFirstNameField.setPromptText(Main.user.getFirstName());
//       custFirstNameField.setEditable(textFieldEditable);

       populateMoviesTable();
       populateScreeningsTable();

   }
}
