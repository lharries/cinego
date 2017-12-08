package application;

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
import javafx.stage.Window;
import models.Film;
import models.FilmDAO;
import models.Screening;
import models.ScreeningDAO;

import javax.imageio.ImageIO;
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



    @FXML
    private void uploadMovieImage(Event event){

//        source: http://java-buddy.blogspot.co.uk/2013/01/use-javafx-filechooser-to-open-image.html
        FileChooser fileChooser = new FileChooser();

        DirectoryChooser directoryChooser = new DirectoryChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

//        DirectoryChooser extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
//        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
//        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);



        //Show open file dialog
//        File file = fileChooser.showOpenDialog(null);


//        try {
//            BufferedImage bufferedImage = ImageIO.read(file);
//            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
//            moviePoster.setImage(image);
//        } catch (IOException ex) {
//            Logger.getLogger(EmployeeHomeController.class.getName()).log(Level.SEVERE, null, ex);
//        }

//        Window window = new Window();


        BufferedImage image = null;
        File chosenFile = null;
        File file = null;
        String filename = "kaiTest.jpg";
        String path = null;


        //TODO: myFileChooser.getSelectedFile().getAbsolutePath()

        //read image file
        try{
            chosenFile = fileChooser.showOpenDialog(null);
            path = chosenFile.getAbsolutePath();
            file = new File(path);
//            path = directoryChooser.showDialog(Main.primaryStage).toString();

            image = ImageIO.read(file);
        }catch(IOException e){
            System.out.println("Error: "+e);
        }

        //write image
        try{
            file = new File("/Users/kai/code/cinego/src/resources" + filename);
            ImageIO.write(image, "jpg", file);
        }catch(IOException e){
            System.out.println("Error: "+e);
        }


            //source: https://bytes.com/topic/java/answers/828841-how-copy-image-file
//        FileInputStream fis=new FileInputStream(new File("D:/image.bmp"));
//        FileOutputStream fos=new FilePutputStream(new FIle("D:/copyImage.bmp"));
//        int c;
//        while((c=fis.read())!=-1)
//        {
//            fos.write(c);
//        }
//



    }


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
