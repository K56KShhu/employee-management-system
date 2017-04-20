package com.zkyyo.www.service;

import com.zkyyo.www.dao.EvaluationDao;
import com.zkyyo.www.po.EmployeePo;
import com.zkyyo.www.po.EvaluationPo;
import com.zkyyo.www.util.CleanUtil;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EvaluationService {
    public static final int ORDER_BY_STARS = 1;
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

    public EvaluationPo addEvaluation(String evaluatorId, String beEvaluatedId, String stars, String comment) {
        int eEvaluatorId = Integer.valueOf(evaluatorId);
        int eBeEvaluatedId = Integer.valueOf(beEvaluatedId);
        int eStars = Integer.valueOf(stars);
        String eComment = CleanUtil.cleanText(comment);

        EvaluationPo eval = new EvaluationPo(eEvaluatorId, eBeEvaluatedId, eStars, eComment);
        EvaluationDao evaluationDao = EvaluationDao.getInstance();
        boolean isAdded = evaluationDao.addEvaluation(eval);
        if (isAdded) {
            return eval;
        } else {
            return null;
        }
    }

    public EvaluationPo deleteEvaluation(int evalId) {
        EvaluationDao evaluationDao = EvaluationDao.getInstance();
        EvaluationPo deletedEval = evaluationDao.selectEvaluation(evalId);
        if (deletedEval == null) {
            return null;
        } else {
            boolean isDeleted = evaluationDao.deleteEvaluation(evalId);
            if (isDeleted) {
                return deletedEval;
            } else {
                return null;
            }
        }
    }

    public List<EvaluationPo> findSendedEvaluations(int userId) {
        EvaluationDao evaluationDao = EvaluationDao.getInstance();
        return evaluationDao.selectSendedEvaluations(userId);
    }

    public List<EvaluationPo> findReceivedEvaluations(int userId) {
        EvaluationDao evaluationDao = EvaluationDao.getInstance();
        return evaluationDao.selectReceivedEvaluations(userId);
    }

    public List<EvaluationPo> findEvaluations() {
        EvaluationDao evaluationDao = EvaluationDao.getInstance();
        return evaluationDao.selectEvaluations();
    }

    public List<EvaluationPo> findEvaluationsByKeyWords(String keys) {
        String regex = "\\s+";
        Set<String> keySet = new HashSet<>();
        Collections.addAll(keySet, keys.trim().split(regex));

        EvaluationDao evaluationDao = EvaluationDao.getInstance();
        Collection<EvaluationPo> col = evaluationDao.selectEvaluationsByKeyWords(keySet).values();
        List<EvaluationPo> list = new ArrayList<>();
        for (EvaluationPo e : col) {
            list.add(e);
        }
        return list;
    }

    public EvaluationPo findEvaluation(int evalId) {
        EvaluationDao evaluationDao = EvaluationDao.getInstance();
        return evaluationDao.selectEvaluation(evalId);
    }

    public List<EvaluationPo> sort(List<EvaluationPo> list, int orderType, boolean isReverse) {
        switch (orderType) {
            case ORDER_BY_STARS:
                Collections.sort(list, new StarsCompare());
                break;
            default:
                break;
        }
        if (isReverse) {
            Collections.reverse(list);
        }
        return list;
    }

    public EvaluationPo updateEvaluation(EvaluationPo updatedEval) {
        EvaluationDao evaluationDao = EvaluationDao.getInstance();
        EvaluationPo initialEval = evaluationDao.selectEvaluation(updatedEval.getEvaluationId());

        List<Integer> updatedTypes = new ArrayList<>();
        if (updatedEval.getStarLevel() != 0 && updatedEval.getStarLevel() != initialEval.getStarLevel()) {
            updatedEval.setComment(CleanUtil.cleanText(updatedEval.getComment()));
            updatedTypes.add(EvaluationDao.UPDATE_STARS);
        }
        if (updatedEval.getComment() != null && !updatedEval.getComment().equals(initialEval.getComment())) {
            updatedTypes.add(EvaluationDao.UPDATE_COMMENT);
        }
        boolean isUpdated = evaluationDao.updateEvaluation(updatedTypes, updatedEval);
        if (isUpdated) {
            return evaluationDao.selectEvaluation(updatedEval.getEvaluationId());
        } else {
            return null;
        }
    }

    class StarsCompare implements Comparator<EvaluationPo> {
        public int compare(EvaluationPo one, EvaluationPo two) {
            return one.getStarLevel() - two.getStarLevel();
        }
    }

    public static void main(String[] args) {
        EvaluationService evaluationService = EvaluationService.getInstance();
        List<EvaluationPo> list = evaluationService.findEvaluations();
        List<EvaluationPo> result = evaluationService.sort(list, ORDER_BY_STARS, true);
        for (EvaluationPo e : result) {
            System.out.println(e);
        }
    }
}
