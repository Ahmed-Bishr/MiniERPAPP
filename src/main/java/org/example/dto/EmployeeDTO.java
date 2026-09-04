package org.example.dto;

/**
 * Data container for a single employee row.
 * Maps to the {@code employees} table plus the joined
 * branch/role names used for display. No SQL or business logic here.
 */
public class EmployeeDTO {

    private int employeeId;
    private int branchId;
    private int roleId;
    private String name;
    private String jobTitle;
    private double salary;
    private String phone;
    private String hireDate;
    private String password;
    private String status;
    private String branchName;
    private String roleName;

    public EmployeeDTO() {
        this(0, 0, 0, "", "", 0, "", "", "", "", "", "");
    }

    public EmployeeDTO(int employeeId, int branchId, int roleId, String name,
                       String jobTitle, double salary, String phone, String hireDate,
                       String password, String status, String branchName, String roleName) {
        this.employeeId = employeeId;
        this.branchId = branchId;
        this.roleId = roleId;
        this.name = name;
        this.jobTitle = jobTitle;
        this.salary = salary;
        this.phone = phone;
        this.hireDate = hireDate;
        this.password = password;
        this.status = status;
        this.branchName = branchName;
        this.roleName = roleName;
    }

    public int getEmployeeId() { return employeeId; }
    public void setEmployeeId(int employeeId) { this.employeeId = employeeId; }

    public int getBranchId() { return branchId; }
    public void setBranchId(int branchId) { this.branchId = branchId; }

    public int getRoleId() { return roleId; }
    public void setRoleId(int roleId) { this.roleId = roleId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getHireDate() { return hireDate; }
    public void setHireDate(String hireDate) { this.hireDate = hireDate; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getBranchName() { return branchName; }
    public void setBranchName(String branchName) { this.branchName = branchName; }

    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }
}