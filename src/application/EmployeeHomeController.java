package application;

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
import javafx.util.converter.DateTimeStringConverter;
import models.Film;
import models.FilmDAO;
import models.Screening;
import models.ScreeningDAO;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
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

    private String title, path, relativePath, description, screeningTime, screeningDate, movieTitle;
    Film film;
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
        BufferedImage image = null;

        File file = null;
        String filename = addTitle.getText();

        try {
            //read image file
            File chosenFile = fileChooser.showOpenDialog(null);
            this.path = chosenFile.getAbsolutePath();
            file = new File(this.path);
            image = ImageIO.read(file);

            // TODO: Switch this to a randomly generated string
            //write image to relative project path
            relativePath = "src/resources/" + filename + ".jpg";
            file = new File(relativePath);
            // TODO: Be careful with the file extensions
            ImageIO.write(image, "jpg", file);
            relativePath = "/resources/" + filename + ".jpg";
        } catch (IOException e) {
            System.out.println("Error: " + e);
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
        title = addTitle.getText() + "";
        description = addDescription.getText() + "";

        System.err.println("\n\n\n" + path + "\n\n\n");


        if (title.isEmpty() || description.isEmpty() || path.isEmpty()) {
            alert.setHeaderText("Error: invalid input fields");
            alert.setContentText("Please fill in all required fields, " + Main.user.getFirstName());
            PauseTransition delay = new PauseTransition(Duration.seconds(4));
            delay.setOnFinished(e -> popup.hide());
            popup.show();
            delay.play();
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
        FilmDAO.insertFilm(title, description, relativePath);

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

        movieTitle = movieSelectionBox.getValue() + "";
        screeningTime = timePicker.getValue() + "";
        screeningDate = datePicker.getValue() + "";
        if (movieTitle.equals("null") || screeningTime.equals("null") || screeningDate.equals("null")) {
            alert.setHeaderText("Error: invalid input fields");
            alert.setContentText("Please fill in all required fields, " + Main.user.getFirstName());
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
        String date = screeningDate + " " + screeningTime;
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
     * Exports a list of relevant screening data to directory: "../cinego/ScreeningsExport.csv"
     * - Source: https://community.oracle.com/thread/2397100
     *
     * @throws IOException
     */
    @FXML
    private void exportToCSV() throws IOException {

        //TODO: add following data to csv export: movie title, dates, times and number of booked and available seats.

        //writes data into .csv file
        Writer writer = null;
        File file;
        file = new File("../cinego/ScreeningsExport.csv");
        try {
            writer = new BufferedWriter(new FileWriter(file));
            String text = "id" + "," + "filmId" + "," + "date" + "\n";
            writer.write(text);

            for (Screening Screening : screeningsData) {
                text = Screening.getId() + "," + Screening.getFilmId() + "," + Screening.getDate() + "\n";
                writer.write(text);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            writer.flush();
            writer.close();
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file);
            }
        }
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

        //TODO: add ability to delete screening or edit screening unless customers have booked a ticket for the movie

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
