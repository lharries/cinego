package application;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 *
 *
 */
public class CustomerMovieProgramController {

   //TODO: render the necessary fields for the Movie Program list view table (Title, time, description, IMDB rating, image, trailer)



   @FXML
   private Button toCustProf;


//    private void showProgramMovie(){
//
//    }
//   @FXML
//    public void goToCustAcc(ActionEvent event){
//        Navigation.setCustomerController();
//
//    }


   @FXML
   private void sortMoviesByDate(){
      //TODO: add ability to query movies from database based on the selected date
      //TODO: return list view to only include movie screenings on selected date
   }

   @FXML
   private void sortMoviesByTime() {
      //TODO: add ability to query movies from database based on the selected date
      //TODO: return list view to only include movie screenings on selected date
   }

   @FXML
   private void filterMoviesBySearch(){
      //TODO: add movie filter based on the input search string
      //TODO: ensure that search queries all attributes of table (title, description, etc.) and returns selection live while typing
   }



}
