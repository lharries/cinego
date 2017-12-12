package controllers;

import application.Main;
import javafx.animation.PauseTransition;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.*;
import utils.CSVUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The EmployeeHomeController implements functionality specific to the EmployeeHome.fxml file which is part
 * of the cinema booking system - 'Cinego'.
 *
 * @author lukeharries, kaikalsen
 * @version 1.0
 */
public class EmployeeHomeController implements Initializable {

    //TODO IMPORTANT: disable past dates + taken timeslots for creating a screening!


    //TODO: add loggers to the validation for us

    //TODO: ASK LUKE ABOUT THE CORRECT DATE / TIME FORMAT
    //TODO: add tooltips to buttons in order to convey additional information w.r.t. their functionality References: https://stackoverflow.com/questions/25338873/is-there-a-simple-way-to-display-hint-texts-in-javafx

    @FXML
    private Button deleteBooking;

    @FXML
    private Button toSeatBooking, deleteScreening, editFilmButton, updateFilmButton, createFilmButton;

    @FXML
    private TableView<Film> filmsTable;

    @FXML
    private TableView<Screening> screeningsTable;

    @FXML
    private ObservableList<Film> filmObservableList;

    @FXML
    private ObservableList<Screening> screeningsData;

    @FXML
    private TableColumn titleCol, descriptCol, titleColScreenTab, dateColScreenTab;

    @FXML
    private TextField titleTextField, trailerURLTextField;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private ComboBox movieSelectionBox, timePicker;

    @FXML
    private DatePicker datePicker;

    private String title, filmImageName, description, screeningTime, screeningDate, movieTitle, movieTrailerURL;
    private Date dateTime;
    public static Integer selectedScreeningId, selectedFilmId;

    private static final Logger LOGGER = Logger.getLogger(CustomerRootController.class.getName());


