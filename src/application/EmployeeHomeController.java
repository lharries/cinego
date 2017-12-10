package application;

import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmployeeHomeController implements Initializable {

    //TODO IMPORTANT: disable past dates + taken timeslots for creating a screening!


    //TODO: add loggers to the validation for us

    //TODO: ASK LUKE ABOUT THE CORRECT DATE / TIME FORMAT
    //TODO: add tooltips to buttons in order to convey additional information w.r.t. their functionality source: https://stackoverflow.com/questions/25338873/is-there-a-simple-way-to-display-hint-texts-in-javafx



    private static final Logger LOGGER = Logger.getLogger(CustomerRootController.class.getName());

    @FXML
    private Button toSeatBooking;

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
    private TableColumn titleCol,urlCol,descriptCol, titleColScreenTab, dateColScreenTab, timeColScreenTab;

    @FXML
    private TextField addTitle, addDescription;

    @FXML
    private ComboBox movieSelectionBox, timePicker;

    @FXML
    private DatePicker datePicker;

    //reused variables in validation and creation of movies and screenings
    private String title, path,relativePath, description, screeningTime, screeningDate, movieTitle;
    private Film film;
    public static int screenID;



    /**
     * Purpose: sets the column titles of both tables and populates them with data from the database,
     * intialises the movieSelectionBox with the latest movies and renderes the background Image of the view
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //set moviesTable headers - 'moviesTable' + populates table
        titleCol.setCellValueFactory(new PropertyValueFactory<Film, String>("title"));
        urlCol.setCellValueFactory(new PropertyValueFactory<Film, String>("imagePath"));
        descriptCol.setCellValueFactory(new PropertyValueFactory<Film, String>("description"));
        populateMoviesTable();

        //set screeningTable headers - 'screeningsTable' + populates movieTable & movieSelectBox
        titleColScreenTab.setCellValueFactory(new PropertyValueFactory<Screening, String>("filmTitle"));
        dateColScreenTab.setCellValueFactory(new PropertyValueFactory<Screening, String>("date"));
        timeColScreenTab.setCellValueFactory(new PropertyValueFactory<Screening, String>("description"));
        populateScreeningsTable();
        populateMovieSelectBox();

    }


    /**
     *Purpose: allows employee to upload a movie poster by copying the image into a local file and naming it
     * after the chosen movie title
     * Sources:
     *  - http://java-buddy.blogspot.co.uk/2013/01/use-javafx-filechooser-to-open-image.html
     *  - https://www.dyclassroom.com/image-processing-project/how-to-read-and-write-image-file-in-java
     */
    @FXML
    private void uploadMovieImage(Event event){

        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

        BufferedImage image = null;
        File chosenFile = null;
        File file = null;
        String filename = addTitle.getText();

        //read image file
        try{
            chosenFile = fileChooser.showOpenDialog(null);
            this.path = chosenFile.getAbsolutePath();
            file = new File(this.path);
            image = ImageIO.read(file);
        }catch(IOException e){
            System.out.println("Error: "+e);
        }

        //write image to relative project path
        try{
            relativePath = "src/resources/" + filename+".jpg";
            file = new File(relativePath);
            ImageIO.write(image, "jpg", file);
        }catch(IOException e){
            System.out.println("Error: "+e);
        }
    }

    /**
     * Purpose: validates user input before creating a movie
     * Informs the user of outcome of test and only creates the movie when input is correct
     *
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

        if(title.isEmpty() || description.isEmpty() || path.isEmpty()){
            alert.setHeaderText("Error: invalid input fields");
            alert.setContentText("Please fill in all required fields, "+ Main.user.getFirstName());
            PauseTransition delay = new PauseTransition(Duration.seconds(4));
            delay.setOnFinished(e -> popup.hide());
            popup.show();
            delay.play();
        } else {
            alert.setHeaderText("Success: movie created");
            alert.setContentText("Your movie was successfully created, "+ Main.user.getFirstName());
            PauseTransition delay = new PauseTransition(Duration.seconds(4));
            delay.setOnFinished(e -> popup.hide());
            popup.show();
            delay.play();

            createMovie();
        }
    }

    /**
     *
     * Purpose: allows user to also change to movie creation view from within his scene
     */
    @FXML
    private void createMovie() throws SQLException, ClassNotFoundException {

        //store input in local variables to used for TableView and database input
        title = addTitle.getText();
        description = addDescription.getText();

        //adds the newly created movie to the database
        FilmDAO.insertFilm(title,description,relativePath);

        //resets input fields to default + updates moviesTable & movieSelectionBox
        addTitle.clear();
        addDescription.clear();
        populateMoviesTable();
        populateMovieSelectBox();
    }

    /**
     * Purpose: tests if input variables to create a screening are correct.
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
        movieTitle = movieSelectionBox.getValue()+"";
        screeningTime = timePicker.getValue()+"";
        screeningDate = datePicker.getValue()+"";
        if(movieTitle.equals("null") || screeningTime.equals("null") || screeningDate.equals("null")){
            alert.setHeaderText("Error: invalid input fields");
            alert.setContentText("Please fill in all required fields, "+ Main.user.getFirstName());
            PauseTransition delay = new PauseTransition(Duration.seconds(4));
            delay.setOnFinished(e -> popup.hide());
            popup.show();
            delay.play();
        } else {
            alert.setHeaderText("Success: screening created");
            alert.setContentText("Your screening was successfully added, "+ Main.user.getFirstName());
            PauseTransition delay = new PauseTransition(Duration.seconds(4));
            delay.setOnFinished(e -> popup.hide());
            popup.show();
            delay.play();

            createScreening();
        }
    }


    /**
     * Purpose: create a new
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @FXML
    private void createScreening() throws SQLException, ClassNotFoundException {

        //TODO: input validation - only when all three fields used + correct input then activate button

        //access input values & create date-time
        String date = screeningDate+" "+screeningTime;
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
     * Purpose: exports list of screening data to directory: "../cinego/ScreeningsExport.csv"
     * - Source: https://community.oracle.com/thread/2397100
     *
     * @throws IOException
     */
    @FXML
    private void exportToCSV() throws IOException {

        //TODO: add following data to csv export: movie title, dates, times and number of booked and available seats.

        //writes data into .csv file
        Writer writer = null;
        try {
            File file = new File("../cinego/ScreeningsExport.csv");
            writer = new BufferedWriter(new FileWriter(file));
            for (Screening Screening: screeningsData) {
                String text = Screening.getId() + "," + Screening.getFilmId() + "," + Screening.getDate() + "\n";
                writer.write(text);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            writer.flush();
            writer.close();
        }
    }

    /**
     * Extracts the screeningsID upon selecting a screening from the screeningsTable
     *
     */
    @FXML
    private void getScreeningID(){
        screenID = screeningsTable.getSelectionModel().getSelectedItem().getId();
        toSeatBooking.setDisable(false);
    }

    @FXML
    private void openSeatsBooked(ActionEvent event) {
        //TODO: open a movie's specific "seats booked overview" +

        EmployeeRootController emplRootController = new EmployeeRootController();
        emplRootController.openBookingView(event);

        toSeatBooking.setDisable(true);
    }


    @FXML
    private void deleteScreening(){
        //TODO: add ability to delete screening or edit screening unless customers have booked a ticket for the movie
    }

    /**
     * Purpose: updates the moviesTable with movie specific data from the database
     */
    private void populateMoviesTable(){
        try {
            moviesData = FilmDAO.getFilmObservableList();
            moviesTable.setItems(moviesData);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Purpose: updates the screeningsTable with screening specific data from the database
     */
    private void populateScreeningsTable(){
        try {
            screeningsData = ScreeningDAO.getScreeningObservableList();
            screeningsTable.setItems(screeningsData);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Purpose: updates the movieSelectionBox with the latest Movie Titles from the database
     */
    private void populateMovieSelectBox(){
        try {
            movieSelectionBox.getItems().addAll(FilmDAO.getFilmObservableList());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
