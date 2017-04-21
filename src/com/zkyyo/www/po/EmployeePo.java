package com.zkyyo.www.po;

import java.sql.Date;
import java.util.Objects;

/**
 * 部门的实体类
 */
public class EmployeePo {
    private int userId; //员工号 1-10位大于0的数字
    private String password; //密码
    private String userName; //员工名
    private int deptId; //员工所在部门号
    private String mobile; //手机号
    private double salary; //薪水
    private String email; //邮箱
    private Date employDate; //就职日期

    /**
     * 默认构造方法
     * 用于更新员工信息, 只需提供需要修改的信息
     */
    public EmployeePo() {

    }

    /**
     * 构造方法
     * 用于添加, 删除, 查询员工信息
     * @param userId 员工号
     * @param password 密码
     * @param userName 员工名
     * @param deptId 员工所在部门号
     * @param mobile 手机号
     * @param salary 薪水
     * @param email 邮箱
     * @param employDate 就职日期
     */
    public EmployeePo(int userId, String password, String userName, int deptId,
                      String mobile, double salary, String email, Date employDate) {
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.deptId = deptId;
        this.mobile = mobile;
        this.salary = salary;
        this.email = email;
        this.employDate = employDate;
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

