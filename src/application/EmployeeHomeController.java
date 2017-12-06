package application;


import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import models.Film;
import models.FilmDAO;
import models.Screening;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class EmployeeHomeController implements Initializable {

    //TODO: add tooltips to buttons in order to convey additional information w.r.t. their functionality
    //TODO: source: https://stackoverflow.com/questions/25338873/is-there-a-simple-way-to-display-hint-texts-in-javafx

    @FXML
    private Button CreateMovieButton;

    @FXML
    private ImageView backgroundImg;

    @FXML
    private AnchorPane AnchorPane;

    @FXML
    private TableView ScreeningsTable;

    @FXML
    private TableView<Film> moviesTable, screeningsTable;

    @FXML
    private ObservableList<Film> moviesData, screeningsData;

    @FXML
    private HBox hBox;

    @FXML
    private TableColumn titleCol,urlCol,descriptCol, titleColScreenTab, dateColScreenTab, timeColScreenTab, bookingColScreenTab;

    @FXML
    private TextField addTitle, addImagePath, addDescription;




//    @FXML
//    private TextArea addDescription;


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        //TODO populate list with movies from the database -> SELECT query to insert DB movies into moviesTable
        //set moviesTable headers - 'moviesTable'
        titleCol.setCellValueFactory(new PropertyValueFactory<Film, String>("title"));
        urlCol.setCellValueFactory(new PropertyValueFactory<Film, String>("imagePath"));
        descriptCol.setCellValueFactory(new PropertyValueFactory<Film, String>("description"));

        //populate moviesTable from database - 'screeningsTable'
        try {
            moviesData = FilmDAO.getFilmObservableList();
            moviesTable.setItems(moviesData);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }




        //set moviesTable headers - 'screeningsTable'
        titleColScreenTab.setCellValueFactory(new PropertyValueFactory<Film, String>("title"));
        dateColScreenTab.setCellValueFactory(new PropertyValueFactory<Screening, String>("date"));
//        timeColScreenTab.setCellValueFactory(new PropertyValueFactory<Screening, String>("description"));
        bookingColScreenTab.setCellValueFactory(new PropertyValueFactory<Film, String>("description"));

        //populate moviesTable from database - 'screeningsTable'

//        try {
//            moviesData = FilmDAO.getFilmObservableList();
//            moviesTable.setItems(moviesData);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }






        //render background image
        BufferedImage bufferedBackground = null;
        try {
            bufferedBackground = ImageIO.read(new File("src/resources/cinWallpaper.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image background = SwingFXUtils.toFXImage(bufferedBackground, null);
        this.backgroundImg.setImage(background);
    }


    /**
     *
     * Purpose: allows user to also change to movie creation view from within his scene
     */
    @FXML
    private void createMovie() throws SQLException, ClassNotFoundException {

        //TODO: add input validation (e.g. ID must be an int otherwise it can't be converter to Integer

        //store input in local variables to used for TableView and database input
        String title = addTitle.getText();
        String imagePath = addImagePath.getText();
        String description = addDescription.getText();

        //adds the newly created movie to the TableView
        addTitle.clear();
        addImagePath.clear();
        addDescription.clear();

        //adds the newly created movie to the database
        FilmDAO.insertFilm(title,imagePath,description);

        //TODO: should we delete the other "moviecreation view"? If yes -> below code to switch to view unnecessary
        //takes user to new view but possibly do without it
//        EmployeeRootController emplRootController = new EmployeeRootController();
//        emplRootController.openMovieFormView(event);
    }

    @FXML
    private void exportToCSV(){
        //TODO: add exporting to CSV functionality (Button triggers downloading all current movies including titles, seats booked etc.)
    }

    @FXML
    private void openSeatsBooked(ActionEvent event) {
        //TODO: open a movie's specific "seats booked overview" +

        EmployeeRootController emplRootController = new EmployeeRootController();
        emplRootController.openBookingView(event);
    }
}
