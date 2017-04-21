package com.zkyyo.www.service;

import com.zkyyo.www.dao.DepartmentDao;
import com.zkyyo.www.po.DepartmentPo;
import com.zkyyo.www.util.CleanUtil;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 该类存放关于部门的用于逻辑处理的代码
 * 其中包含部门的相关信息的校验,增删查改方法
 */
public class DepartmentService {
    /**
     * 作为排序方法的参数, 以部门号为排序依据
     */
    public static final int ORDER_BY_ID = 1;
    /**
     * 作为排序方法的参数, 以部门人数为排序依据
     */
    public static final int ORDER_BY_POPULATION = 2;
    /**
     * 作为排序方法的参数, 以建立日期为排序依据
     */
    public static final int ORDER_BY_DATE = 3;
    /**
     * 用于创建懒汉模式下的一个单例, 默认为null
     */
    private static volatile DepartmentService INSTANCE = null;

    /**
     * 禁止实例化新的对象
     */
    private DepartmentService() {
    }

    /**
     * 创建一个该类的实例
     * @return 返回这个类的一个实例
     */
    public static DepartmentService getInstance() {
        if (INSTANCE == null) {
            synchronized (DepartmentService.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DepartmentService();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 校验部门号是否符合格式
     * 格式为1-10位的大于0的数字
     * @param deptId 待校验的部门号
     * @return 符合为true, 不符合为false
     */
    public boolean isValidId(String deptId) {
        Pattern p;
        Matcher m;
        String regex = "^[\\d]{1,10}$";

        if (deptId != null) {
            p = Pattern.compile(regex);
            m = p.matcher(deptId);
            if (m.matches()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 校验日期是否符合格式, 且是否合法
     * @param date 待校验的日期
     * @return 符合为true, 不符合为false
     */
    public boolean isValidDate(String date) {
        Pattern p;
        Matcher m;
        String regex = "^\\d{4}-\\d{1,2}-\\d{1,2}";

        if (date != null) {
            p = Pattern.compile(regex);
            m = p.matcher(date);
            if (m.matches()) {
                String[] yearMonthDay = date.split("-");
                int year = Integer.valueOf(yearMonthDay[0]);
                int month = Integer.valueOf(yearMonthDay[1]);
                int day = Integer.valueOf(yearMonthDay[2]);
                if (month >= 1 && month <= 12) {
                    Calendar mycal = new GregorianCalendar(year, month - 1, 1); //起始月份为0
                    int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
                    if (day < daysInMonth) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 检测部门号是否可用
     * @param id 待检测的部门号
     * @return 可用为true, 不可用为false
     */
    public boolean isAvailableId(int id) {
        DepartmentDao departmentDao = DepartmentDao.getInstance();
        return departmentDao.isIdExisted(id);
    }

    /**
     * 检测部门名是否可用
     * @param name 待检测的部门名
     * @return 可用为true, 不可用为false
     */
    public boolean isAvailableName(String name) {
        if (name.length() > 0) {
            DepartmentDao departmentDao = DepartmentDao.getInstance();
            return departmentDao.isNameExisted(name);
        } else {
            return false;
        }
    }

    /**
     * 添加部门
     * @param name 部门名
     * @param deptId 部门号
     * @param buildDate 建立日期
     * @param desc 部门描述
     * @return 添加成功返回该部门对象, 否则返回null
     */
    public DepartmentPo addDepartment(String name, String deptId, String buildDate, String desc) {
        int id = Integer.valueOf(deptId);
        java.sql.Date date = java.sql.Date.valueOf(buildDate);
        desc = CleanUtil.cleanText(desc);

        DepartmentPo newDept = new DepartmentPo(id, name, desc, date);
        DepartmentDao departmentDao = DepartmentDao.getInstance();
        boolean isAdded = departmentDao.addDepartment(newDept);
        if (isAdded) {
            return newDept;
        } else {
            return null;
        }
    }

    /**
     * 解散部门
     * @param deptId 待解散的部门号
     * @return 解散成功返回被解散的部门对象, 部门不存在或解散失败返回null
     */
    public DepartmentPo deleteDepartment(int deptId) {
        DepartmentDao departmentDao = DepartmentDao.getInstance();
        DepartmentPo deletedDept = departmentDao.selectDepartmentByDeptId(deptId);
        if (deletedDept == null) {
            return null;
        } else {
            boolean isDeleted = departmentDao.deleteDept(deptId);
            if (isDeleted) {
                return deletedDept;
            } else {
                return null;
            }
        }
    }

    /**
     * 模糊查询部门, 根据部门号查找
     * @param deptId 部门号
     * @return 符合的部门列表
     */
    public List<DepartmentPo> fuzzyFindDepartmentByDeptId(int deptId) {
        DepartmentDao departmentDao = DepartmentDao.getInstance();
        return departmentDao.selectPossibleDepartmentsByDeptId(deptId);
    }

    /**
     * 模糊查询部门, 根据部门名查找
     * @param name 部门号
     * @return 符合的部门列表
     */
    public List<DepartmentPo> fuzzyFindDepartmentByDeptName(String name) {
        DepartmentDao departmentDao = DepartmentDao.getInstance();
        return departmentDao.selectPossibleDepartmentByDeptName(name);
    }

    /**
     * 精确查询部门, 根据部门号查询
     * @param deptId 部门号
     * @return 找到返回该部门对象, 否则返回null
     */
    public DepartmentPo findDepartment(int deptId) {
        DepartmentDao departmentDao = DepartmentDao.getInstance();
        return departmentDao.selectDepartmentByDeptId(deptId);
    }

    /**
     * 查找所有部门
     * @return 包含所有部门的列表
     */
    public List<DepartmentPo> findDepartments() {
        DepartmentDao departmentDao = DepartmentDao.getInstance();
        return departmentDao.selectDepartments();
    }

    /**
     * 对部门列表进行排序
     * @param list 待排序的部门列表
     * @param orderType 排序依据, 参考该类的静态成员变量
     * @param isReverse 降序为true, 升序为false
     * @return 排序后的部门列表
     */
    public List<DepartmentPo> sort(List<DepartmentPo> list, int orderType, boolean isReverse) {
        switch (orderType) {
            case ORDER_BY_ID:
                Collections.sort(list, new IdCompare());
                break;
            case ORDER_BY_POPULATION:
                Collections.sort(list, new PopulationCompare());
                break;
            case ORDER_BY_DATE:
                Collections.sort(list, new BuildDateCompare());
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
     * 更新部门信息
     * @param updatedDept 包含最新部门信息的部门对象
     * @return 更新成功返回最新部门对象, 否则返回null
     */
    public DepartmentPo updateDepartment(DepartmentPo updatedDept) {
        DepartmentDao departmentDao = DepartmentDao.getInstance();
        DepartmentPo initialDept = departmentDao.selectDepartmentByDeptId(updatedDept.getDeptId());
        List<Integer> updatedTypes = new ArrayList<>();

        if (updatedDept.getName() != null && !updatedDept.getName().equals(initialDept.getName())) {
            updatedTypes.add(DepartmentDao.UPDATE_NAME);
        }
        if (updatedDept.getBuildDate() != null && !updatedDept.getBuildDate().equals(initialDept.getBuildDate())) {
            updatedTypes.add(DepartmentDao.UPDATE_BUILD_DATE);
        }
        if (updatedDept.getDescription() != null && !updatedDept.getDescription().equals(initialDept.getDescription())) {
            updatedDept.setDescription(CleanUtil.cleanText(updatedDept.getDescription()));
            updatedTypes.add(DepartmentDao.UPDATE_DESC);
        }
        boolean isUpdated = departmentDao.updateDept(updatedTypes, updatedDept);
        if (isUpdated) {
            return departmentDao.selectDepartmentByDeptId(updatedDept.getDeptId());
        } else {
            return null;
        }
    }

    /**
     * 提供排序依据: 部门号
     */
    class IdCompare implements Comparator<DepartmentPo> {
        public int compare(DepartmentPo one, DepartmentPo two) {
            return one.getDeptId() - two.getDeptId();
        }
    }

    /**
     * 提供排序依据: 部门人数
     */
    class PopulationCompare implements Comparator<DepartmentPo> {
        public int compare(DepartmentPo one, DepartmentPo two) {
            return one.getPopulation() - two.getPopulation();
        }
    }

    /**
     * 提供排序依据: 建立日期
     */
    class BuildDateCompare implements Comparator<DepartmentPo> {
        public int compare(DepartmentPo one, DepartmentPo two) {
            return one.getBuildDate().compareTo(two.getBuildDate());
        }
    }
}

