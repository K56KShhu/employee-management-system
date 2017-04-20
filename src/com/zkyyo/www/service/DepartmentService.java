package com.zkyyo.www.service;

import com.zkyyo.www.dao.DepartmentDao;
import com.zkyyo.www.po.DepartmentPo;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DepartmentService {
    public static final int ORDER_BY_ID = 1;
    public static final int ORDER_BY_POPULATION = 2;
    public static final int ORDER_BY_DATE = 3;
    private static volatile DepartmentService INSTANCE = null;

    private DepartmentService() {
    }

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

    public boolean isValidId(String deptId) {
        Pattern p = null;
        Matcher m = null;
        String regex = "^[\\d]{1,10}$";

        if (deptId != null) {
            p = Pattern.compile(regex);
            m = p.matcher(deptId);
            if (m.matches()) {
                DepartmentDao departmentDao = DepartmentDao.getInstance();
                if (departmentDao.isAvailableId(Integer.valueOf(deptId))) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isValidName(String name) {
        if (name.length() > 0) {
            DepartmentDao departmentDao = DepartmentDao.getInstance();
            return departmentDao.isNameExisted(name);
        } else {
            return false;
        }
    }

    public boolean isValidDate(String date) {
        Pattern p = null;
        Matcher m = null;
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

    public DepartmentPo addDepartment(String name, String deptId, String buildDate, String desc) {
        int id = Integer.valueOf(deptId);
        java.sql.Date date = java.sql.Date.valueOf(buildDate);
        String newStr = desc.replaceAll("(\r\n|\r|\n|\n\r)", "<br>");

        DepartmentPo newDept = new DepartmentPo(id, name, newStr, date);
        DepartmentDao departmentDao = DepartmentDao.getInstance();
        boolean isAdded = departmentDao.addDepartment(newDept);
        if (isAdded) {
            return newDept;
        } else {
            return null;
        }
    }

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

    public List<DepartmentPo> fuzzyFindDepartmentByDeptId(int deptId) {
        DepartmentDao departmentDao = DepartmentDao.getInstance();
        return departmentDao.selectPossibleDepartmentsByDeptId(deptId);
    }

    public List<DepartmentPo> fuzzyFindDepartmentByDeptName(String name) {
        DepartmentDao departmentDao = DepartmentDao.getInstance();
        return departmentDao.selectPossibleDepartmentByDeptName(name);
    }

    public List<DepartmentPo> findDepartments() {
        DepartmentDao departmentDao = DepartmentDao.getInstance();
        return departmentDao.selectDepartments();
    }

    public DepartmentPo findDepartment(int deptId) {
        DepartmentDao departmentDao = DepartmentDao.getInstance();
        return departmentDao.selectDepartmentByDeptId(deptId);
    }

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

    public DepartmentPo updateDepartment(DepartmentPo updatedDept) {
        DepartmentDao departmentDao = DepartmentDao.getInstance();
        DepartmentPo initialDept = departmentDao.selectDepartmentByDeptId(updatedDept.getDeptId());
        List<Integer> updatedTypes = new ArrayList<>();

        if (updatedDept.getDeptName() != null && !updatedDept.getDeptName().equals(initialDept.getDeptName())) {
            updatedTypes.add(DepartmentDao.UPDATE_NAME);
        }
        if (updatedDept.getBuildDate() != null && !updatedDept.getBuildDate().equals(initialDept.getBuildDate())) {
            updatedTypes.add(DepartmentDao.UPDATE_BUILD_DATE);
        }
        if (updatedDept.getDeptDesc() != null && !updatedDept.getDeptDesc().equals(initialDept.getDeptDesc())) {
            updatedTypes.add(DepartmentDao.UPDATE_DESC);
        }
        boolean isUpdated = departmentDao.updateDept(updatedTypes, updatedDept);
        if (isUpdated) {
            return departmentDao.selectDepartmentByDeptId(updatedDept.getDeptId());
        } else {
            return null;
        }
    }


    class IdCompare implements Comparator<DepartmentPo> {
        public int compare(DepartmentPo one, DepartmentPo two) {
            return one.getDeptId() - two.getDeptId();
        }
    }

    class PopulationCompare implements Comparator<DepartmentPo> {
        public int compare(DepartmentPo one, DepartmentPo two) {
            return one.getDeptPopulation() - two.getDeptPopulation();
        }
    }

    class BuildDateCompare implements Comparator<DepartmentPo> {
        public int compare(DepartmentPo one, DepartmentPo two) {
            return one.getBuildDate().compareTo(two.getBuildDate());
        }
    }

    public static void main(String[] args) {
        DepartmentService departmentService = DepartmentService.getInstance();
        List<DepartmentPo> list = departmentService.findDepartments();
        List<DepartmentPo> result = departmentService.sort(list, ORDER_BY_DATE, true);
        for (DepartmentPo d : result) {
            System.out.println(d);
        }
    }
}

