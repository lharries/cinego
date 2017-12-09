package application;

import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
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
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
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

    //TODO: add file uploader to load movie images into project directory


    //TODO: add tooltips to buttons in order to convey additional information w.r.t. their functionality source: https://stackoverflow.com/questions/25338873/is-there-a-simple-way-to-display-hint-texts-in-javafx


    @FXML
    private Button CreateMovieButton;

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
    private TableColumn titleCol,urlCol,descriptCol, titleColScreenTab, dateColScreenTab, timeColScreenTab, bookingColScreenTab;

    @FXML
    private TextField addTitle, addImagePath, addDescription;

    @FXML
    private ComboBox movieSelectionBox, timePicker;

    @FXML
    private DatePicker datePicker;


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
        bookingColScreenTab.setCellValueFactory(new PropertyValueFactory<Screening, String>("description"));
        populateScreeningsTable();
        populateMovieSelectBox();


        //render background image
//        BufferedImage bufferedBackground = null;
//        try {
//            bufferedBackground = ImageIO.read(new File("src/resources/cinWallpaper.png"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Image background = SwingFXUtils.toFXImage(bufferedBackground, null);
//        this.backgroundImg.setImage(background);
    }

    /**
     *
     * Purpose: allows user to also change to movie creation view from within his scene
     */
    @FXML
    private void createMovie() throws SQLException, ClassNotFoundException {

        //TODO: add image uploader to Movie Creation process and pass on it's url to rest of program

        //TODO: add input validation (e.g. ID must be an int otherwise it can't be converter to Integer

        //store input in local variables to used for TableView and database input
        String title = addTitle.getText();
        String imagePath = addImagePath.getText();
        String description = addDescription.getText();



        //adds the newly created movie to the database
        FilmDAO.insertFilm(title, imagePath, description);

        //resets input fields to default + updates moviesTable & movieSelectionBox
        addTitle.clear();
        addImagePath.clear();
        addDescription.clear();
        populateMoviesTable();
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
        String path = null;

        //TODO: add exception Handler if employee presses cancel and doesn't select an image -> THROWS error

        //read image file
        try{
            chosenFile = fileChooser.showOpenDialog(null);
            if(chosenFile == null){
                System.err.println("no image chosen!");

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                Stage popup = (Stage) alert.getDialogPane().getScene().getWindow();
                popup.getIcons().add(new Image(this.getClass().getResource("/resources/cinestar.png").toString()));
                alert.setTitle("Cinego");
                alert.setHeaderText("Error: failed to upload image");
                alert.setContentText("Please select an image, "+ Main.user.getFirstName());
                PauseTransition delay = new PauseTransition(Duration.seconds(2));
                delay.setOnFinished(e -> popup.hide());
                popup.show();
                delay.play();

                JOptionPane.showMessageDialog(null, "PLEASE SELECT 1 FILE!", "Error", JOptionPane.ERROR_MESSAGE);
            }else{
                path = chosenFile.getAbsolutePath();
                file = new File(path);
                image = ImageIO.read(file);

                //write image to relative project path
                try{
                    file = new File("src/resources/" + filename+".jpg");
                    ImageIO.write(image, "jpg", file);
                }catch(IOException e){
                    System.out.println("Error: "+e);
                }
            }
        }catch(IOException e){
            System.out.println("Error: "+e);
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

        //TODO: new screening in screeningTable should include a button linking to screenings specific employeeBookingView ask @LUKE
        //TODO: input validation - only when all three fields used + correct input then activate button

        //access input values & create date-time
        String screeningTime = timePicker.getValue().toString();
        String screeningDate = datePicker.getValue().toString();
        String date = screeningDate+" "+screeningTime;
        Film film = (Film) movieSelectionBox.getSelectionModel().getSelectedItem();
        int movieID = film.getId();
        String movieTitle = film.getTitle();

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
     *
     * @throws IOException
     */
    @FXML
    private void exportToCSV() throws IOException {

        //source: https://community.oracle.com/thread/2397100
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


    @FXML
    private void openSeatsBooked(ActionEvent event) {
        //TODO: open a movie's specific "seats booked overview" +

        EmployeeRootController emplRootController = new EmployeeRootController();
        emplRootController.openBookingView(event);
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
