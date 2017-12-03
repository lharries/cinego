package application;

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
    public static final String CUST_ROOT = "/views/CustomerRoot.fxml";
    public static final String EMPL_ROOT = "/views/EmployeeRoot.fxml";

    //customer views
    public static final String CUST_PROFILE_VIEW = "/views/CustomerProfile.fxml";
    public static final String CUST_PROGRAM_VIEW = "/views/CustomerMovieProgram.fxml";
//    public static final String ORDER_VIEW = "/views/EmpOrderView.fxml";

    //employee views
    public static final String EMPL_HOME_VIEW = "/views/EmployeeHome.fxml";
    public static final String EMPL_MOVIE_FORM = "/views/EmployeeMovieForm.fxml";
//    public static final String MGMT_ORDERS = "/view/MgmtOrdersView.fxml";

    //Main controller for each user's (customer & employee) specific rootViews
    private static CustomerRootController customerController;
    private static EmployeeRootController employeeController;


    /**
     * Purpose: sets the customer controller
     *
     * @param customerController
     */
    public static void setCustomerController(CustomerRootController customerController){
        Navigation.customerController = customerController;
    }

    /**
     * Purpose: sets the customer controller
     *
     * @param employeeController
     */
    public static void setEmployeeController(EmployeeRootController employeeController){
        Navigation.employeeController = employeeController;
    }

    /**
     * Purpose: load the customer fxml views into the customer's Borderpane
     *
     * @param fxmlView
     * @throws IOException
     */
    public static void loadCustFxml(String fxmlView) throws IOException {
        customerController.setCenter(FXMLLoader.load(Navigation.class.getResource(fxmlView)));
    }

    /**
     * Purpose: load the employee fxml views into the employee's Borderpane
     *
     * @param fxmlView
     * @throws IOException
     */
    public static void loadEmplFxml(String fxmlView) throws IOException {
        employeeController.setCenter(FXMLLoader.load(Navigation.class.getResource(fxmlView)));
    }


}
