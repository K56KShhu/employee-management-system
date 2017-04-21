package com.zkyyo.www.service;

import com.zkyyo.www.dao.EvaluationDao;
import com.zkyyo.www.po.EvaluationPo;
import com.zkyyo.www.util.CleanUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 该类存放关于评价的用于逻辑处理的代码
 * 其中包含评价等级的校验,以及相应的增删查改方法
 */
public class EvaluationService {
    /**
     * 作为排序的标识符, 以评价等级为排序依据
     */
    public static final int ORDER_BY_STARS = 1;
    /**
     * 最高的评价等级
     */
    private static final int MAX_STARS = 10;
    /**
     * 最低的评价等级
     */
    private static final int MIN_STARS = 1;
    /**
     * 用于创建懒汉模式下的一个单例, 默认为null
     */
    private static volatile EvaluationService INSTANCE = null;

    /**
     * 禁止实例化新的对象
     */
    private EvaluationService() {
    }

    /**
     * 创建一个该类的实例
     * @return 返回这个类的一个实例
     */
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

    /**
     * 校验评价号是否符合格式
     * 格式为>=0的数字
     * @param evalId 待校验的评价号字符串
     * @return 符合为true, 不符合为false
     */
    public boolean isValidId(String evalId) {
        Pattern p;
        Matcher m;
        String regex = "^\\d{1,10}$";

        if (evalId != null) {
            p = Pattern.compile(regex);
            m = p.matcher(evalId);
            if (m.matches()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 校验评价等级是否符合格式
     * @param stars 待校验的评价等级字符串
     * @return 符合为true, 不符合为false
     */
    public boolean isValidStars(String stars) {
        Pattern p;
        Matcher m;
        String regex = "^\\d+$";

        if (stars != null) {
            p = Pattern.compile(regex);
            m = p.matcher(stars);
            if (m.matches()) {
                int eStars = Integer.valueOf(stars);
                //薪水限制
                if (eStars >= MIN_STARS && eStars <= MAX_STARS) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 检测评价是否已经存在
     * @param evalId 待检测的评价号
     * @return 存在为true, 不存在false
     */
    public boolean isExisted(int evalId) {
        EvaluationDao evaluationDao = EvaluationDao.getInstance();
        return evaluationDao.selectEvaluation(evalId) != null;
    }

    /**
     * 添加评价
     * @param evaluatorId 评价者的员工号字符串
     * @param beEvaluatedId 被评价者的员工号字符串
     * @param stars 评价等级字符串
     * @param comment 评论
     * @return 评论成功返回该评论对象, 否则返回null
     */
    public EvaluationPo addEvaluation(String evaluatorId, String beEvaluatedId, String stars, String comment) {
        int eEvaluatorId = Integer.valueOf(evaluatorId);
        int eBeEvaluatedId = Integer.valueOf(beEvaluatedId);
        int eStars = Integer.valueOf(stars);
        String eComment = CleanUtil.cleanText(comment); //数据清洗

        EvaluationPo eval = new EvaluationPo(eEvaluatorId, eBeEvaluatedId, eStars, eComment);
        EvaluationDao evaluationDao = EvaluationDao.getInstance();
        boolean isAdded = evaluationDao.addEvaluation(eval);
        if (isAdded) {
            return eval;
        } else {
            return null;
        }
    }

    /**
     * 删除评论
     * @param evalId 待删除评论的评论号
     * @return 删除成功返回被删除的评论对象, 评论不存在或删除失败返回null
     */
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

    /**
     * 查找发送的评价
     * @param userId 发送者的员工号
     * @return 评价列表
     */
    public List<EvaluationPo> findSendedEvaluations(int userId) {
        EvaluationDao evaluationDao = EvaluationDao.getInstance();
        return evaluationDao.selectSendedEvaluations(userId);
    }

    /**
     * 查找获得的评价
     * @param userId 获得者的员工号
     * @return 评价列表
     */
    public List<EvaluationPo> findReceivedEvaluations(int userId) {
        EvaluationDao evaluationDao = EvaluationDao.getInstance();
        return evaluationDao.selectReceivedEvaluations(userId);
    }

    /**
     * 查找所有评价
     * @return 评价列表
     */
    public List<EvaluationPo> findEvaluations() {
        EvaluationDao evaluationDao = EvaluationDao.getInstance();
        return evaluationDao.selectEvaluations();
    }

    /**
     * 模糊查询, 根据评论的关键字查找评价
     * @param keys 以空格分隔的关键字字符串
     * @return 符合要求的评价列表
     */
    public List<EvaluationPo> findEvaluationsByKeyWords(String keys) {
        String regex = "\\s+";
        Set<String> keySet = new HashSet<>();
        Collections.addAll(keySet, keys.trim().split(regex)); //将字符串拆分为关键字

        EvaluationDao evaluationDao = EvaluationDao.getInstance();
        //获得结果集
        Set<EvaluationPo> resultSet = evaluationDao.selectEvaluationsByKeyWords(keySet);
        List<EvaluationPo> resultList = new ArrayList<>();
        //转换为列表
        resultList.addAll(resultSet);
        return resultList;
    }

    /**
     * 精确查询, 根据评价号查找
     * @param evalId 待查找的评价号
     * @return 找到返回该评价对象, 否则返回null
     */
    public EvaluationPo findEvaluation(int evalId) {
        EvaluationDao evaluationDao = EvaluationDao.getInstance();
        return evaluationDao.selectEvaluation(evalId);
    }

    /**
     * 对评价列表进行排序
     * @param list 评价列表
     * @param orderType 排序依据, 参考该类的静态成员变量
     * @param isReverse 倒序为true, 升序为false
     * @return 排序后的评价列表
     */
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

    /**
     * 更新评价信息
     * @param updatedEval 包含最新评价信息的评价对象
     * @return 更新成功返回新评价对象, 失败返回null
     */
    public EvaluationPo updateEvaluation(EvaluationPo updatedEval) {
        EvaluationDao evaluationDao = EvaluationDao.getInstance();
        EvaluationPo initialEval = evaluationDao.selectEvaluation(updatedEval.getEvaluationId());
        List<Integer> updatedTypes = new ArrayList<>();

        //评价等级
        if (updatedEval.getStarLevel() != 0 && updatedEval.getStarLevel() != initialEval.getStarLevel()) {
            updatedEval.setComment(CleanUtil.cleanText(updatedEval.getComment()));
            updatedTypes.add(EvaluationDao.UPDATE_STARS);
        }
        //评价内容
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

    /**
     * 提供排序依据: 评价等级
     */
    class StarsCompare implements Comparator<EvaluationPo> {
        public int compare(EvaluationPo one, EvaluationPo two) {
            return one.getStarLevel() - two.getStarLevel();
        }
    }
}
