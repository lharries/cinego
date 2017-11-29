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
    public static final String CUST_ROOT = "/view/customerRoot.fxml";
//    public static final String EMPL_ROOT = "/view/MgmtRootView.fxml";

    public static final String TABLE_VIEW = "/view/EmpTableView.fxml";
//    public static final String EMP_ORDERS_VIEW = "/view/EmpOrdersView.fxml";
//    public static final String ORDER_VIEW = "/view/EmpOrderView.fxml";
//
//    public static final String MGMT_EMPLOYEES = "/view/MgmtEmployeesView.fxml";
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
     * Purpose: load the employee fxml views into the employee's Borderpane
     *
     * @param fxmlView
     * @throws IOException
     */
    public static void loadEmplFxml(String fxmlView) throws IOException {
        emplController.setCenter(FXMLLoader.load(Navigation.class.getResource(fxmlView)));
    }



}
