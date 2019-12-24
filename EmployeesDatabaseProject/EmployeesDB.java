package EmployeesDatabaseProject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.*;
import java.sql.*;

public class EmployeesDB {
    // FIELDS
    private static final String DB_NAME = "employeesDb.db"; // Name of database
    private static final String CONNECTION_STRING = "jdbc:sqlite:C:\\Users\\ /*Input customized path of database*/" + DB_NAME;
    ////////////////////////////////////EMPLOYEE QUERIES AND SQL STATEMENTS/////////////////////////////////////////////
    private static final String QUERY_EMPLOYEES_TABLE = "SELECT * FROM employees";
    private static final String ADD_EMPLOYEES_SQL = "INSERT INTO employees (socialSecurityNumber, firstName, lastName, birthday, " +
            "employeeType, departmentName) VALUES (?,?,?,?,?,?)";
    private static final String DELETE_EMPLOYEE_SQL = "DELETE FROM employees WHERE employees.socialSecurityNumber = ?";
    //////////////////////////////////SALARIED EMPLOYEES AND SQL STATEMENTS/////////////////////////////////////////////
    private static final String QUERY_SALARIED_EMPLOYEES_TABLE = "SELECT * FROM salariedEmployees";
    private static final String ADD_SALARIED_EMPLOYEE = "INSERT INTO salariedEmployees(socialSecurityNumber, weeklySalary, bonus)" +
            "VALUES(?,?,?)";
    private static final String DELETE_EMPLOYEE_FROM_SALARIED_EMPLOYEE = "DELETE FROM salariedEmployees WHERE salariedEmployees.socialsecurityNumber = ?";
    ///////////////////////////////////HOURLY EMPLOYEES AND SQL STATEMENTS//////////////////////////////////////////////
    private static final String QUERY_HOURLY_EMPLOYEES_TABLE = "SELECT * FROM hourlyEmployees";
    private static final String ADD_HOURLY_EMPLOYEE = "INSERT INTO hourlyEmployees(socialSecurityNumber, hours, wage, bonus)" +
            "VALUES(?,?,?,?)";
    private static final String DELETE_EMPLOYEE_FROM_HOURLY_EMPLOYEE = "DELETE FROM hourlyEmployees WHERE hourlyemployees.socialsecurityNumber = ?";
    ///////////////////////////////////COMMISSION EMPLOYEES AND SQL STATEMENTS//////////////////////////////////////////
    private static final String QUERY_COMMISSION_EMPLOYEES_TABLE = "SELECT * FROM commissionEmployees";
    private static final String ADD_COMMISSION_EMPLOYEE = "INSERT INTO commissionEmployees(socialSecurityNumber, grossSales," +
            " commissionRate, bonus)" + "VALUES(?,?,?,?)";
    private static final String DELETE_EMPLOYEE_FROM_COMMISSION_EMPLOYEE = "DELETE FROM commissionEmployees WHERE " +
            "commissionEmployees.socialSecurityNumber = ?";
    /////////////////////////////////////BASE + COMMISSION EMPLOYEES AND SQL STATEMENTS/////////////////////////////////
    private static final String QUERY_BASE_PLUS_COMMISSION_EMPLOYEES_TABLE = "SELECT * FROM basePlusCommissionEmployees";
    private static final String ADD_BASE_PLUS_COMMISSION_EMPLOYEE = "INSERT INTO basePlusCommissionEmployees" +
            "(socialSecurityNumber, grossSales, commissionRate, baseSalary, bonus) VALUES(?,?,?,?,?)";
    private static final String DELETE_EMPLOYEE_FROM_BASE_PLUS_COMMISSION_EMPLOYEE = "DELETE FROM basePlusCommissionEmployees " +
            "WHERE basePlusCommissionEmployees.socialSecurityNumber = ?";
    private static final String QUERY_FOR_EMPLOYEE_TYPE = "SELECT employeeType FROM employees WHERE employees.socialSecurityNumber" +
            "= ?";
    //////////////////////////////////////QUERIES FOR BIRTHDAY BONUS AND RESETTING//////////////////////////////////////
    private static final String SELECT_ALL_BIRTHDAYS = "SELECT socialSecurityNumber, birthday, employeeType FROM employees";
    private static final String UPDATE_SALARIED_EMPLOYEE_BONUS = "UPDATE salariedEmployees SET bonus = ? WHERE socialSecurityNumber = ?";
    private static final String UPDATE_HOURLY_EMPLOYEE_BONUS = "UPDATE hourlyEmployees SET bonus = ? WHERE socialSecurityNumber = ?";
    private static final String UPDATE_COMMISSION_EMPLOYEE_BONUS = "UPDATE commissionEmployees SET bonus = ? WHERE socialSecurityNumber = ?";
    private static final String UPDATE_BASE_PLUS_COMMISSION_EMPLOYEE_BONUS = "UPDATE basePlusCommissionEmployees " +
            "SET bonus = ? WHERE socialSecurityNumber = ?";
    private static final String GET_CURRENT_SALARIED_EMPLOYEE_BONUS_VALUE = "SELECT salariedEmployees.bonus FROM salariedEmployees WHERE socialSecurityNumber = ?";
    private static final String GET_CURRENT_HOURLY_EMPLOYEE_BONUS_VALUE = "SELECT hourlyEmployees.bonus FROM " +
            "hourlyEmployees WHERE socialSecurityNumber = ?";
    private static final String GET_CURRENT_COMMISSION_EMPLOYEE_BONUS_VALUE = "SELECT commissionEmployees.bonus FROM " +
            "commissionEmployees WHERE socialSecurityNumber = ?";
    private static final String GET_GROSS_SALES_CE = "SELECT commissionEmployees.grossSales FROM commissionEmployees WHERE " +
            "socialSecurityNumber = ?";
    private static final String GET_CURRENT_BASE_PLUS_COMMISSION_EMPLOYEE_BONUS_VALUE = "SELECT " +
            "basePlusCommissionEmployees.bonus FROM basePlusCommissionEmployees WHERE socialSecurityNumber = ?";
    private static final String GET_SE_BONUS = "SELECT salariedEmployees.bonus FROM salariedEmployees";
    private static final String GET_HE_BONUS = "SELECT hourlyEmployees.bonus FROM hourlyEmployees";
    private static final String GET_CE_BONUS = "SELECT commissionEmployees.bonus FROM commissionEmployees";
    private static final String GET_BPCE_BONUS = "SELECT basePlusCommissionEmployees.bonus FROM basePlusCommissionEmployees";
    private static final String UPDATE_SE_BONUS = "UPDATE salariedEmployees SET bonus = 0 WHERE bonus > 0";
    private static final String UPDATE_HE_BONUS = "UPDATE hourlyEmployees SET bonus = 0 WHERE bonus > 0";
    private static final String UPDATE_CE_BONUS_NCB = "UPDATE commissionEmployees SET bonus = 0 WHERE grossSales < 10000";
    private static final String UPDATE_CE_BONUS_WCB = "UPDATE commissionEmployees SET bonus = 100 WHERE grossSales > 10000";
    private static final String UPDATE_BPCE_BONUS = "UPDATE basePlusCommissionEmployees SET bonus = 0 WHERE bonus > 0";

