package com.zkyyo.www.po;

import java.sql.Date;
import java.util.Objects;

/**
 * 员工的实体类
 */
public class DepartmentPo {
    private int deptId; //部门号 1-10位大于0的数字
    private String name; //部门名 最多20个字符
    private int population = 0; //部门人数 默认为0
    private String description; //部门描述
    private Date buildDate; //部门建立日期

    /**
     * 默认构造方法
     * 用于更新部门信息, 只需提供需要修改的信息
     */
    public DepartmentPo() {

    }

    /**
     * 构造方法
     * 用于创建部门, 不允许指定人数
     *
     * @param deptId      部门号
     * @param name        部门名
     * @param description 部门描述
     * @param buildDate   建立日期
     */
    public DepartmentPo(int deptId, String name, String description, Date buildDate) {
        this.deptId = deptId;
        this.name = name;
        this.description = description;
        this.buildDate = buildDate;
    }

    /**
     * 构造方法
     * 用于查询部门, 包含人数
     *
     * @param deptId      部门号
     * @param name        部门名
     * @param population  部门人数
     * @param description 部门描述
     * @param buildDate   建立日期
     */
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
