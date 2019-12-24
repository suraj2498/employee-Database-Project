package EmployeesDatabaseProject;

public class CommissionEmployee {
    private String socialSecurityNumber;
    private String grossSales;
    private String commissionRate;
    private String bonus;

    public CommissionEmployee(String socialSecurityNumber, String grossSales, String commissionRate, String bonus) {
        this.socialSecurityNumber = socialSecurityNumber;
        this.grossSales = grossSales;
        this.commissionRate = commissionRate;
        this.bonus = bonus;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public String getGrossSales() {
        return grossSales;
    }

    public void setGrossSales(String grossSales) {
        this.grossSales = grossSales;
    }

    public String getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(String commissionRate) {
        this.commissionRate = commissionRate;
    }

    public String getBonus() {
        return bonus;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }
}