    private static final String SELECT_BASE_SALARY = "SELECT baseSalary, socialSecurityNumber FROM " +
            "basePlusCommissionEmployees";
    private static final String UPDATE_BASE_SALARY = "UPDATE basePlusCommissionEmployees SET baseSalary = ? WHERE " +
            "socialSecurityNumber = ?";

    //ALL PREPARED STATEMENTS
    private Connection connection;
    private PreparedStatement queryEmployeesTable;
    private PreparedStatement addEmployees;
    private PreparedStatement deleteEmployee;
    private PreparedStatement querySalariedEmployeesTable;
    private PreparedStatement addSalariedEmployee;
    private PreparedStatement employeeTypeQuery;
    private PreparedStatement deleteFromSalariedEmployee;
    private PreparedStatement queryHourlyEmployeesTable;
    private PreparedStatement addHourlyEmployee;
    private PreparedStatement deleteHourlyEmployee;
    private PreparedStatement queryCommissionEmployeesTable;
    private PreparedStatement addCommissionEmployeesTable;
    private PreparedStatement deleteCommissionEmployees;
    private PreparedStatement queryBasePlusCommissionEmployees;
    private PreparedStatement deleteFromBasePlusCommissionEmployees;
    private PreparedStatement addBasePlusCommissionEmployees;
    private PreparedStatement selectAllBirthdays;
    private PreparedStatement updateSalariedEmployeeBonus;
    private PreparedStatement updateHourlyEmployeeBonus;
    private PreparedStatement updateCommissionEmployeeBonus;
    private PreparedStatement updateBasePlusCommissionEmployeeBonus;
    private PreparedStatement getCurrentSalariedEmployeeBonusValue;
    private PreparedStatement getCurrentHourlyEmployeeBonusValue;
    private PreparedStatement getCurrentCommissionEmployeeBonusValue;
    private PreparedStatement getCurrentBasePlusCommissionEmployeeBonusValue;
    private PreparedStatement getGrossSales;
    private PreparedStatement getSEBonus;
    private PreparedStatement getHEBonus;
    private PreparedStatement getCEBonus;
    private PreparedStatement getBPCEBonus;
    private PreparedStatement updateSEBonus;
    private PreparedStatement updateHEBonus;
    private PreparedStatement updateCEBonusNCB;
    private PreparedStatement updateCEBonusWCB;
    private PreparedStatement updateBPCEBonus;
    private PreparedStatement selectBaseSalary;
    private PreparedStatement updateBaseSalary;

    // COLUMNS FOR
    private TableColumn<Employees, String> SSNColumn = new TableColumn<>("SSN");
    private TableColumn<Employees, String> firstNameColumn = new TableColumn<>("First Name");
    private TableColumn<Employees, String> lastNameColumn = new TableColumn<>("Last Name");
    private TableColumn<Employees, String> birthdayColumn = new TableColumn<>("Birthday");
    private TableColumn<Employees, String> employeeTypeColumn = new TableColumn<>("Employee Type");
    private TableColumn<Employees, String> departmentNameColumn = new TableColumn<>("Department Name");

    private TableColumn<SalariedEmployees, String> SSNColumnSalariedEmployees = new TableColumn<>("SSN");
    private TableColumn<SalariedEmployees, String> weeklySalaryColumn = new TableColumn<>("Weekly Salary");
    private TableColumn<SalariedEmployees, String> bonusColumnSalariedEmployees = new TableColumn<>("Bonus");

    private TableColumn<hourlyEmployees, String> SSNColumnHourlyEmployees = new TableColumn<>("SSN");
    private TableColumn<hourlyEmployees, String> hoursColumn = new TableColumn<>("Hours Worked");
    private TableColumn<hourlyEmployees, String> bonusColumnHourlyEmployees = new TableColumn<>("Bonus");
    private TableColumn<hourlyEmployees, String> wageColumn = new TableColumn<>("Wage");

    private TableColumn<CommissionEmployee, String> SSNColumnCommissionEmployees = new TableColumn<>("SSN");
    private TableColumn<CommissionEmployee, String> grossSalesColumn = new TableColumn<>("Gross Sales");
    private TableColumn<CommissionEmployee, String> commissionRateColumn = new TableColumn<>("Commission Rate");
    private TableColumn<CommissionEmployee, String> bonusColumnCommissionEmployees = new TableColumn<>("Bonus");

    private TableColumn<BasePlusCommissionEmployees, String> SSNColumnbasePlusCommissionEmployees = new TableColumn<>("SSN");
    private TableColumn<BasePlusCommissionEmployees, String> grossSalesColumnbasePlusCommissionEmployees = new TableColumn<>("Gross Sales");
    private TableColumn<BasePlusCommissionEmployees, String> commissionRateColumnbasePlusCommissionEmployees = new TableColumn<>("Commission Rate");
    private TableColumn<BasePlusCommissionEmployees, String> baseSalaryColumn = new TableColumn<>("Base Salary");
    private TableColumn<BasePlusCommissionEmployees, String> bonusColumnbasePlusCommissionEmployees = new TableColumn<>("Bonus");

