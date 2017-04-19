package com.zkyyo.www.po;

import java.sql.Date;
import java.util.Objects;

public class EmployeePo {
    private int userId;
    private String password;
    private String userName;
    private int deptId;
    private String mobile;
    private double salary;
    private String email;
    private Date employDate;

    public EmployeePo(int eUserId, String password, String userName, int deptId,
                      String mobile, double salary, String email, Date employDate) {
        this.userId = eUserId;
        this.password = password;
        this.userName = userName;
        this.deptId = deptId;
        this.mobile = mobile;
        this.salary = salary;
        this.email = email;
        this.employDate = employDate;
    }

    /**
     * 作为默认构造函数，用于添加新员工
     */
    public EmployeePo() {

    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getEmployDate() {
        return employDate;
    }

    public void setEmployDate(Date employDate) {
        this.employDate = employDate;
    }

    @Override
    public String toString() {
        return "EmployeePo{" +
                "userId=" + userId +
                ", password='" + password + '\'' +
                ", userName='" + userName + '\'' +
                ", deptId=" + deptId +
                ", mobile='" + mobile + '\'' +
                ", salary=" + salary +
                ", email='" + email + '\'' +
                ", employDate='" + employDate + '\'' +
                '}';
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        if (!(obj instanceof EmployeePo)) {
            return false;
        }
        EmployeePo other = (EmployeePo) obj;
        return userId == other.userId
                && password.equals(other.password)
                && userName.equals(other.userName)
                && deptId == other.getDeptId()
                && mobile.equals(other.mobile)
                && salary == other.getSalary()
                && email.equals(other.email)
                && employDate.equals(other.getEmployDate());
    }
    
    public int hashCode() {
        return Objects.hash(userId, password, userName, deptId, mobile, salary, email, employDate);
    }
}

