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
    public static final String CUST_ACC_VIEW = "/views/CustomerAccount.fxml";
    public static final String CUST_PROG_VIEW = "/views/CustomerProgram.fxml";
//    public static final String ORDER_VIEW = "/views/EmpOrderView.fxml";

    //employee views
    public static final String MGMT_EMPLOYEES = "/views/MgmtEmployeesView.fxml";
//    public static final String MGMT_MENU = "/view/MgmtMenuView.fxml";
//    public static final String MGMT_ORDERS = "/view/MgmtOrdersView.fxml";

    //Main controller for each user's (customer & employee) specific rootViews
    private static CustomerRootController custController;
    private static EmployeeRootController emplController;


    /**
     * Purpose: sets the customer controller
     *
     * @param custController
     */
    public static void setCustController(CustomerRootController custController){
        Navigation.custController = custController;
    }

    /**
     * Purpose: sets the customer controller
     *
     * @param emplController
     */
    public static void setEmplController(EmployeeRootController emplController){
        Navigation.emplController = emplController;
    }

    /**
     * Purpose: load the customer fxml views into the customer's Borderpane
     *
     * @param fxmlView
     * @throws IOException
     */
    public static void loadCustFxml(String fxmlView) throws IOException {
        custController.setCenter(FXMLLoader.load(Navigation.class.getResource(fxmlView)));
    }

    /**
     * Purpose: load the employee fxml views into the employee's Borderpane
     *
     * @param fxmlView
     * @throws IOException
     */
    public static void loadEmplFxml(String fxmlView) throws IOException {
        emplController.setCenter(FXMLLoader.load(Navigation.class.getResource(fxmlView)));
    }


}
