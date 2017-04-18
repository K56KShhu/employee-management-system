package com.zkyyo.www.po;

import java.sql.Timestamp;

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

    public EvaluationPo(int evaluationId, int beEvaluatedId, int evaluatorId, int starLevel, String comment) {
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
}

