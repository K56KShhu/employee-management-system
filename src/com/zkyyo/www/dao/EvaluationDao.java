package com.zkyyo.www.dao;

import com.zkyyo.www.db.DbClose;
import com.zkyyo.www.db.DbConn;
import com.zkyyo.www.po.EvaluationPo;

import java.sql.*;
import java.util.Map;
import java.util.TreeMap;

public class EvaluationDao {
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

    public Map<Integer, EvaluationPo> selectEvaluationMapByEvaluatorId(int evaluatorId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Map<Integer, EvaluationPo> evals = new TreeMap<>();

        try {
            conn = DbConn.getConn();
            String sql = "SELECT * FROM evaluation WHERE evaluator_id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, evaluatorId);
            rs = pstmt.executeQuery();

            int index = 1;
            while (rs.next()) {
                int evaluationId = rs.getInt("test_evaluation_id");
                int beEvaluatedId = rs.getInt("be_evaluated_id");
                int starLevel = rs.getInt("star_level");
                String comment = rs.getString("comment");

                EvaluationPo eval = new EvaluationPo(evaluationId, evaluatorId, beEvaluatedId, starLevel, comment);
                evals.put(index, eval);
                index++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return evals;
    }

    public Map<Integer, EvaluationPo> selectEvaluationMapByBeEvaluatedId(int beEvaluatedId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Map<Integer, EvaluationPo> evals = new TreeMap<>();

        try {
            conn = DbConn.getConn();
            String sql = "SELECT * FROM evaluation WHERE be_evaluated_id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, beEvaluatedId);
            rs = pstmt.executeQuery();

            int index = 1;
            while (rs.next()) {
                int evaluationId = rs.getInt("test_evaluation_id");
                int evaluatorId = rs.getInt("evaluator_id");
                int starLevel = rs.getInt("star_level");
                String comment = rs.getString("comment");

                EvaluationPo eval = new EvaluationPo(evaluationId, evaluatorId, beEvaluatedId, starLevel, comment);
                evals.put(index, eval);
                index++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, pstmt, rs);
        }
        return evals;
    }

    public Map<Integer, EvaluationPo> selectEvaluationMap() {
        Map<Integer, EvaluationPo> evals = new TreeMap<>();
        Connection conn = DbConn.getConn();
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DbConn.getConn();
            String sql = "SELECT * FROM evaluation";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            int index = 1;
            while (rs.next()) {
                int evaluationId = rs.getInt("test_evaluation_id");
                int beEvaluatedId = rs.getInt("be_evaluated_id");
                int evaluatorId = rs.getInt("evaluator_id");
                int starLevel = rs.getInt("star_level");
                String comment = rs.getString("comment");

                EvaluationPo eval = new EvaluationPo(evaluationId, evaluatorId, beEvaluatedId, starLevel, comment);
                evals.put(index, eval);
                index++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbClose.close(conn, stmt, rs);
        }
        return evals;
    }

    public boolean updateEvaluation(EvaluationPo newEvaluation) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean isUpdated = false;

        try {
            conn = DbConn.getConn();
            String sql = "UPDATE evaluation SET star_level=?, comment=? WHERE test_evaluation_id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, newEvaluation.getStarLevel());
            pstmt.setString(2, newEvaluation.getComment());
            pstmt.setInt(3, newEvaluation.getEvaluationId());
            int effects = pstmt.executeUpdate();

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
