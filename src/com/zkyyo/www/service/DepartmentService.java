package com.zkyyo.www.service;

import com.zkyyo.www.dao.DepartmentDao;
import com.zkyyo.www.po.DepartmentPo;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DepartmentService {
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

    public Integer addDepartment(String name, String deptId, String buildDate, String desc) {
        int id = Integer.valueOf(deptId);
        java.sql.Date date = java.sql.Date.valueOf(buildDate);
        String newStr = desc.replaceAll("(\r\n|\r|\n|\n\r)", "<br>");

        DepartmentPo newDept = new DepartmentPo(id, name, newStr, date);
        DepartmentDao departmentDao = DepartmentDao.getInstance();
        boolean isAdded = departmentDao.addDepartment(newDept);
        if (isAdded) {
            return id;
        } else {
            return null;
        }
    }

    public boolean deleteDepartment(int deptId) {
        DepartmentDao departmentDao = DepartmentDao.getInstance();
        return departmentDao.deleteDept(deptId);
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

    public boolean updateDepartment(DepartmentPo updatedDept) {
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

        return !updatedTypes.isEmpty() && departmentDao.updateDept(updatedTypes, updatedDept);
    }


    public static void main(String[] args) {
        DepartmentService departmentService = DepartmentService.getInstance();
        System.out.println(departmentService.findDepartment(-1));
    }
}