    // ESTABLISH A CONNECTION AND PREPARE ALL QUERIES
    public void open() {
        try {
            connection = DriverManager.getConnection(CONNECTION_STRING);
            queryEmployeesTable = connection.prepareStatement(QUERY_EMPLOYEES_TABLE);
            addEmployees = connection.prepareStatement(ADD_EMPLOYEES_SQL);
            deleteEmployee = connection.prepareStatement(DELETE_EMPLOYEE_SQL);
            querySalariedEmployeesTable = connection.prepareStatement(QUERY_SALARIED_EMPLOYEES_TABLE);
            addSalariedEmployee = connection.prepareStatement(ADD_SALARIED_EMPLOYEE);
            employeeTypeQuery = connection.prepareStatement(QUERY_FOR_EMPLOYEE_TYPE);
            deleteFromSalariedEmployee = connection.prepareStatement(DELETE_EMPLOYEE_FROM_SALARIED_EMPLOYEE);
            queryHourlyEmployeesTable = connection.prepareStatement(QUERY_HOURLY_EMPLOYEES_TABLE);
            addHourlyEmployee = connection.prepareStatement(ADD_HOURLY_EMPLOYEE);
            deleteHourlyEmployee = connection.prepareStatement(DELETE_EMPLOYEE_FROM_HOURLY_EMPLOYEE);
            queryCommissionEmployeesTable = connection.prepareStatement(QUERY_COMMISSION_EMPLOYEES_TABLE);
            addCommissionEmployeesTable = connection.prepareStatement(ADD_COMMISSION_EMPLOYEE);
            deleteCommissionEmployees = connection.prepareStatement(DELETE_EMPLOYEE_FROM_COMMISSION_EMPLOYEE);
            queryBasePlusCommissionEmployees = connection.prepareStatement(QUERY_BASE_PLUS_COMMISSION_EMPLOYEES_TABLE);
            addBasePlusCommissionEmployees = connection.prepareStatement(ADD_BASE_PLUS_COMMISSION_EMPLOYEE);
            deleteFromBasePlusCommissionEmployees = connection.prepareStatement(DELETE_EMPLOYEE_FROM_BASE_PLUS_COMMISSION_EMPLOYEE);
            selectAllBirthdays = connection.prepareStatement(SELECT_ALL_BIRTHDAYS);
            updateSalariedEmployeeBonus = connection.prepareStatement(UPDATE_SALARIED_EMPLOYEE_BONUS);
            updateHourlyEmployeeBonus = connection.prepareStatement(UPDATE_HOURLY_EMPLOYEE_BONUS);
            updateCommissionEmployeeBonus = connection.prepareStatement(UPDATE_COMMISSION_EMPLOYEE_BONUS);
            updateBasePlusCommissionEmployeeBonus = connection.prepareStatement(UPDATE_BASE_PLUS_COMMISSION_EMPLOYEE_BONUS);
            getCurrentSalariedEmployeeBonusValue = connection.prepareStatement(GET_CURRENT_SALARIED_EMPLOYEE_BONUS_VALUE);
            getCurrentHourlyEmployeeBonusValue = connection.prepareStatement(GET_CURRENT_HOURLY_EMPLOYEE_BONUS_VALUE);
            getCurrentCommissionEmployeeBonusValue = connection.prepareStatement(GET_CURRENT_COMMISSION_EMPLOYEE_BONUS_VALUE);
            getCurrentBasePlusCommissionEmployeeBonusValue = connection.prepareStatement(GET_CURRENT_BASE_PLUS_COMMISSION_EMPLOYEE_BONUS_VALUE);
            getGrossSales = connection.prepareStatement(GET_GROSS_SALES_CE);
            getSEBonus = connection.prepareStatement(GET_SE_BONUS);
            getHEBonus = connection.prepareStatement(GET_HE_BONUS);
            getCEBonus = connection.prepareStatement(GET_CE_BONUS);
            getBPCEBonus = connection.prepareStatement(GET_BPCE_BONUS);
            updateSEBonus = connection.prepareStatement(UPDATE_SE_BONUS);
            updateHEBonus = connection.prepareStatement(UPDATE_HE_BONUS);
            updateBPCEBonus = connection.prepareStatement(UPDATE_BPCE_BONUS);
            updateCEBonusWCB = connection.prepareStatement(UPDATE_CE_BONUS_WCB);
            updateCEBonusNCB = connection.prepareStatement(UPDATE_CE_BONUS_NCB);
            selectBaseSalary = connection.prepareStatement(SELECT_BASE_SALARY);
            updateBaseSalary = connection.prepareStatement(UPDATE_BASE_SALARY);
            System.out.println("Successfully Connected to DB");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private static boolean hasColumn(ResultSet rs, String columnName) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int columns = rsmd.getColumnCount();
        for (int x = 1; x <= columns; x++) {
            if (columnName.equals(rsmd.getColumnName(x))) {
                return true;
            }
        }
        return false;
    }

    ///////////////////////////////////////// WORKING WITH THE EMPLOYEES TABLE //////////////////////////////////////
    private ObservableList<Employees> populateEmployeesList(ResultSet resultSet) {
        // TAKES A RESULT SET GENERATED FROM A SQL QUERY AS A PARAMETER THEN BY LOOPING THROUGH THE RESULT SET CREATE
        // A NEW EMPLOYEE WITH DATA FROM THE RESULT SET. RETURNS AN OBSERVABLE LIST OF EMPLOYEES.
        try {
            ObservableList<Employees> employeesList = FXCollections.observableArrayList();
            while (resultSet.next()) {
                String SSN = resultSet.getString("socialSecurityNumber");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String birthday = resultSet.getString("birthday");
                String employeeType = resultSet.getString("employeeType");
                String departmentName = resultSet.getString("departmentName");

                employeesList.add(new Employees(SSN, firstName, lastName, birthday, employeeType, departmentName));
            }
            return employeesList;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private ObservableList<Employees> populateEmployeesTable() {
        // CREATES A RESULT SET GENERATED BY RUNNING THE QUERY FOR SELECTING ALL FROM THE EMPLOYEES TABLE AND RETURNS
        // AN OBSERVABLE LIS OF EMPLOYEE BY CALLING THE PREVIOUS FUNCTION AND PASSING IN THE RESULT SET GENERATED
        // AS THE PARAMETER
        try (ResultSet resultSet = queryEmployeesTable.executeQuery()) {
            return populateEmployeesList(resultSet);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private void setEmployeeTableLayout() {
        // SETS THE MIN COLUMN WIDTH  SETS CELL VALUE FACTORY LOOKS AT THE TYPE OF DATA THE WHOLE TABLE HOLDS(EMPLOYEES)
        // AND GETS THE CURRENT VALUE IN THAT SPOT TO ADD TO THE CELL IN IN THE SPECIFIC COLUMN WE ARE IN WE DO THIS FOR
        //  EACH COLUMN
        SSNColumn.setMinWidth(100);
        SSNColumn.setCellValueFactory(new PropertyValueFactory<>("SSN"));
        firstNameColumn.setMinWidth(100);
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setMinWidth(100);
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        birthdayColumn.setMinWidth(100);
        birthdayColumn.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        employeeTypeColumn.setMinWidth(100);
        employeeTypeColumn.setCellValueFactory(new PropertyValueFactory<>("employeeType"));
        departmentNameColumn.setMinWidth(100);
        departmentNameColumn.setCellValueFactory(new PropertyValueFactory<>("departmentName"));
    }

    public TableView<Employees> buildEmployeesTable(TableView table) {
        // CLEAR ANY RESIDUAL DATA THAT MAY HAVE BEEN LEFT BACK
        table.getColumns().clear();
        table.getItems().clear();
        setEmployeeTableLayout();
        // ADDS THE COLUMNS TO THE TABLE
        table.getColumns().addAll(SSNColumn, firstNameColumn, lastNameColumn, birthdayColumn, employeeTypeColumn,
                departmentNameColumn);
        // PROVIDES THE DATA IN THE FORM OF THE EMPLOYEES LIST THAT WE CAN USE TO GET SPECIFIC VALUES FROM EACH EMPLOYEE
        // (SSN, FIRTNAME, ...)
        table.setItems(populateEmployeesTable());

        return table;
    }

    public void addEmployee() {
        try {
            // CREATE 6 TEXT FIELDS TO PROMPT USER TO INPUT INFORMATION ABOUT THE EMPLOYEE THEY ARE INSERTING,
            // INFORMATION FILLS THE 6 COLUMN IN THE EMPLOYEE TABLE WITH BASIC EMPLOYEE INFORMATION
            JTextField field1 = new JTextField();
            JTextField field2 = new JTextField();
            JTextField field3 = new JTextField();
            JTextField field4 = new JTextField();
            JTextField field5 = new JTextField();
            JTextField field6 = new JTextField();

            Object[] fields = {"Enter SSN", field1, "Enter First Name", field2, "Enter Last Name", field3,
                    "Enter Birthday(YYYY-MM-DD)", field4, "Enter Employee Type", field5, "Enter Department Name", field6};

            // CREATES THE DIALOG PROMPT FOR USER, DEPENDING ON OK OR CANCEL THE BUTTON PRESSED RETURNS AN INT
            int n = JOptionPane.showConfirmDialog(null, fields, "INSERT EMPLOYEE", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);

            // CREATE STRINGS TO PASS INTO EMPLOYEES CONSTRUCTOR, TO CREATE A NEW EMPLOYEE
            String SSN = field1.getText();
            addEmployees.setString(1, SSN);
            String firstName = field2.getText();
            addEmployees.setString(2, firstName);
            String lastName = field3.getText();
            addEmployees.setString(3, lastName);
            String birthday = field4.getText();
            addEmployees.setString(4, birthday);
            String employeeType = field5.getText();
            addEmployees.setString(5, employeeType);
            String departmentName = field6.getText();
            addEmployees.setString(6, departmentName);

            // IF N = CANCEL_OPTION THEN EMPLOYEE INSERTION WILL BE TERMINATED AND CODE RETURNS WITHOUT ADDING EMPLOYEE
            if (n == JOptionPane.CANCEL_OPTION) {
                return;
            } else {
                // ADDS TO THE SALARIED EMPLOYEE TABLE IF THE EMPLOYEE TYPE IS SALARIED EMPLOYEE
                if (employeeType.toUpperCase().equals("SALARIEDEMPLOYEE") ||
                        employeeType.toUpperCase().equals("SALARIED EMPLOYEE")) {
                    // PROMPTS USER TO FILL IN REST OF THE INFO BASED ON THE EMPLOYEE TYPE
                    JTextField field7 = new JTextField();
                    Object[] salariedEmployeeFields = {"Enter Weekly Salary Of New Employee", field7};

                    int s = JOptionPane.showConfirmDialog(null, salariedEmployeeFields,
                            "COMPLETE SALARIED EMPLOYEE REGISTRATION", JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.PLAIN_MESSAGE);

                    // CANCEL INSERTION IF CANCEL IS CLICKED
                    if (s == JOptionPane.CANCEL_OPTION) {
                        return;
                    }
                    // OTHERWISE FILL THE PLACEHOLDER IN SQL QUERY WITH INFO OBTAINED FROM THE PREVIOUS USER INPUTS AND
                    // 0.0 FOR BONUS AND EXECUTE THE UPDATE TO ADD THE EMPLOYEE IN THE SALARIED TABLE, THE DATABASE GETS
                    // UPDATED IN THE BACKGROUND AND WHEN USER SWITCHES TO THE TABLE THE TABLE IS REFRESHED AND USER
                    // SEES UPDATED INFO
                    addSalariedEmployee.setString(1, SSN);
                    addSalariedEmployee.setString(2, field7.getText());
                    addSalariedEmployee.setString(3, "0.00");
                    addSalariedEmployee.executeUpdate();
                    //System.out.println("salary EmployeesDatabaseProject.Employees " + populateSalariedEmployeesTable());
                    //PROCESS REPEATED FOR ALL POSSIBLE EMPLOYEE TYPE
                }

                // ADDS TO THE HOURLY EMPLOYEE TABLE IF THE EMPLOYEE TYPE IS HOURLY EMPLOYEE
                if (employeeType.toUpperCase().equals("HOURLYEMPLOYEE") ||
                        employeeType.toUpperCase().equals("HOURLY EMPLOYEE")) {

                    JTextField field8 = new JTextField();
                    JTextField field9 = new JTextField();
                    Object[] hourlyEmployeeFields = {"Enter Hours Worked Per Week", field8, "Enter Hourly Wage Of New " +
                            "Employee", field9};

                    int s = JOptionPane.showConfirmDialog(null, hourlyEmployeeFields,
                            "COMPLETE HOURLY EMPLOYEE REGISTRATION", JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.PLAIN_MESSAGE);

                    if (s == JOptionPane.CANCEL_OPTION) {
                        return;
                    }
                    addHourlyEmployee.setString(1, SSN);
                    addHourlyEmployee.setString(2, field8.getText());
                    addHourlyEmployee.setString(3, field9.getText());
                    addHourlyEmployee.setString(4, "0.00");
                    addHourlyEmployee.executeUpdate();
                    //System.out.println("Hourly EmployeesDatabaseProject.Employees " + populateHourlyEmployeesTable());
                }
                // ADDS TO COMMISSION EMPLOYEE TABLE IF EMPLOYEE TYPE IS COMMISSION EMPLOYEE
                if (employeeType.toUpperCase().equals("COMMISSIONEMPLOYEE") ||
                        employeeType.toUpperCase().equals("COMMISSION EMPLOYEE")) {

                    JTextField field10 = new JTextField();
                    JTextField field11 = new JTextField();
                    Object[] commissionEmployeeFields = {"Enter Gross Sales Per Week", field10, "Enter Commission Rate Of New " +
                            "Employee", field11};

                    int t = JOptionPane.showConfirmDialog(null, commissionEmployeeFields,
                            "COMPLETE COMMISSION EMPLOYEE REGISTRATION", JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.PLAIN_MESSAGE);

                    if (t == JOptionPane.CANCEL_OPTION) {
                        return;
                    }
                    addCommissionEmployeesTable.setString(1, SSN);
                    addCommissionEmployeesTable.setString(2, field10.getText());
                    addCommissionEmployeesTable.setString(3, field11.getText());
                    if (Double.parseDouble(field10.getText()) > 10000.00) {
                        addCommissionEmployeesTable.setString(4, "100.00");
                    } else {
                        addCommissionEmployeesTable.setString(4, "0.00");
                    }
                    addCommissionEmployeesTable.executeUpdate();
                    //System.out.println("Commission EmployeesDatabaseProject.Employees " + populateCommissionEmployeesTable());
                }
                //ADDS BASE PLUS COMMISSION EMPLOYEE
                if (employeeType.toUpperCase().equals("BASEPLUSCOMMISSIONEMPLOYEE") ||
                        employeeType.toUpperCase().equals("BASE PLUS COMMISSION EMPLOYEE")) {
                    JTextField field12 = new JTextField();
                    JTextField field13 = new JTextField();
                    JTextField field14 = new JTextField();
                    Object[] basePlusCommissionEmployeeFields = {"Enter Gross Sales Per Week", field12, "Enter Commission Rate Of New " +
                            "Employee", field13, "Enter New Employees Base Salary", field14};

                    int t = JOptionPane.showConfirmDialog(null, basePlusCommissionEmployeeFields,
                            "COMPLETE BASE PLUS COMMISSION EMPLOYEE REGISTRATION", JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.PLAIN_MESSAGE);

                    if (t == JOptionPane.CANCEL_OPTION) {
                        return;
                    }
                    addBasePlusCommissionEmployees.setString(1, SSN);
                    addBasePlusCommissionEmployees.setString(2, field12.getText());
                    addBasePlusCommissionEmployees.setString(3, field13.getText());
                    addBasePlusCommissionEmployees.setString(4, field14.getText());
                    addBasePlusCommissionEmployees.setString(5, "0.0");
                    addBasePlusCommissionEmployees.executeUpdate();
                }
                addEmployees.executeUpdate();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "THE SOCIAL SECURITY NUMBER YOU ENTERED IS" +
                    " ALREADY REGISTERED!", "ERROR", JOptionPane.ERROR_MESSAGE);
            System.out.println("Couldn't Run Code " + e.getMessage());
        }
    }

    public void deleteEmployee() {
        try {
            // PROMPT USER FOR INPUT SSN TO DELETE EMPLOYEE SINCE EVERY SSN IS UNIQUE, THEN SELECTS THE EMPLOYEE WITH SSN
            // ENTERED, IF THE CANCEL OPTION IS HIT RETURN WITHOUT DELETION, RUN A QUERY TO GET THE EMPLOYEE TYPE, THEN
            // CHECK AGAINST ALL EMPLOYEE TYPE AND IF MATCHES THETN DELETE FROM THAT SPECIFIC TABLE THEN FINISH BY
            // DELETING FROM THE MAIN EMPLOYEE TABLE ALSO
            JTextField field1 = new JTextField();
            Object[] fields = {"Enter SSN Of Employee You Wish To Remove", field1};

            int n = JOptionPane.showConfirmDialog(null, fields, "DELETE EMPLOYEE",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            String deletedEmployeeSSN = field1.getText();
            deleteEmployee.setString(1, deletedEmployeeSSN);

            if (n == JOptionPane.CANCEL_OPTION) {
                return;
            } else {
                employeeTypeQuery.setString(1, deletedEmployeeSSN); // SELECTS THE EMPLOYEE TYPE OF EMPLOYEE WITH SSN ***
                ResultSet resultSet = employeeTypeQuery.executeQuery();

                if (resultSet.next()) { // ONLY BE ONE ENTRY
                    if (resultSet.getString("employeeType").toUpperCase().equals("SALARIEDEMPLOYEE") ||
                            resultSet.getString("employeeType").toUpperCase().equals("SALARIED EMPLOYEE")) {
                        deleteFromSalariedEmployee.setString(1, deletedEmployeeSSN);
                        deleteFromSalariedEmployee.executeUpdate();
                    }
                    if (resultSet.getString("employeeType").toUpperCase().equals("HOURLYEMPLOYEE") ||
                            resultSet.getString("employeeType").toUpperCase().equals("HOURLY EMPLOYEE")) {
                        deleteHourlyEmployee.setString(1, deletedEmployeeSSN);
                        deleteHourlyEmployee.executeUpdate();
                    }
                    if (resultSet.getString("employeeType").toUpperCase().equals("COMMISSIONEMPLOYEE") ||
                            resultSet.getString("employeeType").toUpperCase().equals("COMMISSION EMPLOYEE")) {
                        deleteCommissionEmployees.setString(1, deletedEmployeeSSN);
                        deleteCommissionEmployees.executeUpdate();
                    }
                    if (resultSet.getString("employeeType").toUpperCase().equals("BASEPLUSCOMMISSIONEMPLOYEE") ||
                            resultSet.getString("employeeType").toUpperCase().equals("BASE PLUS COMMISSION EMPLOYEE")) {
                        deleteFromBasePlusCommissionEmployees.setString(1, deletedEmployeeSSN);
                        deleteFromBasePlusCommissionEmployees.executeUpdate();
                    }
                }
                if (deleteEmployee.executeUpdate() != 1) {
                    JOptionPane.showMessageDialog(null, "THE SOCIAL SECURITY NUMBER YOU ENTERED " +
                            " DOES NOT EXIST", "ERROR", JOptionPane.ERROR_MESSAGE);

                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void manualQueryEmployeesTable(TableView table, String inputtedSQL) {
        // CREATES A STATEMNT BASED ON THE INPUT TO THE TEXT ARE AND CREATES A RESULT SET FROM THAT QUERY, THEN LOOPS
        // THROUGH THE RESULT SET TO CHECK FOR COLUMNS WITHIN THE RESULT SET THEN SETS STRINGS EQUAL TO THE STRING IN
        // THE COLUMN TO CREATE A NEW EMPLOYEE AND ADD IT TO THE EMPLOYEES LIST AND CREATES A NEW TABLE WITH THE RESULTS
        // FROM THE QUERY
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(inputtedSQL)) {
            ObservableList<Employees> queriedEmployeeTableList = FXCollections.observableArrayList();
            //LOOP THRU RESULT SET ADD ALL PARAMETERS AND CREATE AS MANY EMPLOYEES AS NECESSARY AND ADD TO A LIST
            while (resultSet.next()) {
                String SSN = null;
                String firstName = null;
                String lastName = null;
                String birthday = null;
                String employeeType = null;
                String departmentName = null;

                if (hasColumn(resultSet, "socialSecurityNumber")) {
                    SSN = resultSet.getString("socialSecurityNumber");
                }
                if (hasColumn(resultSet, "firstName")) {
                    firstName = resultSet.getString("firstName");
                }
                if (hasColumn(resultSet, "lastName")) {
                    lastName = resultSet.getString("lastName");
                }
                if (hasColumn(resultSet, "birthday")) {
                    birthday = resultSet.getString("birthday");
                }
                if (hasColumn(resultSet, "employeeType")) {
                    employeeType = resultSet.getString("employeeType");
                }
                if (hasColumn(resultSet, "departmentName")) {
                    departmentName = resultSet.getString("departmentName");
                }
                queriedEmployeeTableList.add(new Employees(SSN, firstName, lastName, birthday, employeeType, departmentName));
            }
            table.getColumns().clear();
            table.getItems().clear();
            //REBUILD TABLE WITH DATA OBTAINED FROM THE QUERY
            // ADDS THE COLUMN NAMES
            table.getColumns().addAll(SSNColumn, firstNameColumn, lastNameColumn, birthdayColumn, employeeTypeColumn,
                    departmentNameColumn);
            // PROVIDES THE DATA
            table.setItems(queriedEmployeeTableList);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    // RESETS THE TABLE THAT WE ARE CURRENTLY VIEWING WITH THE INFO PRE-MANUAL QUERY
    public void resetEmployeesTable(TableView table) {
        populateEmployeesTable();
        buildEmployeesTable(table);
    }
    //////////////////////////////////// WORKING WITH SALARIED EMPLOYEES TABLE//////////////////////////////////////////
    // THESE FUNCTIONS FOLLOW THE SAME STRUCTURE AS THE EMPLOYEES TABLE EXCEPT FOR ADD AND DELETE SINCE THOSE ARE ONLY
    // FUNCTIONAL IN THE EMPLOYEE TABLE, THIS PROCEDURE OCCURS FOR ALL OTHER TABLES TOO
    private void setSalariedEmployeesTableLayout() {
        SSNColumnSalariedEmployees.setMinWidth(100);
        SSNColumnSalariedEmployees.setCellValueFactory(new PropertyValueFactory<>("socialSecurityNumber"));
        weeklySalaryColumn.setMinWidth(100);
        weeklySalaryColumn.setCellValueFactory(new PropertyValueFactory<>("weeklySalary"));
        bonusColumnSalariedEmployees.setMinWidth(100);
        bonusColumnSalariedEmployees.setCellValueFactory(new PropertyValueFactory<>("bonus"));
    }

    public TableView<SalariedEmployees> buildSalariedEmployeesTable(TableView tableView) {
        tableView.getItems().clear();
        tableView.getColumns().clear();
        setSalariedEmployeesTableLayout();
        // PROVIDES THE DATA
        tableView.setItems(populateSalariedEmployeesTable());
        // ADDS THE COLUMN NAMES
        tableView.getColumns().addAll(SSNColumnSalariedEmployees, weeklySalaryColumn, bonusColumnSalariedEmployees);

        return tableView;
    }

    private ObservableList<SalariedEmployees> populateListOfSalariedEmployees(ResultSet resultSet) {
        try {
            ObservableList<SalariedEmployees> salariedEmployeesList = FXCollections.observableArrayList();
            while (resultSet.next()) {
                String SSN = resultSet.getString("socialSecurityNumber");
                String weeklySalary = resultSet.getString("weeklySalary");
                String bonus = resultSet.getString("bonus");

                salariedEmployeesList.add(new SalariedEmployees(SSN, weeklySalary, bonus));
            }
            return salariedEmployeesList;
        } catch (SQLException e) {
            //
            return null;
        }
    }

    private ObservableList<SalariedEmployees> populateSalariedEmployeesTable() {
        try (ResultSet resultSet = querySalariedEmployeesTable.executeQuery()) {
            return populateListOfSalariedEmployees(resultSet);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public void manualQueriesSalariedEmployeeTable(TableView table, String inputtedSQL) {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(inputtedSQL)) {

            ObservableList<SalariedEmployees> queriedSalariedEmployeeTableList = FXCollections.observableArrayList();
            while (resultSet.next()) {
                String SSN = null;
                String weekly = null;
                String bonus = null;
                System.out.println(hasColumn(resultSet, ""));
                if (hasColumn(resultSet, "socialsecurityNumber")) {
                    SSN = resultSet.getString("socialSecurityNumber");
                }
                if (hasColumn(resultSet, "weeklySalary")) {
                    weekly = resultSet.getString("weeklySalary");
                }
                if (hasColumn(resultSet, "bonus")) {
                    bonus = resultSet.getString("bonus");
                }
                queriedSalariedEmployeeTableList.add(new SalariedEmployees(SSN, weekly, bonus));
            }
            table.getColumns().clear();
            table.getItems().clear();
            // ADDS THE COLUMN NAMES
            table.getColumns().addAll(SSNColumnSalariedEmployees, weeklySalaryColumn, bonusColumnSalariedEmployees);
            // PROVIDES THE DATA
            table.setItems(queriedSalariedEmployeeTableList);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void resetSalariedEmployeesTable(TableView table) {
        populateSalariedEmployeesTable();
        buildSalariedEmployeesTable(table);
    }
///////////////////////////////////WORKING WITH HOURLY EMPLOYEES TABLE//////////////////////////////////////////////////
    private void setHourlyEmployeesTableLayout() {
        SSNColumnHourlyEmployees.setMinWidth(100);
        SSNColumnHourlyEmployees.setCellValueFactory(new PropertyValueFactory<>("socialSecurityNumber"));
        hoursColumn.setMinWidth(100);
        hoursColumn.setCellValueFactory(new PropertyValueFactory<>("hours"));
        wageColumn.setMinWidth(100);
        wageColumn.setCellValueFactory(new PropertyValueFactory<>("wage"));
        bonusColumnHourlyEmployees.setMinWidth(100);
        bonusColumnHourlyEmployees.setCellValueFactory(new PropertyValueFactory<>("bonus"));
    }

    public TableView<hourlyEmployees> buildHourlyEmployeesTable(TableView tableView) {
        tableView.getItems().clear();
        tableView.getColumns().clear();
        setHourlyEmployeesTableLayout();
        // ADDS THE COLUMN NAMES
        tableView.getColumns().addAll(SSNColumnHourlyEmployees, hoursColumn, wageColumn, bonusColumnHourlyEmployees);
        // PROVIDES THE DATA
        tableView.setItems(populateHourlyEmployeesTable());
        return tableView;
    }

    private ObservableList<hourlyEmployees> populateListOfHourlyEmployees(ResultSet resultSet) {
        try {
            ObservableList<hourlyEmployees> hourlyEmployeesList = FXCollections.observableArrayList();
            while (resultSet.next()) {
                String SSN = resultSet.getString("socialSecurityNumber");
                String hours = resultSet.getString("hours");
                String wage = resultSet.getString("wage");
                String bonus = resultSet.getString("bonus");

                hourlyEmployeesList.add(new hourlyEmployees(SSN, hours, wage, bonus));
            }
            return hourlyEmployeesList;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private ObservableList<hourlyEmployees> populateHourlyEmployeesTable() {
        try (ResultSet resultSet = queryHourlyEmployeesTable.executeQuery()) {
            return populateListOfHourlyEmployees(resultSet);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public void manualQueriesHourlyEmployeeTable(TableView table, String inputtedSQL) {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(inputtedSQL)) {

            ObservableList<hourlyEmployees> queriedHourlyEmployeeTableList = FXCollections.observableArrayList();
            while (resultSet.next()) {
                String SSN = null;
                String hours = null;
                String wage = null;
                String bonus = null;

                if (hasColumn(resultSet, "socialSecurityNumber")) {
                    SSN = resultSet.getString("socialSecurityNumber");
                }
                if (hasColumn(resultSet, "hours")) {
                    hours = resultSet.getString("hours");
                }
                if (hasColumn(resultSet, "wage")) {
                    wage = resultSet.getString("wage");
                }
                if (hasColumn(resultSet, "bonus")) {
                    bonus = resultSet.getString("bonus");
                }
                queriedHourlyEmployeeTableList.add(new hourlyEmployees(SSN, hours, wage, bonus));
            }
            table.getColumns().clear();
            table.getItems().clear();
            // ADDS THE COLUMN NAMES
            table.getColumns().addAll(SSNColumnHourlyEmployees, hoursColumn, wageColumn, bonusColumnHourlyEmployees);
            // PROVIDES THE DATA
            table.setItems(queriedHourlyEmployeeTableList);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void resetHourlyEmployeesTable(TableView table) {
        populateHourlyEmployeesTable();
        buildHourlyEmployeesTable(table);
    }
    ////////////////////////////////////////WORKING WITH COMMISSION EMPLOYEES///////////////////////////////////////////
    private void setComissionEmployeesTableLayout() {
        SSNColumnCommissionEmployees.setMinWidth(100);
        SSNColumnCommissionEmployees.setCellValueFactory(new PropertyValueFactory<>("socialSecurityNumber"));
        grossSalesColumn.setMinWidth(100);
        grossSalesColumn.setCellValueFactory(new PropertyValueFactory<>("grossSales"));
        commissionRateColumn.setMinWidth(100);
        commissionRateColumn.setCellValueFactory(new PropertyValueFactory<>("commissionRate"));
        bonusColumnCommissionEmployees.setMinWidth(100);
        bonusColumnCommissionEmployees.setCellValueFactory(new PropertyValueFactory<>("bonus"));
    }

    public TableView<CommissionEmployee> buildCommissionEmployeesTable(TableView tableView) {
        tableView.getItems().clear();
        tableView.getColumns().clear();
        setComissionEmployeesTableLayout();
        // ADDS THE COLUMN NAMES
        tableView.getColumns().addAll(SSNColumnCommissionEmployees, grossSalesColumn, commissionRateColumn,
                bonusColumnCommissionEmployees);
        // PROVIDES THE DATA
        tableView.setItems(populateCommissionEmployeesTable());
        return tableView;
    }

    private ObservableList<CommissionEmployee> populateListOfCommissionEmployees(ResultSet resultSet) {
        try {
            ObservableList<CommissionEmployee> commissionEmployeesList = FXCollections.observableArrayList();
            while (resultSet.next()) {
                String SSN = resultSet.getString("socialSecurityNumber");
                String grossSales = resultSet.getString("grossSales");
                String commissionRate = resultSet.getString("commissionRate");
                String bonus = resultSet.getString("bonus");
                commissionEmployeesList.add(new CommissionEmployee(SSN, grossSales, commissionRate, bonus));
            }
            return commissionEmployeesList;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private ObservableList<CommissionEmployee> populateCommissionEmployeesTable() {
        try (ResultSet resultSet = queryCommissionEmployeesTable.executeQuery()) {
            return populateListOfCommissionEmployees(resultSet);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public void manualQueriesCommissionEmployeeTable(TableView table, String inputtedSQL) {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(inputtedSQL)) {

            ObservableList<CommissionEmployee> queriedCommissionEmployeeTableList = FXCollections.observableArrayList();
            while (resultSet.next()) {
                String SSN = null;
                String grossSales = null;
                String commissionRate = null;
                String bonus = null;

                if (hasColumn(resultSet, "socialSecurityNumber")) {
                    System.out.println("Into the Social Security Number section");
                    SSN = resultSet.getString("socialSecurityNumber");
                }
                if (hasColumn(resultSet, "grossSales")) {
                    System.out.println("Into the grossSales Section");
                    grossSales = resultSet.getString("grossSales");
                }
                if (hasColumn(resultSet, "commissionRate")) {
                    System.out.println("Into the commissionRate Section");
                    commissionRate = resultSet.getString("commissionRate");
                }
                if (hasColumn(resultSet, "bonus")) {
                    System.out.println("BOONUS SECTION CE");
                    bonus = resultSet.getString("bonus");
                }
                queriedCommissionEmployeeTableList.add(new CommissionEmployee(SSN, grossSales, commissionRate, bonus));
            }
            table.getColumns().clear();
            table.getItems().clear();
            // ADDS THE COLUMN NAMES
            table.getColumns().addAll(SSNColumnCommissionEmployees, grossSalesColumn, commissionRateColumn, bonusColumnCommissionEmployees);
            // PROVIDES THE DATA
            table.setItems(queriedCommissionEmployeeTableList);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void resetCommissionEmployeesTable(TableView table) {
        populateCommissionEmployeesTable();
        buildCommissionEmployeesTable(table);
    }
    /////////////////////////////////////WORKING WITH COMMISSION + BASE EMPLOYEES///////////////////////////////////////
    private void setBasePlusCommissionEmployeesTableLayout() {
        SSNColumnbasePlusCommissionEmployees.setMinWidth(100);
        SSNColumnbasePlusCommissionEmployees.setCellValueFactory(new PropertyValueFactory<>("socialSecurityNumber"));
        grossSalesColumnbasePlusCommissionEmployees.setMinWidth(100);
        grossSalesColumnbasePlusCommissionEmployees.setCellValueFactory(new PropertyValueFactory<>("grossSales"));
        commissionRateColumnbasePlusCommissionEmployees.setMinWidth(100);
        commissionRateColumnbasePlusCommissionEmployees.setCellValueFactory(new PropertyValueFactory<>("commissionRate"));
        baseSalaryColumn.setMinWidth(100);
        baseSalaryColumn.setCellValueFactory(new PropertyValueFactory<>("baseSalary"));
        bonusColumnbasePlusCommissionEmployees.setMinWidth(100);
        bonusColumnbasePlusCommissionEmployees.setCellValueFactory(new PropertyValueFactory<>("bonus"));
    }

    public TableView<BasePlusCommissionEmployees> buildBasePlusCommissionEmployeesTable(TableView tableView) {
        tableView.getItems().clear();
        tableView.getColumns().clear();
        setBasePlusCommissionEmployeesTableLayout();
        // ADDS THE COLUMN NAMES
        tableView.getColumns().addAll(SSNColumnbasePlusCommissionEmployees, grossSalesColumnbasePlusCommissionEmployees,
                commissionRateColumnbasePlusCommissionEmployees, baseSalaryColumn, bonusColumnbasePlusCommissionEmployees);
        // PROVIDES THE DATA
        tableView.setItems(populateBasePlusCommissionEmployeesTable());
        return tableView;
    }

    private ObservableList<BasePlusCommissionEmployees> populateListOfBasePlusCommissionEmployees(ResultSet resultSet) {
        try {
            ObservableList<BasePlusCommissionEmployees> basePlusCommissionEmployeesList = FXCollections.observableArrayList();
            while (resultSet.next()) {
                String SSN = resultSet.getString("socialSecurityNumber");
                String grossSales = resultSet.getString("grossSales");
                String commissionRate = resultSet.getString("commissionRate");
                String baseSalary = resultSet.getString("baseSalary");
                String bonus = resultSet.getString("bonus");
                basePlusCommissionEmployeesList.add(new BasePlusCommissionEmployees(SSN, grossSales, commissionRate,
                        baseSalary, bonus));
            }
            return basePlusCommissionEmployeesList;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private ObservableList<BasePlusCommissionEmployees> populateBasePlusCommissionEmployeesTable() {
        try (ResultSet resultSet = queryBasePlusCommissionEmployees.executeQuery()) {
            return populateListOfBasePlusCommissionEmployees(resultSet);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public void manualQueriesBasePlusCommissionEmployeeTable(TableView table, String inputtedSQL) {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(inputtedSQL)) {

            ObservableList<BasePlusCommissionEmployees> queriedBasePlusCommissionEmployeeTableList = FXCollections.observableArrayList();
            while (resultSet.next()) {
                String SSN = null;
                String grossSales = null;
                String commissionRate = null;
                String baseSalary = null;
                String bonus = null;

                if (hasColumn(resultSet, "socialSecurityNumber")) {
                    System.out.println("Into the Social Security Number section");
                    SSN = resultSet.getString("socialSecurityNumber");
                }
                if (hasColumn(resultSet, "grossSales")) {
                    System.out.println("Into the grossSales Section");
                    grossSales = resultSet.getString("grossSales");
                }
                if (hasColumn(resultSet, "baseSalary")) {
                    System.out.println("Into the commissionRate Section");
                    baseSalary = resultSet.getString("commissionRate");
                }
                if (hasColumn(resultSet, "bonus")) {
                    System.out.println("BOONUS SECTION CE");
                    bonus = resultSet.getString("bonus");
                }
                if (hasColumn(resultSet, "commissionRate")) {
                    System.out.println("Into the commissionRate Section");
                    commissionRate = resultSet.getString("commissionRate");
                }
                queriedBasePlusCommissionEmployeeTableList.add(new BasePlusCommissionEmployees(SSN, grossSales,
                        commissionRate, baseSalary, bonus));
            }
            table.getColumns().clear();
            table.getItems().clear();
            // ADDS THE COLUMN NAMES
            table.getColumns().addAll(SSNColumnbasePlusCommissionEmployees, grossSalesColumnbasePlusCommissionEmployees,
                    commissionRateColumnbasePlusCommissionEmployees, baseSalaryColumn, bonusColumnCommissionEmployees);
            // PROVIDES THE DATA
            table.setItems(queriedBasePlusCommissionEmployeeTableList);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void resetBasePlusCommissionEmployeesTable(TableView table) {
        populateBasePlusCommissionEmployeesTable();
        buildBasePlusCommissionEmployeesTable(table);
    }
    /////////////////////////////////////////////BIRTHDAY BONUS ADDER///////////////////////////////////////////////////
    private boolean isItBirthdayMonth(DatePicker datePicker, String month) {
        // CHECKS IF THE LOCAL DATE(MONTH) OBTAINED FROM THE DATEPICKER MATCHES THE MONTH ENTERED AS A PARAMETER AND
        // RETURNS TRUE OR FALSE
        String localDate = datePicker.getValue().toString();
        return ((localDate.charAt(5) + localDate.charAt(6)) == (month.charAt(0) + month.charAt(1)) ||
                localDate.charAt(6) == month.charAt(0));
    }

    public void addBonusIfBirthdayMonth(DatePicker datePicker) {
        // CHECKS THE EMPLOYEES AND SEE IF ITS ANYONE'S BIRTHDAY THEN CHECKS THEIR EMPLOYEE TYPE AND SINCE THE MAX
        // BONUS ANY EMPLOYEE CAN GET THATS NOT COMMISSION IS 100 WE ADD 100 TO THEIR BONUS IF ITS 0 OR ADD 100 IF
        // BONUS IS 100 AND THEY ARE A COMMISSION EMPLOYEE WHOSE GROSS SALES ARE > 10000
        try (ResultSet resultSet = selectAllBirthdays.executeQuery()) {
            while (resultSet.next()) {
                String SSN = resultSet.getString("socialSecurityNumber");
                String birthday = resultSet.getString("birthday");
                String employeeType = resultSet.getString("employeeType");

                if (isItBirthdayMonth(datePicker, birthday.substring(5, 7))) {
                    // SALARIED EMPLOYEEES
                    if (employeeType.toUpperCase().equals("SALARIEDEMPLOYEE") ||
                            employeeType.toUpperCase().equals("SALARIED EMPLOYEE")) {

                        getCurrentSalariedEmployeeBonusValue.setString(1, SSN);

                        ResultSet resultSet1 = getCurrentSalariedEmployeeBonusValue.executeQuery();
                        while (resultSet1.next()) {
                            Double bonusValue = (Double.parseDouble(resultSet1.getString("bonus"))) + 100.0;
                            if (bonusValue <= 100) {
                                updateSalariedEmployeeBonus.setString(1, Double.toString(bonusValue));
                                updateSalariedEmployeeBonus.setString(2, SSN);
                                updateSalariedEmployeeBonus.executeUpdate();
                            }
                        }
                    }
                    // HOURLY EMPLOYEE
                    if (employeeType.toUpperCase().equals("HOURLYEMPLOYEE") ||
                            employeeType.toUpperCase().equals("HOURLY EMPLOYEE")) {

                        getCurrentHourlyEmployeeBonusValue.setString(1, SSN);

                        ResultSet resultSet1 = getCurrentHourlyEmployeeBonusValue.executeQuery();

                        while (resultSet1.next()) {
                            Double bonusValue = (Double.parseDouble(resultSet1.getString("bonus"))) + 100.0;
                            if (bonusValue <= 100) {
                                System.out.println("Bonus for SSN " + SSN + " is now " + bonusValue);
                                updateHourlyEmployeeBonus.setString(1, Double.toString(bonusValue));
                                updateHourlyEmployeeBonus.setString(2, SSN);
                                updateHourlyEmployeeBonus.executeUpdate();
                            }
                        }
                    }
                    // COMMISSION EMPLOYEE
                    if (employeeType.toUpperCase().equals("COMMISSIONEMPLOYEE") ||
                            employeeType.toUpperCase().equals("COMMISSION EMPLOYEE")) {

                        getCurrentCommissionEmployeeBonusValue.setString(1, SSN);

                        ResultSet resultSet1 = getCurrentCommissionEmployeeBonusValue.executeQuery();
                        while (resultSet1.next()) {
                            Double bonusValue= (Double.parseDouble(resultSet1.getString("bonus")));
                            getGrossSales.setString(1,SSN);
                            ResultSet resultSet2 =getGrossSales.executeQuery();

                            if ((bonusValue == 100) && (Double.parseDouble(resultSet2.getString("grossSales")) > 10000.0)) {
                                bonusValue += 100;
                                updateCommissionEmployeeBonus.setString(1, Double.toString(bonusValue));
                                updateCommissionEmployeeBonus.setString(2, SSN);
                                updateCommissionEmployeeBonus.executeUpdate();
                            }
                            else if((bonusValue == 0 ) && (Double.parseDouble(resultSet2.
                                    getString("grossSales")) < 10000.0)){
                                bonusValue += 100;
                                updateCommissionEmployeeBonus.setString(1, Double.toString(bonusValue));
                                updateCommissionEmployeeBonus.setString(2, SSN);
                                updateCommissionEmployeeBonus.executeUpdate();
                            }
                        }
                    }
                    //BASE PLUS COMMISSION EMPLOYEE
                    if (employeeType.toUpperCase().equals("BASEPLUSCOMMISSIONEMPLOYEE") ||
                            employeeType.toUpperCase().equals("BASE PLUS COMMISSION EMPLOYEE")) {

                        getCurrentBasePlusCommissionEmployeeBonusValue.setString(1, SSN);

                        ResultSet resultSet1 = getCurrentBasePlusCommissionEmployeeBonusValue.executeQuery();

                        while (resultSet1.next()) {
                            Double bonusValue = (Double.parseDouble(resultSet1.getString("bonus"))) + 100.0;
                            if (bonusValue <= 100) {
                                updateBasePlusCommissionEmployeeBonus.setString(1, Double.toString(bonusValue));
                                updateBasePlusCommissionEmployeeBonus.setString(2, SSN);
                                updateBasePlusCommissionEmployeeBonus.executeUpdate();
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void resetBonuses(){
        // RESETS ALL BONUSES FOR WHEN USER RELAUNCHES APPLICATION
        try(ResultSet resultSet = getSEBonus.executeQuery();
            ResultSet resultSet1 = getHEBonus.executeQuery();
            ResultSet resultSet2 = getCEBonus.executeQuery();
            ResultSet resultSet3 = getBPCEBonus.executeQuery()){
            while (resultSet.next()){
                updateSEBonus.executeUpdate();
            }
            while (resultSet1.next()){
                updateHEBonus.executeUpdate();
            }
            while (resultSet2.next()){
                updateCEBonusNCB.executeUpdate();
                updateCEBonusWCB.executeUpdate();
            }
            while (resultSet3.next()){
                updateBPCEBonus.executeUpdate();
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addTenPercent(TableView table){
        try(ResultSet resultSet = selectBaseSalary.executeQuery()){
            while (resultSet.next()){
                String SSN = resultSet.getString("socialSecurityNumber");
                double currentBaseSalary = resultSet.getDouble("baseSalary");
                double newBaseSalary = (0.1 * currentBaseSalary) + currentBaseSalary;
                updateBaseSalary.setString(1,String.format("%.2f",newBaseSalary));
                updateBaseSalary.setString(2,SSN);
                updateBaseSalary.executeUpdate();
            }
            buildBasePlusCommissionEmployeesTable(table);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }
}
