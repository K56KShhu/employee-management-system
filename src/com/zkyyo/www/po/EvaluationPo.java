package com.zkyyo.www.po;

import java.util.Objects;

public class EvaluationPo {
    private int evaluationId;
    private int beEvaluatedId;
    private int evaluatorId;
    private int starLevel;
    private String comment;

    public EvaluationPo() {

    }

    public EvaluationPo(int evaluatorId, int beEvaluatedId, int starLevel, String comment) {
        this.beEvaluatedId = beEvaluatedId;
        this.evaluatorId = evaluatorId;
        this.starLevel = starLevel;
        this.comment = comment;
    }

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

