package com.zkyyo.www.service;

import com.zkyyo.www.dao.EvaluationDao;
import com.zkyyo.www.po.EvaluationPo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EvaluationService {
    private static final int MAX_STARS = 10;
    private static final int MIN_STARS = 1;
    private static volatile EvaluationService INSTANCE = null;

    private EvaluationService() {
    }

    public static EvaluationService getInstance() {
        if (INSTANCE == null) {
            synchronized (EvaluationService.class) {
                if (INSTANCE == null) {
                    INSTANCE = new EvaluationService();
                }
            }
        }
        return INSTANCE;
    }

    public boolean isValidStars(String stars) {
        Pattern p = null;
        Matcher m = null;
        String regex = "^\\d+$";

        if (stars != null) {
            p = Pattern.compile(regex);
            m = p.matcher(stars);
            if (m.matches()) {
                int eStars = Integer.valueOf(stars);
                if (eStars >= MIN_STARS && eStars <= MAX_STARS) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isValidId(String evalId) {
        Pattern p = null;
        Matcher m = null;
        String regex = "^\\d+$";

        if (evalId != null) {
            p = Pattern.compile(regex);
            m = p.matcher(evalId);
            if (m.matches()) {
                    return true;
            }
        }
        return false;
    }

    public boolean isExisted(int evalId) {
        EvaluationDao evaluationDao = EvaluationDao.getInstance();
        return evaluationDao.selectEvaluation(evalId) != null;
    }

    public boolean addEvaluation(String evaluatorId, String beEvaluatedId, String stars, String comment) {
        int eEvaluatorId = Integer.valueOf(evaluatorId);
        int eBeEvaluatedId = Integer.valueOf(beEvaluatedId);
        int eStars = Integer.valueOf(stars);
        String eComment = comment.replaceAll("(\r\n|\r|\n|\n\r)", "<br>");

        EvaluationPo eval = new EvaluationPo(eEvaluatorId, eBeEvaluatedId, eStars, eComment);
        EvaluationDao evaluationDao = EvaluationDao.getInstance();
        return evaluationDao.addEvaluation(eval);
    }

    public boolean deleteEvaluation(int evalId) {
        EvaluationDao evaluationDao = EvaluationDao.getInstance();
        return evaluationDao.deleteEvaluation(evalId);
    }

    public List<EvaluationPo> findSendedEvaluations(int userId) {
        EvaluationDao evaluationDao = EvaluationDao.getInstance();
        return evaluationDao.selectSendedEvaluations(userId);
    }

    public List<EvaluationPo> findReceivedEvaluations(int userId) {
        EvaluationDao evaluationDao = EvaluationDao.getInstance();
        return evaluationDao.selectReceivedEvaluations(userId);
    }

    public boolean updateEvaluation(EvaluationPo updatedEval) {
        EvaluationDao evaluationDao = EvaluationDao.getInstance();
        EvaluationPo initialEval = evaluationDao.selectEvaluation(updatedEval.getEvaluationId());

        List<Integer> updatedTypes = new ArrayList<>();
        if (updatedEval.getStarLevel() != 0 && updatedEval.getStarLevel() != initialEval.getStarLevel()) {
            updatedTypes.add(EvaluationDao.UPDATE_STARS);
        }
        if (updatedEval.getComment() != null && !updatedEval.getComment().equals(initialEval.getComment())) {
            updatedTypes.add(EvaluationDao.UPDATE_COMMENT);
        }
        return !updatedTypes.isEmpty() && evaluationDao.updateEvaluation(updatedTypes, updatedEval);
    }
}
