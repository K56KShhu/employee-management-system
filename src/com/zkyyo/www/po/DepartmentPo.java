package com.zkyyo.www.po;

import java.sql.Date;
import java.util.Objects;

public class DepartmentPo {
    private int deptId;
    private String name;
    private int population = 0;
    private String description;
    private Date buildDate;

    public DepartmentPo() {

    }

    public DepartmentPo(int deptId, String name, String description, Date buildDate) {
        this.deptId = deptId;
        this.name = name;
        this.description = description;
        this.buildDate = buildDate;
    }

    public DepartmentPo(int deptId, String name, int population, String description, Date buildDate) {
        this.deptId = deptId;
        this.name = name;
        this.population = population;
        this.description = description;
        this.buildDate = buildDate;
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
                ", name='" + name + '\'' +
                ", population=" + population +
                ", description='" + description + '\'' +
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
                && name.equals(other.getName())
                && population == other.getPopulation()
                && description.equals(other.getDescription())
                && buildDate.equals(other.getBuildDate());
    }
    
    public int hashCode() {
        return Objects.hash(deptId, name, population, description, buildDate);
    }
}
