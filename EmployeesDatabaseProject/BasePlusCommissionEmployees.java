package EmployeesDatabaseProject;

public class BasePlusCommissionEmployees {
    private String socialSecurityNumber;
    private String grossSales;
    private String commissionRate;
    private String baseSalary;
    private String bonus;

    public BasePlusCommissionEmployees(String socialSecurityNumber, String grossSales, String commissionRate,
                                       String baseSalary, String bonus) {

        this.socialSecurityNumber = socialSecurityNumber;
        this.grossSales = grossSales;
        this.commissionRate = commissionRate;
        this.baseSalary = baseSalary;
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

    public String getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(String baseSalary) {
        this.baseSalary = baseSalary;
    }

    public String getBonus() {
        return bonus;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }
}
