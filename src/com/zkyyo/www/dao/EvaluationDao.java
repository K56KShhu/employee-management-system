package com.zkyyo.www.dao;

import com.zkyyo.www.db.DbClose;
import com.zkyyo.www.db.DbConn;
import com.zkyyo.www.po.EvaluationPo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * 该类包含与评价相关, 涉及数据库操作的代码
 */
public class EvaluationDao {
    /**
     * 更新评价等级的标识符
     */
    public static final int UPDATE_STARS = 1;
    /**
     * 更新评价内容的标识符
     */
    public static final int UPDATE_COMMENT = 2;
    /**
     * 用于创建懒汉模式下的一个单例, 默认为null
     */
    private static volatile EvaluationDao INSTANCE = null;

    /**
     * 禁止实例化新的对象
     */
    private EvaluationDao() {
    }

    /**
     * 创建一个该类的实例
     *
     * @return 返回这个类的一个实例
     */
    public static EvaluationDao getInstance() {
        if (INSTANCE == null) {
            synchronized (EvaluationDao.class) {
                if (INSTANCE == null) {
                    INSTANCE = new EvaluationDao();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 向数据库中插入新评价
     *
     * @param newEval 待插入的评价对象
     * @return 插入成功为true, 失败为false
     */
    public boolean addEvaluation(EvaluationPo newEval) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean isAdded = false;

        try {
            conn = DbConn.getConn();
            String sql = "INSERT INTO evaluation (evaluator_id, be_evaluated_id, star_level, comment)" +
                    " VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, newEval.getEvaluatorId());
            pstmt.setInt(2, newEval.getBeEvaluatedId());
            pstmt.setInt(3, newEval.getStarLevel());
            pstmt.setString(4, newEval.getComment());
            pstmt.executeUpdate();
            isAdded = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt);
        }
        return isAdded;
    }

    /**
     * 删除数据中指定的评价
     *
     * @param evaluationId 待删除的评价号
     * @return 删除成功为true, 失败为false
     */
    public boolean deleteEvaluation(int evaluationId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean isDeleted = false;

        try {
            conn = DbConn.getConn();
            String sql = "DELETE FROM evaluation WHERE id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, evaluationId);
            pstmt.executeUpdate();
            isDeleted = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt);
        }
        return isDeleted;
    }

    /**
     * 查询数据库中员工发送的评价, 以发送者员工号为查询依据
     *
     * @param userId 发送者的员工号
     * @return 发送的评价列表
     */
    public List<EvaluationPo> selectSendedEvaluations(int userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<EvaluationPo> evals = new ArrayList<>();

        try {
            conn = DbConn.getConn();
            String sql = "SELECT * FROM evaluation WHERE evaluator_id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int evaluationId = rs.getInt("id");
                int beEvaluatedId = rs.getInt("be_evaluated_id");
                int starLevel = rs.getInt("star_level");
                String comment = rs.getString("comment");
                evals.add(new EvaluationPo(evaluationId, userId, beEvaluatedId, starLevel, comment));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return evals;
    }

    /**
     * 查询数据库中员工收到的评价, 以接收者员工号为查询依据
     *
     * @param userId 接收者的员工号
     * @return 接收的评价列表
     */
    public List<EvaluationPo> selectReceivedEvaluations(int userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<EvaluationPo> evals = new ArrayList<>();

        try {
            conn = DbConn.getConn();
            String sql = "SELECT * FROM evaluation WHERE be_evaluated_id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int evaluationId = rs.getInt("id");
                int evaluatorId = rs.getInt("evaluator_id");
                int starLevel = rs.getInt("star_level");
                String comment = rs.getString("comment");
                evals.add(new EvaluationPo(evaluationId, evaluatorId, userId, starLevel, comment));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return evals;
    }

    /**
     * 获取数据库中指定评价号的评价
     *
     * @param evalId 指定的评价号
     * @return 查询成功返回该评价对象, 失败返回null
     */
    public EvaluationPo selectEvaluation(int evalId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        EvaluationPo eval = null;

        try {
            conn = DbConn.getConn();
            String sql = "SELECT * FROM evaluation WHERE id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, evalId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                int evaluatorId = rs.getInt("evaluator_id");
                int beEvaluatedId = rs.getInt("be_evaluated_id");
                int starLevel = rs.getInt("star_level");
                String comment = rs.getString("comment");
                eval = new EvaluationPo(evalId, evaluatorId, beEvaluatedId, starLevel, comment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return eval;
    }

    /**
     * 查询数据库中的所有评价
     *
     * @return 包含所有评价对象的列表
     */
    public List<EvaluationPo> selectEvaluations() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<EvaluationPo> evals = new ArrayList<>();

        try {
            conn = DbConn.getConn();
            String sql = "SELECT * FROM evaluation";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                //将数据库中的主键作为评价的唯一标识
                int evaluationId = rs.getInt("id");
                int beEvaluatedId = rs.getInt("be_evaluated_id");
                int evaluatorId = rs.getInt("evaluator_id");
                int starLevel = rs.getInt("star_level");
                String comment = rs.getString("comment");
                evals.add(new EvaluationPo(evaluationId, evaluatorId, beEvaluatedId, starLevel, comment));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, stmt, rs);
        }
        return evals;
    }

    /*
    public Map<Integer, EvaluationPo> selectEvaluationsByKeyWords(Set<String> keys) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Map<Integer, EvaluationPo> evals = new HashMap<>();

        try {
            conn = DbConn.getConn();
            conn.setAutoCommit(false);
            for (String key : keys) {
                String sql = "SELECT * FROM evaluation WHERE comment LIKE ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, "%" + key + "%");
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    int evaluationId = rs.getInt("id");
                    int beEvaluatedId = rs.getInt("be_evaluated_id");
                    int evaluatorId = rs.getInt("evaluator_id");
                    int starLevel = rs.getInt("star_level");
                    String comment = rs.getString("comment");
                    EvaluationPo eval = new EvaluationPo(evaluationId, evaluatorId, beEvaluatedId, starLevel, comment);
                    evals.put(evaluationId, eval);
                }
            }
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return evals;
    }
    */

    /**
     * 模糊查询数据库中的评价, 查询依据为评价内容的关键字
     *
     * @param keys 评价内容的关键字集合
     * @return 符合的评价列表
     */
    public Set<EvaluationPo> selectEvaluationsByKeyWords(Set<String> keys) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Set<EvaluationPo> evals = new HashSet<>();

        try {
            conn = DbConn.getConn();
            conn.setAutoCommit(false);
            //每个关键字搜索一次
            for (String key : keys) {
                String sql = "SELECT * FROM evaluation WHERE comment LIKE ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, "%" + key + "%");
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    int evaluationId = rs.getInt("id");
                    int beEvaluatedId = rs.getInt("be_evaluated_id");
                    int evaluatorId = rs.getInt("evaluator_id");
                    int starLevel = rs.getInt("star_level");
                    String comment = rs.getString("comment");
                    evals.add(new EvaluationPo(evaluationId, evaluatorId, beEvaluatedId, starLevel, comment));
                }
            }
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return evals;
    }

    /**
     * 更新数据库中评价
     *
     * @param updatedTypes 待更新类型的标识符列表
     * @param eval         包含最新评价信息的评价对象
     * @return 更新成功为true, 失败为false
     */
    public boolean updateEvaluation(List<Integer> updatedTypes, EvaluationPo eval) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean isUpdated = false;

        try {
            conn = DbConn.getConn();
            conn.setAutoCommit(false);
            String sql;
            //更新每一个指定的类型
            for (int updateType : updatedTypes) {
                switch (updateType) {
                    //评价等级
                    case 1:
                        sql = "UPDATE evaluation SET star_level=? WHERE id=?";
                        pstmt = conn.prepareStatement(sql);
                        pstmt.setInt(1, eval.getStarLevel());
                        pstmt.setInt(2, eval.getEvaluationId());
                        pstmt.executeUpdate();
                        break;
                    //评价内容
                    case 2:
                        sql = "UPDATE evaluation SET comment=? WHERE id=?";
                        pstmt = conn.prepareStatement(sql);
                        pstmt.setString(1, eval.getComment());
                        pstmt.setInt(2, eval.getEvaluationId());
                        pstmt.executeUpdate();
                        break;
                    default:
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
