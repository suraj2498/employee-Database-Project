package EmployeesDatabaseProject;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private EmployeesDB employeesDB = new EmployeesDB();
    private TableView table = new TableView<>();

    @Override
    public void start(Stage primaryStage){
        try {
            BorderPane root = new BorderPane();
            primaryStage.setTitle("Employee Database Application");

            // CONTROLS TO USE
            Button button1 = new Button("Add Employee");
            Button button2 = new Button("Delete Employee");
            Button button3 = new Button("Execute Manual Query");
            Button button4 = new Button("Reset Current Table");
            Button button5 = new Button("Clear Query Area");
            Button button6 = new Button("increase Base Salary 10%");
            ComboBox predefinedQueries = new ComboBox();
            TextArea textArea = new TextArea();
            DatePicker datePicker = new DatePicker();

            // TEXT AREA SPECIFICS
            textArea.setPromptText("Enter SQL Query For Table Here");
            textArea.setMinWidth(600);

            // ADDING THE CHOICES TO COMBOBOX
            ObservableList<String> listOfTableNames = FXCollections.observableArrayList();
            listOfTableNames.add("Employees");
            listOfTableNames.add("Hourly Employees");
            listOfTableNames.add("Salaried Employees");
            listOfTableNames.add("Commission Only Employees");
            listOfTableNames.add("Base Plus Commission Employees");

            predefinedQueries.setMaxWidth(150);
            ObservableList<String> predefinedQueryOptions = FXCollections.observableArrayList();
            predefinedQueryOptions.addAll(
                    "SELECT * FROM employees WHERE departmentName = \"SALES\"",
                    "SELECT * FROM hourlyEmployees WHERE hours > 30",
                    "SELECT * FROM commissionEmployees ORDER BY commissionRate"
            );
            predefinedQueries.setItems(predefinedQueryOptions);
            predefinedQueries.setPromptText("Predefined Queries");

            // THE RIGHT PANE
            VBox rightContainer = new VBox();
            rightContainer.getChildren().addAll(button1, button2, datePicker);
            rightContainer.setAlignment(Pos.CENTER);
            rightContainer.setSpacing(10);
            rightContainer.setPrefWidth(200);
            BorderPane.setAlignment(rightContainer, Pos.CENTER);


            VBox centerContainer = new VBox();
            Label tableLabel = new Label("Table Name");
            ComboBox tableNames = new ComboBox();
            tableNames.setItems(listOfTableNames);
            tableNames.setPromptText("Select Table");

            HBox labelAndComboBoxHolder = new HBox();
            labelAndComboBoxHolder.setAlignment(Pos.CENTER_LEFT);
            labelAndComboBoxHolder.setSpacing(7);
            labelAndComboBoxHolder.getChildren().addAll(tableLabel, tableNames);

            centerContainer.getChildren().addAll(labelAndComboBoxHolder, table);
            centerContainer.setSpacing(10);

            VBox bottomRight = new VBox();
            textArea.setPrefWidth(table.getWidth());
            bottomRight.setSpacing(10);
            bottomRight.setAlignment(Pos.CENTER);
            bottomRight.getChildren().addAll(button3, predefinedQueries, button4, button5,button6);

            HBox bottomContainer = new HBox();
            // HBox.setHgrow(textArea, Priority.ALWAYS);
            bottomContainer.getChildren().addAll(textArea, bottomRight);
            bottomContainer.setSpacing(15);
            bottomContainer.setAlignment(Pos.CENTER_LEFT);

            employeesDB.open();
            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            table.setVisible(false);
            button1.setDisable(true);
            button2.setDisable(true);
            button3.setDisable(true);
            button4.setDisable(true);
            button5.setDisable(true);
            button6.setVisible(false);
            textArea.setDisable(true);
            datePicker.setDisable(true);
            predefinedQueries.setDisable(true);


            tableNames.setOnAction(event -> {
                if (tableNames.getValue() == ("Employees")) {
                    buildCorrespondingTable(1);
                    table.setVisible(true);
                    table.refresh();
                    button1.setDisable(false);
                    button2.setDisable(false);
                    button3.setDisable(false);
                    button4.setDisable(false);
                    textArea.setDisable(false);
                    datePicker.setDisable(false);
                    predefinedQueries.setDisable(false);
                    button5.setDisable(false);

                    datePicker.setOnAction(event0 -> {
                        employeesDB.addBonusIfBirthdayMonth(datePicker);
                        table.refresh();
                    });

                    button1.setOnAction(event1 -> {
                        employeesDB.addEmployee();
                        employeesDB.buildEmployeesTable(table);
                        table.refresh();
                    });

                    button2.setOnAction(event2 -> {
                        employeesDB.deleteEmployee();
                        employeesDB.buildEmployeesTable(table);
                        table.refresh();
                    });

                    button3.setOnAction(event3 -> {
                        employeesDB.manualQueryEmployeesTable(table, textArea.getText());
                        table.refresh();
                    });

                    button4.setOnAction(event4 -> {
                        employeesDB.resetEmployeesTable(table);
                        table.refresh();
                    });
                } else if (tableNames.getValue() == "Salaried Employees") {
                    buildCorrespondingTable(2);
                    table.setVisible(true);
                    table.refresh();
                    button1.setDisable(true);
                    button2.setDisable(true);
                    button3.setDisable(false);
                    button4.setDisable(false);
                    datePicker.setDisable(true);
                    predefinedQueries.setDisable(false);
                    textArea.setDisable(false);
                    button5.setDisable(false);

                    button3.setOnAction(event2 -> {
                        employeesDB.manualQueriesSalariedEmployeeTable(table, textArea.getText());
                        table.refresh();
                    });
                    button4.setOnAction(event3 -> {
                        employeesDB.resetSalariedEmployeesTable(table);
                        table.refresh();
                    });
                } else if (tableNames.getValue() == "Hourly Employees") {
                    buildCorrespondingTable(3);
                    table.setVisible(true);
                    table.refresh();
                    button1.setDisable(true);
                    button2.setDisable(true);
                    button3.setDisable(false);
                    button4.setDisable(false);
                    datePicker.setDisable(true);
                    predefinedQueries.setDisable(false);
                    textArea.setDisable(false);
                    button5.setDisable(false);

                    button3.setOnAction(event1 -> {
                        employeesDB.manualQueriesHourlyEmployeeTable(table, textArea.getText());
                        table.refresh();
                    });
                    button4.setOnAction(event2 -> {
                        employeesDB.resetHourlyEmployeesTable(table);
                        table.refresh();
                    });
                } else if (tableNames.getValue() == "Commission Only Employees") {
                    buildCorrespondingTable(4);
                    table.setVisible(true);
                    table.refresh();
                    button1.setDisable(true);
                    button2.setDisable(true);
                    button3.setDisable(false);
                    button4.setDisable(false);
                    datePicker.setDisable(true);
                    predefinedQueries.setDisable(false);
                    textArea.setDisable(false);
                    button5.setDisable(false);

                    button3.setOnAction(event1 -> {
                        employeesDB.manualQueriesCommissionEmployeeTable(table, textArea.getText());
                        table.refresh();
                    });
                    button4.setOnAction(event2 -> {
                        employeesDB.resetCommissionEmployeesTable(table);
                        table.refresh();
                    });
                } else if (tableNames.getValue() == "Base Plus Commission Employees") {
                    buildCorrespondingTable(5);
                    table.setVisible(true);
                    table.refresh();
                    button1.setDisable(true);
                    button2.setDisable(true);
                    button3.setDisable(false);
                    button4.setDisable(false);
                    datePicker.setDisable(true);
                    predefinedQueries.setDisable(false);
                    textArea.setDisable(false);
                    button5.setDisable(false);
                    button6.setVisible(true);

                    button3.setOnAction(event1 -> {
                        employeesDB.manualQueriesBasePlusCommissionEmployeeTable(table, textArea.getText());
                        table.refresh();
                    });
                    button4.setOnAction(event2 -> {
                        employeesDB.resetBasePlusCommissionEmployeesTable(table);
                        table.refresh();
                    });
                    button6.setOnAction(event3 -> {
                        employeesDB.addTenPercent(table);
                        table.refresh();
                    });
                }

                button5.setOnAction(event1 -> {
                    textArea.clear();
                });

                predefinedQueries.setOnAction(event1 -> {
                    if (predefinedQueries.getValue() == ("SELECT * FROM employees WHERE departmentName = " +
                            "\"SALES\"")) {
                        textArea.setText(predefinedQueries.getValue().toString());
                    } else if (predefinedQueries.getValue() == ("SELECT * FROM hourlyEmployees WHERE hours > 30")) {
                        textArea.setText(predefinedQueries.getValue().toString());
                    } else if (predefinedQueries.getValue() == ("SELECT * FROM commissionEmployees ORDER BY " +
                            "commissionRate")) {
                        textArea.setText(predefinedQueries.getValue().toString());
                    }
                });
            });

            root.setBottom(bottomContainer);
            root.setRight(rightContainer);
            root.setCenter(centerContainer);

            primaryStage.setScene(new Scene(root, 800, 600));
            primaryStage.show();
        }
        finally {
            employeesDB.resetBonuses();
        }
    }

    private void buildCorrespondingTable(int x){
        switch (x){
            case 1:
                table = employeesDB.buildEmployeesTable(table);
                break;
            case 2:
                table = employeesDB.buildSalariedEmployeesTable(table);
                break;
            case 3:
                table = employeesDB.buildHourlyEmployeesTable(table);
                break;
            case 4:
                table = employeesDB.buildCommissionEmployeesTable(table);
                break;
            case 5:
                table = employeesDB.buildBasePlusCommissionEmployeesTable(table);
                break;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
