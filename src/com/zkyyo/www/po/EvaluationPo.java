package com.zkyyo.www.po;

import java.util.Objects;

/**
 * 评价的实体类
 */
public class EvaluationPo {
    private int evaluationId; //评价号 为数据库中主键
    private int beEvaluatedId; //被评价的员工号
    private int evaluatorId; //评价者的员工号
    private int starLevel; //评价等级
    private String comment; //评价内容

    /**
     * 默认构造方法
     * 用于更新评价信息, 只需提供需要修改的信息
     */
    public EvaluationPo() {

    }

    /**
     * 构造方法
     * 用于添加评价
     *
     * @param evaluatorId   评价者的员工号
     * @param beEvaluatedId 被评价的员工号
     * @param starLevel     评价等级
     * @param comment       评价内容
     */
    public EvaluationPo(int evaluatorId, int beEvaluatedId, int starLevel, String comment) {
        this.beEvaluatedId = beEvaluatedId;
        this.evaluatorId = evaluatorId;
        this.starLevel = starLevel;
        this.comment = comment;
    }

    /**
     * 构造方法
     * 用于修改, 删除评价
     *
     * @param evaluationId  评价号
     * @param evaluatorId   评价者的员工号
     * @param beEvaluatedId 被评价的员工号
     * @param starLevel     评价等级
     * @param comment       评价内容
     */
    public EvaluationPo(int evaluationId, int evaluatorId, int beEvaluatedId, int starLevel, String comment) {
        this.evaluationId = evaluationId;
        this.beEvaluatedId = beEvaluatedId;
        this.evaluatorId = evaluatorId;
        this.starLevel = starLevel;
        this.comment = comment;
    }

    public int getEvaluationId() {
        return evaluationId;
    }

    public void setEvaluationId(int evaluationId) {
        this.evaluationId = evaluationId;
    }

    public int getBeEvaluatedId() {
        return beEvaluatedId;
    }

    public void setBeEvaluatedId(int beEvaluatedId) {
        this.beEvaluatedId = beEvaluatedId;
    }

    public int getEvaluatorId() {
        return evaluatorId;
    }

    public void setEvaluatorId(int evaluatorId) {
        this.evaluatorId = evaluatorId;
    }

    public int getStarLevel() {
        return starLevel;
    }

    public void setStarLevel(int starLevel) {
        this.starLevel = starLevel;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "EvaluationPo{" +
                "evaluationId=" + evaluationId +
                ", beEvaluatedId=" + beEvaluatedId +
                ", evaluatorId=" + evaluatorId +
                ", starLevel=" + starLevel +
                ", comment='" + comment + '\'' +
                '}';
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        if (!(obj instanceof EvaluationPo)) {
            return false;
        }
        EvaluationPo other = (EvaluationPo) obj;
        return evaluatorId == other.evaluatorId
                && beEvaluatedId == other.getBeEvaluatedId()
                && evaluatorId == other.getEvaluatorId()
                && starLevel == other.getStarLevel()
                && comment.equals(other.getComment());
    }

    public int hashCode() {
        return Objects.hash(evaluationId, beEvaluatedId, evaluatorId, starLevel, comment);
    }
}

