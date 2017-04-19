package com.zkyyo.www.po;

import java.sql.Date;
import java.util.Objects;

public class DepartmentPo {
    private int deptId;
    private String deptName;
    private int deptPopulation = 0;
    private String deptDesc;
    private Date buildDate;

    public DepartmentPo() {

    }

    public DepartmentPo(int deptId, String deptName, String deptDesc, Date buildDate) {
        this.deptId = deptId;
        this.deptName = deptName;
        this.deptDesc = deptDesc;
        this.buildDate = buildDate;
    }

    public DepartmentPo(int deptId, String deptName, int deptPopulation, String deptDesc, Date buildDate) {
        this.deptId = deptId;
        this.deptName = deptName;
        this.deptPopulation = deptPopulation;
        this.deptDesc = deptDesc;
        this.buildDate = buildDate;
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public int getDeptPopulation() {
        return deptPopulation;
    }

    public void setDeptPopulation(int deptPopulation) {
        this.deptPopulation = deptPopulation;
    }

    public String getDeptDesc() {
        return deptDesc;
    }

    public void setDeptDesc(String deptDesc) {
        this.deptDesc = deptDesc;
    }

    public Date getBuildDate() {
        return buildDate;
    }

    public void setBuildDate(Date buildDate) {
        this.buildDate = buildDate;
    }

    @Override
    public String toString() {
        return "DepartmentPo{" +
                "deptId=" + deptId +
                ", deptName='" + deptName + '\'' +
                ", deptPopulation=" + deptPopulation +
                ", deptDesc='" + deptDesc + '\'' +
                ", buildDate=" + buildDate +
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
        if (!(obj instanceof DepartmentPo)) {
            return false;
        }
        DepartmentPo other = (DepartmentPo) obj;
        return deptId == other.getDeptId()
                && deptName.equals(other.getDeptName())
                && deptPopulation == other.getDeptPopulation()
                && deptDesc.equals(other.getDeptDesc())
                && buildDate.equals(other.getBuildDate());
    }
    
    public int hashCode() {
        return Objects.hash(deptId, deptName, deptPopulation, deptDesc, buildDate);
    }
}
