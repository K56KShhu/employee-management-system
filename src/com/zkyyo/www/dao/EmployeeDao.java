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

/**
 * 该类包含与员工相关, 涉及数据库操作的代码
 */
public class EmployeeDao {
    /**
     * 更新部门号的标识符
     */
    public static final int UPDATE_DEPARTMENT_ID = 1;
    /**
     * 更新手机号的标识符
     */
    public static final int UPDATE_MOBILE = 2;
    /**
     * 更新薪水的标识符
     */
    public static final int UPDATE_SALARY = 3;
    /**
     * 更新邮箱的标识符
     */
    public static final int UPDATE_EMAIL = 4;
    /**
     * 更新就职日期的标识符
     */
    public static final int UPDATE_EMPLOYEE_DATE = 5;
    /**
     * 更新密码的标识符
     */
    public static final int UPDATE_PASSWORD = 6;
    /**
     * 更新员工名的标识符
     */
    public static final int UPDATE_NAME = 7;
    /**
     * 员工号允许生成的最大长度
     */
    private static final int USER_ID_LENGTH = 9;
    /**
     * 用于创建懒汉模式下的一个单例, 默认为null
     */
    private static volatile EmployeeDao INSTANCE = null;

    /**
     * 禁止实例化新的对象
     */
    private EmployeeDao() {
    }

    /**
     * 创建一个该类的实例
     * @return 返回这个类的一个实例
     */
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

    /**
     * 获取指定员工在数据库的密码
     * @param enterUserId 指定的员工号
     * @return 正确的密码字符串
     */
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

    /**
     * 随机生成指定长度的员工号
     * @return 生成的员工号
     */
    public Integer createEmployeeId() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int largestNum = (int) Math.pow(10, USER_ID_LENGTH);

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

    /**
     * 向数据库中插入新员工
     * @param newEp 待插入的员工对象
     * @return 插入成功为true, 失败为false
     */
    public boolean addEmployee(EmployeePo newEp) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean isUpdated = false;

        try {
            conn = DbConn.getConn();
            conn.setAutoCommit(false);
            String sql = "INSERT INTO employee (user_id, user_pwd, user_name, dept_id," +
                    " mobile, salary, email, employee_date)" +
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

    /**
     * 删除数据库中指定的员工
     * @param deletedUserId 待删除的员工号
     * @return 删除成功为true, 失败为false
     */
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

    /**
     * 精确获得数据库中的员工信息, 以员工号为查询依据
     * @param searchedUserId 待查询的员工号
     * @return 查询成功返回该员工对象, 失败返回null
     */
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

    /**
     * 获得数据库中的所有员工
     * @return 包含所有员工的列表
     */
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

    /**
     * 模糊查询数据中的员工, 以员工号为查询依据
     * @param userId 可能的员工号
     * @return 符合的员工列表
     */
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

    /**
     * 模糊查询数据库中员工, 以员工名为查询依据
     * @param userName 可能的员工名
     * @return 符合的员工列表
     */
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

    /**
     * 查询制定部门的所有员工
     * @param deptId 指定的部门号
     * @return 同一部门的员工列表
     */
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

    /**
     * 更新数据库中的员工信息
     * @param updatedTypes 待更新类型的标识符列表
     * @param newEp 包含最新员工信息的员工对象
     * @return 更新成功为true, 失败为false
     */
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
