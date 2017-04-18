package com.zkyyo.www.dao;

import com.zkyyo.www.db.DbClose;
import com.zkyyo.www.db.DbConn;
import com.zkyyo.www.po.DepartmentPo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDao {
    private static volatile DepartmentDao INSTANCE = null;

    private DepartmentDao() {
    }

    public static DepartmentDao getInstance() {
        if (INSTANCE == null) {
            synchronized (DepartmentDao.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DepartmentDao();
                }
            }
        }
        return INSTANCE;
    }

    public boolean isAvailableId(int id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DbConn.getConn();
            String sql = "SELECT dept_id FROM department WHERE dept_id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            return !rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return false;
    }

    public boolean isAvailableName(String name) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DbConn.getConn();
            String sql = "SELECT dept_name FROM department WHERE dept_name=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            return !rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return false;
    }

    public boolean addDepartment(DepartmentPo newDept) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean isAdded = false;

        try {
            conn = DbConn.getConn();
            String sql = "INSERT INTO department (dept_id, dept_name," +
                    "dept_population, description, built_date) VALUES (?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, newDept.getDeptId());
            pstmt.setString(2, newDept.getDeptName());
            pstmt.setInt(3, newDept.getDeptPopulation());
            pstmt.setString(4, newDept.getDeptDesc());
            pstmt.setDate(5, newDept.getBuiltDate());

            int effects = pstmt.executeUpdate();
            if (effects > 0) {
                isAdded = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt);
        }
        return isAdded;
    }

    public DepartmentPo selectDepartmentByDeptId(int searchedDeptId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DbConn.getConn();
            String sql = "SELECT * FROM department WHERE dept_id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, searchedDeptId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                int deptId = rs.getInt("dept_id");
                String deptName = rs.getString("dept_name");
                int deptPopulation = rs.getInt("dept_population");
                String description = rs.getString("description");
                java.sql.Date builtDate = rs.getDate("built_date");

                return new DepartmentPo(deptId, deptName,
                        deptPopulation, description, builtDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return null;
    }

    /*
    public DepartmentPo selectDepartmentByDeptName(String searchedDeptName) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DbConn.getConn();
            String sql = "SELECT * FROM department WHERE dept_name=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, searchedDeptName);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                int deptId = rs.getInt("dept_id");
                String deptName = rs.getString("dept_name");
                int deptPopulation = rs.getInt("dept_population");
                String description = rs.getString("description");
                java.sql.Date builtDate = rs.getDate("built_date");

                return new DepartmentPo(deptId, deptName,
                        deptPopulation, description, builtDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return null;
    }
    */

    public DepartmentPo selectDepartmentByUserId(int userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DbConn.getConn();
            String sql = "SELECT dept_id FROM employee WHERE user_id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                int searchedDeptId = rs.getInt("dept_id");
                sql = "SELECT * FROM department WHERE dept_id=?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, searchedDeptId);
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    int deptId = rs.getInt("dept_id");
                    String deptName = rs.getString("dept_name");
                    int deptPopulation = rs.getInt("dept_population");
                    String description = rs.getString("description");
                    java.sql.Date builtDate = rs.getDate("built_date");

                    return new DepartmentPo(deptId, deptName,
                            deptPopulation, description, builtDate);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return null;
    }

    public DepartmentPo selectDepartmentByUserName(String userName) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DbConn.getConn();
            String sql = "SELECT dept_id FROM employee WHERE user_name=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userName);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                int searchedDeptId = rs.getInt("dept_id");
                sql = "SELECT * FROM department WHERE dept_id=?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, searchedDeptId);
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    int deptId = rs.getInt("dept_id");
                    String deptName = rs.getString("dept_name");
                    int deptPopulation = rs.getInt("dept_population");
                    String description = rs.getString("description");
                    java.sql.Date builtDate = rs.getDate("built_date");

                    return new DepartmentPo(deptId, deptName,
                            deptPopulation, description, builtDate);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return null;
    }

    public List<DepartmentPo> selectPossibleDepartmentsByDeptId(int searchedDeptId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<DepartmentPo> depts = new ArrayList<>();

        try {
            conn = DbConn.getConn();
            String sql = "SELECT * FROM department WHERE dept_id LIKE ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + searchedDeptId + "%");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int deptId = rs.getInt("dept_id");
                String deptName = rs.getString("dept_name");
                int deptPopulation = rs.getInt("dept_population");
                String description = rs.getString("description");
                java.sql.Date builtDate = rs.getDate("built_date");

                depts.add(new DepartmentPo(deptId, deptName, deptPopulation, description, builtDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return depts;
    }

    public List<DepartmentPo> selectPossibleDepartmentByDeptName(String searcheDdeptName) {
        Connection conn = DbConn.getConn();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<DepartmentPo> depts = new ArrayList<>();

        try {
            conn = DbConn.getConn();
            String sql = "SELECT * FROM department WHERE dept_name LIKE ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + searcheDdeptName + "%");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int deptId = rs.getInt("dept_id");
                String deptName = rs.getString("dept_name");
                int deptPopulation = rs.getInt("dept_population");
                String description = rs.getString("description");
                java.sql.Date builtDate = rs.getDate("built_date");

                depts.add(new DepartmentPo(deptId, deptName, deptPopulation, description, builtDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return depts;
    }

    public List<DepartmentPo> selectDepartments() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<DepartmentPo> depts = new ArrayList<>();

        try {
            conn = DbConn.getConn();
            String sql = "SELECT * FROM department";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int deptId = rs.getInt("dept_id");
                String deptName = rs.getString("dept_name");
                int deptPopulation = rs.getInt("dept_population");
                String description = rs.getString("description");
                java.sql.Date builtDate = rs.getDate("built_date");

                DepartmentPo dept = new DepartmentPo(deptId, deptName,
                        deptPopulation, description, builtDate);
                depts.add(dept);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, stmt, rs);
        }
        return depts;
    }

    public boolean deleteDept(int deleteddeptId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean isDeleted = false;

        try {
            conn = DbConn.getConn();
            conn.setAutoCommit(false);
            //删除部门
            String sql = "DELETE FROM department WHERE dept_id=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, deleteddeptId);
            stmt.executeUpdate();

            //相应员工的部门号设为-1,表示待业
            sql = "UPDATE employee SET dept_id = -1 WHERE dept_id=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, deleteddeptId);
            stmt.executeUpdate();

            conn.commit();
            isDeleted = true;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            DbClose.close(conn, stmt);
        }
        return isDeleted;
    }

    public boolean updateDept(int updateDeptId, int type, DepartmentPo newDept) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean isUpdated = false;

        try {
            conn = DbConn.getConn();
            String sql;
            int effects = 0;
            switch (type) {
                //部门名
                case 1:
                    sql = "UPDATE department SET dept_name=? WHERE dept_id=?";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, newDept.getDeptName());
                    pstmt.setInt(2, updateDeptId);
                    effects = pstmt.executeUpdate();
                    break;
                //部门描述
                case 2:
                    sql = "UPDATE department SET description=? WHERE dept_id=?";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, newDept.getDeptDesc());
                    pstmt.setInt(2, updateDeptId);
                    effects = pstmt.executeUpdate();
                    break;
                //建立时间
                case 3:
                    sql = "UPDATE department SET built_date=? WHERE dept_id=?";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setDate(1, newDept.getBuiltDate());
                    pstmt.setInt(2, updateDeptId);
                    effects = pstmt.executeUpdate();
                    break;
            }
            if (effects > 0) {
                isUpdated = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt);
        }
        return isUpdated;
    }

}
