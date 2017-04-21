package com.zkyyo.www.dao;

import com.zkyyo.www.db.DbClose;
import com.zkyyo.www.db.DbConn;
import com.zkyyo.www.po.DepartmentPo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 该类包含与部门相关, 涉及数据库操作的代码
 */
public class DepartmentDao {
    /**
     * 更新部门名的标识符
     */
    public static final int UPDATE_NAME = 1;
    /**
     * 更新部门描述标识符
     */
    public static final int UPDATE_DESC = 2;
    /**
     * 更新建立日期标识符
     */
    public static final int UPDATE_BUILD_DATE = 3;

    /**
     * 用于创建懒汉模式下的一个单例, 默认为null
     */
    private static volatile DepartmentDao INSTANCE = null;

    /**
     * 禁止实例化新的对象
     */
    private DepartmentDao() {
    }

    /**
     * 创建一个该类的实例
     * @return 返回这个类的一个实例
     */
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

    /**
     * 检测数据库中是否存在指定部门号
     * @param id 待检测的部门号
     * @return 存在为true, 不存在为false
     */
    public boolean isIdExisted(int id) {
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

    /**
     * 检测数据库中是否存在指定部门名
     * @param name 待检测的部门名
     * @return 存在为true, 不存在为false
     */
    public boolean isNameExisted(String name) {
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

    /**
     * 向数据库中插入新部门
     * @param newDept 待插入的部门
     * @return 插入成功为true, 失败为false
     */
    public boolean addDepartment(DepartmentPo newDept) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean isAdded = false;

        try {
            conn = DbConn.getConn();
            String sql = "INSERT INTO department (dept_id, dept_name," +
                    "dept_population, description, build_date) VALUES (?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, newDept.getDeptId());
            pstmt.setString(2, newDept.getName());
            pstmt.setInt(3, newDept.getPopulation());
            pstmt.setString(4, newDept.getDescription());
            pstmt.setDate(5, newDept.getBuildDate());

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

    /**
     * 删除数据库中的指定部门
     * @param deletedDeptId 待删除的部门号
     * @return 删除成功为true, 失败为false
     */
    public boolean deleteDept(int deletedDeptId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean isDeleted = false;

        try {
            conn = DbConn.getConn();
            conn.setAutoCommit(false);
            //删除部门
            String sql = "DELETE FROM department WHERE dept_id=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, deletedDeptId);
            stmt.executeUpdate();

            //相应员工的部门号设为-1,表示待业
            sql = "UPDATE employee SET dept_id = -1 WHERE dept_id=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, deletedDeptId);
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

    /**
     * 通过部门号查询数据库中的部门
     * @param searchedDeptId 待查询的部门号
     * @return 找到返回该部门对象, 否则返回nul
     */
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
                java.sql.Date buildDate = rs.getDate("build_date");

                return new DepartmentPo(deptId, deptName,
                        deptPopulation, description, buildDate);
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
                java.sql.Date buildDate = rs.getDate("build_date");

                return new DepartmentPo(deptId, deptName,
                        deptPopulation, description, buildDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return null;
    }
    */

    /**
     * 通过员工号查询该员工所在的部门
     * @param userId 员工号
     * @return 若找到返回该部门对象, 否则返回bull
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
                    java.sql.Date buildDate = rs.getDate("build_date");

                    return new DepartmentPo(deptId, deptName,
                            deptPopulation, description, buildDate);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return null;
    }

    /*
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
                    java.sql.Date buildDate = rs.getDate("build_date");

                    return new DepartmentPo(deptId, deptName,
                            deptPopulation, description, buildDate);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return null;
    }
    */

    /**
     * 模糊查询数据库中的部门, 以部门号为查询依据
     * @param searchedDeptId 可能的部门号
     * @return 符合的部门列表
     */
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
                java.sql.Date buildDate = rs.getDate("build_date");

                depts.add(new DepartmentPo(deptId, deptName, deptPopulation, description, buildDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return depts;
    }

    /**
     * 模糊查询数据库中的部门, 以部门名为查询依据
     * @param searcheDdeptName 可能的部门名
     * @return 符合的部门列表
     */
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
                java.sql.Date buildDate = rs.getDate("build_date");

                depts.add(new DepartmentPo(deptId, deptName, deptPopulation, description, buildDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return depts;
    }

    /**
     * 获得数据库中的所有部门
     * @return 包含所有部门对象的列表
     */
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
                java.sql.Date buildDate = rs.getDate("build_date");

                DepartmentPo dept = new DepartmentPo(deptId, deptName,
                        deptPopulation, description, buildDate);
                depts.add(dept);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, stmt, rs);
        }
        return depts;
    }

    /*
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
                    pstmt.setString(1, newDept.getName());
                    pstmt.setInt(2, updateDeptId);
                    effects = pstmt.executeUpdate();
                    break;
                //部门描述
                case 2:
                    sql = "UPDATE department SET description=? WHERE dept_id=?";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, newDept.getDescription());
                    pstmt.setInt(2, updateDeptId);
                    effects = pstmt.executeUpdate();
                    break;
                //建立时间
                case 3:
                    sql = "UPDATE department SET build_date=? WHERE dept_id=?";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setDate(1, newDept.getBuildDate());
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
    */

    /**
     * 更新数据库中的指定部门
     * @param updatedTypes 待更新类型的标识符列表
     * @param updatedDept 包含最新部门信息的部门对象
     * @return 更新成功为true, 失败为false
     */
    public boolean updateDept(List<Integer> updatedTypes, DepartmentPo updatedDept) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean isUpdated = false;

        try {
            conn = DbConn.getConn();
            conn.setAutoCommit(false);
            String sql = null;
            for (int updatedType : updatedTypes) {
                switch (updatedType) {
                    //部门名
                    case UPDATE_NAME:
                        sql = "UPDATE department SET dept_name=? WHERE dept_id=?";
                        pstmt = conn.prepareStatement(sql);
                        pstmt.setString(1, updatedDept.getName());
                        pstmt.setInt(2, updatedDept.getDeptId());
                        pstmt.executeUpdate();
                        break;
                    //部门描述
                    case UPDATE_DESC:
                        sql = "UPDATE department SET description=? WHERE dept_id=?";
                        pstmt = conn.prepareStatement(sql);
                        pstmt.setString(1, updatedDept.getDescription());
                        pstmt.setInt(2, updatedDept.getDeptId());
                        pstmt.executeUpdate();
                        break;
                    //建立时间
                    case UPDATE_BUILD_DATE:
                        sql = "UPDATE department SET build_date=? WHERE dept_id=?";
                        pstmt = conn.prepareStatement(sql);
                        pstmt.setDate(1, updatedDept.getBuildDate());
                        pstmt.setInt(2, updatedDept.getDeptId());
                        pstmt.executeUpdate();
                        break;
                }
            }
            conn.commit();
            isUpdated = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt);
        }
        return isUpdated;
    }
}