    /**
     * Sets the column titles of both tables and populates them with data from the database,
     * intialises the movieSelectionBox with the latest movies and renderes the background Image of the view
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //set filmsTable headers - 'filmsTable' + populates table
        titleCol.setCellValueFactory(new PropertyValueFactory<Film, String>("title"));
        descriptCol.setCellValueFactory(new PropertyValueFactory<Film, String>("description"));
        populateFilmsTable();

        //set screeningTable headers - 'screeningsTable' + populates movieTable & movieSelectBox
        titleColScreenTab.setCellValueFactory(new PropertyValueFactory<Screening, String>("filmTitle"));
        dateColScreenTab.setCellValueFactory(new PropertyValueFactory<Screening, String>("date"));
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

            String fileType = (chosenFile.toString().substring(chosenFile.toString().length() - 4, chosenFile.toString().length()));
            Random rnd = new Random();
            int rndNum = 1000000 + rnd.nextInt(9000000);
            filmImageName = Integer.toString(rndNum) + fileType;

            // locate the moviesDir
            File directory = new File(".");
            File moviesDirectory = new File(directory.getAbsolutePath(), "movie-images");
            File newMovie = new File(moviesDirectory, filmImageName);

            try {
                newMovie.createNewFile();
                Files.copy(chosenFile.toPath(), newMovie.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
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
    private void filmValidation() throws SQLException, ClassNotFoundException {

        //creates alert to be used in both cases: correct & incorrect inputs
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Stage popup = (Stage) alert.getDialogPane().getScene().getWindow();
        alert.setTitle("Cinego");
        popup.getIcons().add(new Image(this.getClass().getResource("/resources/cinestar.png").toString()));

        //gets the input values and checks if they're correctly filled in
        title = titleTextField.getText();
        description = descriptionTextArea.getText();
        movieTrailerURL = trailerURLTextField.getText();

        if (title.isEmpty() || description.isEmpty() || filmImageName == null || movieTrailerURL.isEmpty()) {
            alert.setHeaderText("Error: invalid input fields");
            alert.setContentText("Please fill in all required fields, " + Main.user.getFirstName());
        } else {
            alert.setHeaderText("Success: movie created");
            alert.setContentText("Your movie was successfully created, " + Main.user.getFirstName());
            createFilm();
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
    private void createFilm() throws SQLException, ClassNotFoundException {

        //adds the newly created movie to the database
        FilmDAO.insertFilm(title, description, filmImageName, movieTrailerURL);

        //resets input fields to default + updates filmsTable & movieSelectionBox
        titleTextField.clear();
        descriptionTextArea.clear();
        trailerURLTextField.clear();
        populateFilmsTable();
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
        movieTitle = String.valueOf(movieSelectionBox.getValue());
        screeningTime = String.valueOf(timePicker.getValue());
        screeningDate = String.valueOf(datePicker.getValue());
        Date now = new Date();

        // convert the dateTime to the correct format
        try {
            String dateTimeString = screeningDate + " " + screeningTime;
            dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dateTimeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //perform validity checks: empty & dateSelected < now
        if (movieTitle == null || screeningTime == null || dateTime == null) {
            alert.setHeaderText("Error: invalid input fields");
            alert.setContentText("Please fill in all required fields, " + Main.user.getFirstName());
        }
        if (now.compareTo(dateTime) > 0) {
            alert.setHeaderText("Error: screening date in the past");
            alert.setContentText("Please select a future screening date, " + Main.user.getFirstName());
            PauseTransition delay = new PauseTransition(Duration.seconds(4));
            delay.setOnFinished(e -> popup.hide());
            popup.show();
            delay.play();
        } else {
            alert.setHeaderText("Success: screening created");
            alert.setContentText("Your screening was successfully added, " + Main.user.getFirstName());
            PauseTransition delay = new PauseTransition(Duration.seconds(4));
            delay.setOnFinished(e -> popup.hide());
            popup.show();
            delay.play();

            createScreening();
        }
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
        Film film = (Film) movieSelectionBox.getSelectionModel().getSelectedItem();
        int filmId = film.getId();

        //adds the newly created screening to the database
        ScreeningDAO.insertScreening(filmId, date);

        //resets input values to default + update screeningTable
        movieSelectionBox.setValue(null);
        timePicker.setValue(null);
        datePicker.setValue(null);
        populateScreeningsTable();
    }

    /**
     * Exports a list of relevant screening and movie statistics to directory: "../cinego/ScreeningsExport.csv"
     * References:  - https://community.oracle.com/thread/2397100
     * - http://csvjdbc.sourceforge.net/
     *
     * @throws IOException
     */
    @FXML
    private void exportData() throws IOException, ClassNotFoundException, SQLException {

        //TODO: additional / extra statistics to CSV file
        CSVUtils.exportToCSV();
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

    /**
     * Enables the employee delete a movie if it currently doesn't have any bookings
     */

    @FXML
    private void deleteScreening() {

        boolean noBookings = false;

        try {
            Booking booking = BookingDAO.getBookingsByScreeningId(selectedScreeningId);
            if (booking == null) {
                noBookings = true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.logp(Level.WARNING, "EmployeeHomeController", "deleteScreening", "Failed to get Bookings with screening id from screeningTable. See: " + e);
            e.printStackTrace();
        }

        if (!noBookings) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            Stage popup = (Stage) alert.getDialogPane().getScene().getWindow();
            alert.setTitle("Cinego");
            popup.getIcons().add(new Image(this.getClass().getResource("/resources/cinestar.png").toString()));
            alert.setHeaderText("Warning: screening is booked");
            alert.setContentText("You can't delete a screening with existing bookings, " + Main.user.getFirstName() + " !");
            alert.showAndWait();
        } else {
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
     * Updates the filmsTable with movie specific data from the database
     */
    private void populateFilmsTable() {
        try {
            filmObservableList = FilmDAO.getFilmObservableList();
            filmsTable.setItems(filmObservableList);

            filmObservableList.addListener((ListChangeListener<Film>) c -> {
                filmsTable.setItems(filmObservableList);
            });
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            LOGGER.logp(Level.WARNING, "EmployeeHomeController", "populateFilmsTable", "Failed to load data to populate the movies table. See: " + e);
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
            movieSelectionBox.getItems().clear();
            movieSelectionBox.getItems().addAll(FilmDAO.getFilmObservableList());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            LOGGER.logp(Level.WARNING, "EmployeeHomeController", "populateMovieSelectionBox", "Failed to load data to populate the movieSelectionBox. See: " + e);
        }
    }

    /**
     * Retrieves the filmId of the selected film object in the film table (not screening table)
     */
    @FXML
    private void filmRowClickedHandler() {
        Film selectedFilm = filmsTable.getSelectionModel().getSelectedItem();

        if (selectedFilm != null) {
            selectedFilmId = selectedFilm.getId();
            editFilmButton.setDisable(false);
            createFilmButton.setDisable(false);
            updateFilmButton.setDisable(true);

            titleTextField.clear();
            descriptionTextArea.clear();
            trailerURLTextField.clear();
            filmImageName = null;

        }
    }

    /**
     * Allows the employee to edit any selected film. This method fills the input fields with the previous data and
     * thereby enabling the employee to edit the details.
     */
    @FXML
    private void editFilmBtnHandler() {

        //enable and disable buttons
        createFilmButton.setDisable(true);
        editFilmButton.setDisable(true);
        updateFilmButton.setDisable(false);

        //fill components with movie's data
        try {
            Film film = FilmDAO.getFilmById(selectedFilmId);
            titleTextField.setText(film.getTitle());
            descriptionTextArea.setText(film.getDescription());
            trailerURLTextField.setText(film.getTrailerURL());
            filmImageName = film.getImageName();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            LOGGER.logp(Level.WARNING, "EmployeeHomeController", "editFilmBtnHandler", "Failed to load movie's data into film object. See: " + e);
        }
    }


    /**
     * Upon "updating" a film's data, the changed data is being pushed into the database
     * to ensure data consistency across user sessions.
     */
    @FXML
    private void updateFilmBtnHandler() {

        //read out and store new values from components if changed
        title = titleTextField.getText();
        description = descriptionTextArea.getText();
        movieTrailerURL = trailerURLTextField.getText();

        try {
            FilmDAO.updateFilm(title, description, filmImageName, movieTrailerURL, selectedFilmId);
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.logp(Level.WARNING, "EmployeeHomeController", "updateFilmBtnHandler", "Failed to update database with edited movie data. See: " + e);
            e.printStackTrace();
        }
        editFilmButton.setDisable(true);
        updateFilmButton.setDisable(true);
        createFilmButton.setDisable(false);

        //Inform user of successful updating of movie
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Stage popup = (Stage) alert.getDialogPane().getScene().getWindow();
        alert.setTitle("Cinego");
        popup.getIcons().add(new Image(this.getClass().getResource("/resources/cinestar.png").toString()));
        alert.setHeaderText("Success: updated film");
        alert.setContentText("You have successfully updated the film " + titleTextField.getText() + ", " + Main.user.getFirstName());
        PauseTransition delay = new PauseTransition(Duration.seconds(4));
        delay.setOnFinished(e -> popup.hide());
        popup.show();
        delay.play();

        titleTextField.clear();
        descriptionTextArea.clear();
        trailerURLTextField.clear();

        populateFilmsTable();
        populateScreeningsTable();
        populateMovieSelectionBox();

    }
}
