package application;

import controllers.CustomerRootController;
import controllers.EmployeeRootController;
import javafx.fxml.FXMLLoader;

import java.io.IOException;


/**
 *
 *
 *
 */
public class Navigation {

    /**
     * Constants locating the FXML files representing the different views
     */
    //root views into which the below scenes are loaded
    public static final String CUST_ROOT = "/views/CustomerRoot.fxml";
    public static final String EMPL_ROOT = "/views/EmployeeRoot.fxml";

    //user scenes
    public static final String CUST_PROFILE_VIEW = "/views/CustomerProfile.fxml";
    public static final String CUST_PROGRAM_VIEW = "/views/CustomerMovies.fxml";
    public static final String CUST_BOOKING_VIEW = "/views/CustomerBooking.fxml";
    public static final String CUST_PAYMENTS_VIEW = "/views/CustomerPayments.fxml";

    //employee scenes
    public static final String EMPL_HOME_VIEW = "/views/EmployeeHome.fxml";
    public static final String EMPL_MOVIE_VIEW = "/views/EmployeeMovieForm.fxml";
    public static final String EMPL_BOOKING_VIEW = "/views/EmployeeBooking.fxml";

    //Main controller for each user's (user & employee) specific rootViews
    private static CustomerRootController customerController;
    private static EmployeeRootController employeeController;


    /**
     * sets the user controller
     *
     * @param customerController customerController
     */
    public static void setCustomerController(CustomerRootController customerController){
        Navigation.customerController = customerController;
    }

    /**
     * sets the user controller
     *
     * @param employeeController employeeController
     */
    public static void setEmployeeController(EmployeeRootController employeeController){
        Navigation.employeeController = employeeController;
    }

    /**
     * load the user fxml views into the user's Borderpane
     *
     * @param fxmlView fxmlView
     * @throws IOException IOException
     */
    public static void loadCustFxml(String fxmlView) throws IOException {
        customerController.setCenter(FXMLLoader.load(Navigation.class.getResource(fxmlView)));
    }

    /**
     * load the employee fxml views into the employee's Borderpane
     *
     * @param fxmlView fxmlView
     * @throws IOException IOException
     */
    public static void loadEmplFxml(String fxmlView) throws IOException {
        employeeController.setCenter(FXMLLoader.load(Navigation.class.getResource(fxmlView)));
    }


}
