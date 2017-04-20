package com.zkyyo.www.dao;

import com.zkyyo.www.db.DbClose;
import com.zkyyo.www.db.DbConn;
import com.zkyyo.www.po.EmployeePo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDao {
    public static final int UPDATE_DEPARTMENT_ID = 1;
    public static final int UPDATE_MOBILE = 2;
    public static final int UPDATE_SALARY = 3;
    public static final int UPDATE_EMAIL = 4;
    public static final int UPDATE_EMPLOYEE_DATE = 5;
    public static final int UPDATE_PASSWORD = 6;
    public static final int UPDATE_NAME = 7;
    private static volatile EmployeeDao INSTANCE = null;

    private EmployeeDao() {
    }

    public static EmployeeDao getInstance() {
        if (INSTANCE == null) {
            synchronized (EmployeeDao.class) {
                if (INSTANCE == null) {
                    INSTANCE = new EmployeeDao();
                }
            }
        }
        return INSTANCE;
    }

    public String loginCheck(int enterUserId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DbConn.getConn();
            String sql = "SELECT user_pwd FROM employee WHERE user_id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, enterUserId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("user_pwd");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return null;

    }

    public Integer createEmployeeId() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int largestNum = (int) Math.pow(10, 9);

        try {
            conn = DbConn.getConn();
            String sql = "SELECT user_id FROM employee WHERE user_id=?";
            stmt = conn.prepareStatement(sql);
            for (int i = 0; i < largestNum; i++) {
                int newUserId = (int) (largestNum * Math.random());
                if (newUserId != 0) {
                    stmt.setInt(1, newUserId);
                    rs = stmt.executeQuery();
                    if (!rs.next()) {
                        return newUserId;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, stmt, rs);
        }
        return null;
    }

    public boolean addEmployee(EmployeePo newEp) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean isUpdated = false;

        try {
            conn = DbConn.getConn();
            conn.setAutoCommit(false);
            String sql = "INSERT INTO employee (user_id, user_pwd, user_name, dept_id, mobile, salary, email, employee_date)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, newEp.getUserId());
            pstmt.setString(2, newEp.getPassword());
            pstmt.setString(3, newEp.getUserName());
            pstmt.setInt(4, newEp.getDeptId());
            pstmt.setString(5, newEp.getMobile());
            pstmt.setDouble(6, newEp.getSalary());
            pstmt.setString(7, newEp.getEmail());
            pstmt.setDate(8, newEp.getEmployDate());
            pstmt.executeUpdate();

            sql = "UPDATE department SET dept_population = dept_population + 1" +
                    " WHERE dept_id = (SELECT dept_id FROM employee WHERE user_id=?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, newEp.getUserId());
            pstmt.executeUpdate();

            conn.commit();
            isUpdated = true;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback();
                    conn.setAutoCommit(true);
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            DbClose.close(conn, pstmt);
        }
        return isUpdated;
    }

    public boolean deleteEmployee(int deletedUserId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean isDeleted = false;

        try {
            conn = DbConn.getConn();
            conn.setAutoCommit(false);
            String sql = "DELETE FROM employee WHERE user_id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, deletedUserId);
            pstmt.executeUpdate();

            sql = "UPDATE department SET dept_population = dept_population - 1" +
                    " WHERE dept_id = (SELECT dept_id FROM employee WHERE user_id=?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, deletedUserId);
            pstmt.executeUpdate();

            conn.commit();
            isDeleted = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt);
        }
        return isDeleted;
    }

    public EmployeePo selectEmployeeByUserId(int searchedUserId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DbConn.getConn();
            String sql = "SELECT * FROM employee WHERE user_id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, searchedUserId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                int eUserId = rs.getInt("user_id");
                String ePassword = rs.getString("user_pwd");
                String eName = rs.getString("user_name");
                int eDeptId = rs.getInt("dept_id");
                String eMobile = rs.getString("mobile");
                double eSalary = rs.getDouble("salary");
                String eEmail = rs.getString("email");
                java.sql.Date eEmployDate = rs.getDate("employee_date");

                return new EmployeePo(eUserId, ePassword, eName, eDeptId, eMobile, eSalary, eEmail, eEmployDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return null;
    }

    public List<EmployeePo> selectEmployees() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<EmployeePo> eps = new ArrayList<>();

        try {
            conn = DbConn.getConn();
            String sql = "SELECT * FROM employee";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int eUserId = rs.getInt("user_id");
                String ePassword = rs.getString("user_pwd");
                String eName = rs.getString("user_name");
                int eDeptId = rs.getInt("dept_id");
                String eMobile = rs.getString("mobile");
                double eSalary = rs.getDouble("salary");
                String eEmail = rs.getString("email");
                java.sql.Date eEmployDate = rs.getDate("employee_date");

                eps.add(new EmployeePo(eUserId, ePassword, eName, eDeptId,
                        eMobile, eSalary, eEmail, eEmployDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, stmt, rs);
        }
        return eps;
    }


    public List<EmployeePo> selectPossibleEmployeesByUserId(int userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<EmployeePo> eps = new ArrayList<>();

        try {
            conn = DbConn.getConn();
            String sql = "SELECT * FROM employee WHERE user_id LIKE ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + userId + "%");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int eUserId = rs.getInt("user_id");
                String ePassword = rs.getString("user_pwd");
                String eName = rs.getString("user_name");
                int eDeptId = rs.getInt("dept_id");
                String eMobile = rs.getString("mobile");
                double eSalary = rs.getDouble("salary");
                String eEmail = rs.getString("email");
                java.sql.Date eEmployDate = rs.getDate("employee_date");

                eps.add(new EmployeePo(eUserId, ePassword, eName, eDeptId,
                        eMobile, eSalary, eEmail, eEmployDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return eps;
    }

    public List<EmployeePo> selectPossibleEmployeesByUserName(String userName) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<EmployeePo> eps = new ArrayList<>();

        try {
            conn = DbConn.getConn();
            String sql = "SELECT * FROM employee WHERE user_name LIKE ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + userName + "%");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int eUserId = rs.getInt("user_id");
                String ePassword = rs.getString("user_pwd");
                String eName = rs.getString("user_name");
                int eDeptId = rs.getInt("dept_id");
                String eMobile = rs.getString("mobile");
                double eSalary = rs.getDouble("salary");
                String eEmail = rs.getString("email");
                java.sql.Date eEmployDate = rs.getDate("employee_date");

                eps.add(new EmployeePo(eUserId, ePassword, eName, eDeptId,
                        eMobile, eSalary, eEmail, eEmployDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return eps;
    }

    public List<EmployeePo> selectEmployeesByDeptId(int deptId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<EmployeePo> eps = new ArrayList<>();

        try {
            conn = DbConn.getConn();
            String sql = "SELECT * FROM employee WHERE dept_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, deptId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int eUserId = rs.getInt("user_id");
                String ePassword = rs.getString("user_pwd");
                String eName = rs.getString("user_name");
                int eDeptId = rs.getInt("dept_id");
                String eMobile = rs.getString("mobile");
                double eSalary = rs.getDouble("salary");
                String eEmail = rs.getString("email");
                java.sql.Date eEmployDate = rs.getDate("employee_date");

                eps.add(new EmployeePo(eUserId, ePassword, eName, eDeptId,
                        eMobile, eSalary, eEmail, eEmployDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return eps;
    }

    public boolean updateEmployee(List<Integer> updatedTypes, EmployeePo newEp) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean isUpdate = false;

        try {
            conn = DbConn.getConn();
            conn.setAutoCommit(false);
            String sql = null;
            for (int updatedType : updatedTypes) {
                switch (updatedType) {
                    //部门号
                    case UPDATE_DEPARTMENT_ID:
                        //原部门人数减1
                        sql = "UPDATE department SET dept_population = dept_population - 1" +
                                " WHERE dept_id = (SELECT dept_id FROM employee WHERE user_id=?)";
                        pstmt = conn.prepareStatement(sql);
                        pstmt.setInt(1, newEp.getUserId());
                        pstmt.executeUpdate();

                        //修改部门号
                        sql = "UPDATE employee SET dept_id=? WHERE user_id=?";
                        pstmt = conn.prepareStatement(sql);
                        pstmt.setInt(1, newEp.getDeptId());
                        pstmt.setInt(2, newEp.getUserId());
                        pstmt.executeUpdate();

                        //新部门人数加1
                        sql = "UPDATE department SET dept_population = dept_population + 1" +
                                " WHERE dept_id = (SELECT dept_id FROM employee WHERE user_id=?)";
                        pstmt = conn.prepareStatement(sql);
                        pstmt.setInt(1, newEp.getUserId());
                        pstmt.executeUpdate();
                        break;
                    //手机号
                    case UPDATE_MOBILE:
                        sql = "UPDATE employee SET mobile=? WHERE user_id=?";
                        pstmt = conn.prepareStatement(sql);
                        pstmt.setString(1, newEp.getMobile());
                        pstmt.setInt(2, newEp.getUserId());
                        pstmt.executeUpdate();
                        break;
                    //薪水
                    case UPDATE_SALARY:
                        sql = "UPDATE employee SET salary=? WHERE user_id=?";
                        pstmt = conn.prepareStatement(sql);
                        pstmt.setDouble(1, newEp.getSalary());
                        pstmt.setInt(2, newEp.getUserId());
                        pstmt.executeUpdate();
                        break;
                    //邮箱
                    case UPDATE_EMAIL:
                        sql = "UPDATE employee SET email=? WHERE user_id=?";
                        pstmt = conn.prepareStatement(sql);
                        pstmt.setString(1, newEp.getEmail());
                        pstmt.setInt(2, newEp.getUserId());
                        pstmt.executeUpdate();
                        break;
                    //就职日期
                    case UPDATE_EMPLOYEE_DATE:
                        sql = "UPDATE employee SET employee_date=? WHERE user_id=?";
                        pstmt = conn.prepareStatement(sql);
                        pstmt.setDate(1, newEp.getEmployDate());
                        pstmt.setInt(2, newEp.getUserId());
                        pstmt.executeUpdate();
                        break;
                    //密码
                    case UPDATE_PASSWORD:
                        sql = "UPDATE employee SET user_pwd=? WHERE user_id=?";
                        pstmt = conn.prepareStatement(sql);
                        pstmt.setString(1, newEp.getPassword());
                        pstmt.setInt(2, newEp.getUserId());
                        pstmt.executeUpdate();
                        break;
                    //姓名
                    case UPDATE_NAME:
                        sql = "UPDATE employee SET user_name=? WHERE user_id=?";
                        pstmt = conn.prepareStatement(sql);
                        pstmt.setString(1, newEp.getUserName());
                        pstmt.setInt(2, newEp.getUserId());
                        pstmt.executeUpdate();
                        break;
                    default:
                        break;
                }
            }
            conn.commit();
            isUpdate = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt);
        }
        return isUpdate;
    }
}
