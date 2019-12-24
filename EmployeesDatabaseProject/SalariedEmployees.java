package EmployeesDatabaseProject;

public class SalariedEmployees {
    private String socialSecurityNumber;
    private String weeklySalary;
    private String bonus;

    public SalariedEmployees(String socialSecurityNumber, String weeklySalary, String bonus) {
        this.socialSecurityNumber = socialSecurityNumber;
        this.weeklySalary = weeklySalary;
        this.bonus = bonus;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public String getWeeklySalary() {
        return weeklySalary;
    }

    public void setWeeklySalary(String weeklySalary) {
        this.weeklySalary = weeklySalary;
    }

    public String getBonus() {
        return bonus;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }
}
