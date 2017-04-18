package com.zkyyo.www.service;

import com.zkyyo.www.dao.EvaluationDao;
import com.zkyyo.www.po.EmployeePo;
import com.zkyyo.www.po.EvaluationPo;

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

    public boolean addEvaluation(String evaluatorId, String beEvaluatedId, String stars, String comment) {
        int eEvaluatorId = Integer.valueOf(evaluatorId);
        int eBeEvaluatedId = Integer.valueOf(beEvaluatedId);
        int eStars = Integer.valueOf(stars);
        String eComment = comment.replaceAll("(\r\n|\r|\n|\n\r)", "<br>");

        EvaluationPo eval = new EvaluationPo(eEvaluatorId, eBeEvaluatedId, eStars, eComment);
        EvaluationDao evaluationDao = EvaluationDao.getInstance();
        return evaluationDao.addEvaluation(eval);
    }
}
