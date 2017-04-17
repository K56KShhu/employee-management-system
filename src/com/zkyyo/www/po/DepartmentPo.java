package com.zkyyo.www.po;

import java.sql.Date;

public class DepartmentPo {
    private int deptId;
    private String deptName;
    private int deptPopulation = 0;
    private String deptDesc;
    private Date builtDate;

    public DepartmentPo() {

    }

    public DepartmentPo(int deptId, String deptName, int deptPopulation, String deptDesc, Date builtDate) {
        this.deptId = deptId;
        this.deptName = deptName;
        this.deptPopulation = deptPopulation;
        this.deptDesc = deptDesc;
        this.builtDate = builtDate;
    }

    public int getDeptId() { return deptId; }
    public void setDeptId(int deptId) { this.deptId = deptId; }
    public String getDeptName() { return deptName; }
    public void setDeptName(String deptName) { this.deptName = deptName; }
    public int getDeptPopulation() { return deptPopulation; }
    public void setDeptPopulation(int deptPopulation) { this.deptPopulation = deptPopulation; }
    public String getDeptDesc() { return deptDesc; }
    public void setDeptDesc(String deptDesc) { this.deptDesc = deptDesc; }
    public Date getBuiltDate() { return builtDate; }
    public void setBuiltDate(Date builtDate) { this.builtDate = builtDate; }

    @Override
    public String toString() {
        return "DepartmentPo{" +
                "deptId=" + deptId +
                ", deptName='" + deptName + '\'' +
                ", deptPopulation=" + deptPopulation +
                ", deptDesc='" + deptDesc + '\'' +
                ", builtDate=" + builtDate +
                '}';
    }
}
