package EmployeesDatabaseProject;

public class hourlyEmployees {
    private String socialSecurityNumber;
    private String hours;
    private String wage;
    private String bonus;

    public hourlyEmployees(String socialSecurityNumber, String hours, String wage, String bonus) {
        this.socialSecurityNumber = socialSecurityNumber;
        this.hours = hours;
        this.wage = wage;
        this.bonus = bonus;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getWage() {
        return wage;
    }

    public void setWage(String wage) {
        this.wage = wage;
    }

    public String getBonus() {
        return bonus;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }
}
