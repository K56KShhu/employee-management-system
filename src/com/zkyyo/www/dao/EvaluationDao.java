package com.zkyyo.www.dao;

import com.zkyyo.www.db.DbClose;
import com.zkyyo.www.db.DbConn;
import com.zkyyo.www.po.EvaluationPo;

import java.sql.*;
import java.util.*;

public class EvaluationDao {
    public static final int UPDATE_STARS = 1;
    public static final int UPDATE_COMMENT = 2;
    private static volatile EvaluationDao INSTANCE = null;

    private EvaluationDao() {
    }

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

    public boolean addEvaluation(EvaluationPo newEval) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean isAdded = false;

        try {
            conn = DbConn.getConn();
            String sql = "INSERT INTO evaluation (evaluator_id, be_evaluated_id, star_level, comment) VALUES (?, ?, ?, ?)";
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
                int evaluationId = rs.getInt("test_evaluation_id");
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
                int evaluationId = rs.getInt("test_evaluation_id");
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

    public EvaluationPo selectEvaluation(int evalId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        EvaluationPo eval = null;

        try {
            conn = DbConn.getConn();
            String sql = "SELECT * FROM evaluation WHERE test_evaluation_id=?";
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
                //将数据库中的主键作为评价的唯一标识id
                int evaluationId = rs.getInt("test_evaluation_id");
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
                    int evaluationId = rs.getInt("test_evaluation_id");
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

    public boolean updateEvaluation(List<Integer> updatedTypes, EvaluationPo eval) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean isUpdated = false;

        try {
            conn = DbConn.getConn();
            conn.setAutoCommit(false);
            String sql;
            for (int updateType : updatedTypes) {
                switch (updateType) {
                    case 1:
                        sql = "UPDATE evaluation SET star_level=? WHERE test_evaluation_id=?";
                        pstmt = conn.prepareStatement(sql);
                        pstmt.setInt(1, eval.getStarLevel());
                        pstmt.setInt(2, eval.getEvaluationId());
                        pstmt.executeUpdate();
                        break;
                    case 2:
                        sql = "UPDATE evaluation SET comment=? WHERE test_evaluation_id=?";
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

    public boolean deleteEvaluation(int evaluationId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean isDeleted = false;

        try {
            conn = DbConn.getConn();
            String sql = "DELETE FROM evaluation WHERE test_evaluation_id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, evaluationId);
            int effects = pstmt.executeUpdate();
            if (effects > 0) {
                isDeleted = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt);
        }

        return isDeleted;
    }
}
