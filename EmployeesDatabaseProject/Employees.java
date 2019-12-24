package EmployeesDatabaseProject;

public class Employees {
    private String SSN;
    private String firstName;
    private String lastName;
    private String birthday;
    private String employeeType;
    private String departmentName;

    public Employees(String SSN, String firstName, String lastName, String birthday, String employeeType, String departmentName) {
        this.SSN = SSN;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.employeeType = employeeType;
        this.departmentName = departmentName;
    }

    public String getSSN() {
        return SSN;
    }

    public void setSSN(String SSN) {
        this.SSN = SSN;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
