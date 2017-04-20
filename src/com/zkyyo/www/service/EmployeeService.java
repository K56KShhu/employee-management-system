package com.zkyyo.www.service;

import com.zkyyo.www.dao.EmployeeDao;
import com.zkyyo.www.po.EmployeePo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmployeeService {
    public static final int ORDER_BY_USER_ID = 1;
    public static final int ORDER_BY_DEPT_ID = 2;
    public static final int ORDER_BY_SALARY = 3;
    public static final int ORDER_BY_DATE = 4;

    private static volatile EmployeeService INSTANCE = null;

    private EmployeeService() {
    }

    public static EmployeeService getInstance() {
        if (INSTANCE == null) {
            synchronized (EmployeeService.class) {
                if (INSTANCE == null) {
                    INSTANCE = new EmployeeService();
                }
            }
        }
        return INSTANCE;
    }

    public boolean checkLogin(int employeeId, String password) {
        EmployeeDao employeeDao = EmployeeDao.getInstance();
        String realPsw = employeeDao.loginCheck(employeeId);
        if (realPsw != null && password != null) {
            if (realPsw.equals(password)) {
                return true;
            }
        }
        return false;
    }

    public boolean isValidId(String id) {
        Pattern p;
        Matcher m;
        String regex = "^\\d{1,10}$";

        if (id != null) {
            p = Pattern.compile(regex);
            m = p.matcher(id);
            if (m.matches()) {
                return true;
            }
        }
        return false;
    }

    public boolean isIdExisted(int id) {
        EmployeeDao employeeDao = EmployeeDao.getInstance();
        EmployeePo foundEp = employeeDao.selectEmployeeByUserId(id);
        return foundEp != null;
    }

    public boolean isValidName(String name) {
        Pattern p;
        Matcher m;
//        String regex = "^[\\u4e00-\\u9fa5]{1,20}$|^[A-Za-z\\s]{1,20}$";
        String regex = "^[\\u4e00-\\u9fa5]{1,20}$|^[A-Za-z\\s]{1,20}$";

        if (name != null) {
            p = Pattern.compile(regex);
            m = p.matcher(name);
            if (m.matches()) {
                return true;
            }
        }
        return false;
    }

    public boolean isValidPassword(String password, String confirmedPassword) {
        if (password != null && confirmedPassword != null) {
            if (password.length() > 0 && password.length() <= 50) {
                if (password.equals(confirmedPassword)) {
                    return true;
                }
            }
        }
        return false;
    }

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

    public boolean isValidEmail(String email) {
        Pattern p;
        Matcher m;
        String regex = "^[\\w.+-]+@[\\w.+-]+\\.[\\w.+-]+$";

        if (email != null) {
            p = Pattern.compile(regex);
            m = p.matcher(email);
            if (m.matches()) {
                return true;
            }
        }
        return false;
    }

    public boolean isValidSalary(String salary) {
        Pattern p;
        Matcher m;
        String regex = "^\\d{1,10}\\.?[05]?0?$";

        if (salary != null) {
            p = Pattern.compile(regex);
            m = p.matcher(salary);
            if (m.matches()) {
                if (Integer.valueOf(salary) > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isValidMobile(String mobile) {
        Pattern p;
        Matcher m;
        String regex = "^0{0,2}((\\+86)|(86))?1[0-9]{10}$";

        if (mobile != null) {
            p = Pattern.compile(regex);
            m = p.matcher(mobile);
            if (m.matches()) {
                return true;
            }
        }
        return false;
    }

    public EmployeePo addEmployee(String name, String mobile, String email, String password,
                                  String departmentId, String salary, String date) {
        EmployeeDao employeeDao = EmployeeDao.getInstance();
        int employeeId = employeeDao.createEmployeeId();
        int deptId = Integer.valueOf(departmentId);
        double eSalary = Double.valueOf(salary);
        java.sql.Date eDate = java.sql.Date.valueOf(date);
        EmployeePo newEp = new EmployeePo(employeeId, password, name, deptId, mobile, eSalary, email, eDate);
        boolean isAdded = employeeDao.addEmployee(newEp);
        if (isAdded) {
            return newEp;
        } else {
            return null;
        }
    }

    public EmployeePo deleteEmployee(int userId) {
        EmployeeDao employeeDao = EmployeeDao.getInstance();
        EmployeePo deletedEp = employeeDao.selectEmployeeByUserId(userId);
        if (deletedEp == null) {
            return null;
        } else {
            boolean isDeleted = employeeDao.deleteEmployee(userId);
            if (isDeleted) {
                return deletedEp;
            } else {
                return null;
            }
        }
    }

    public List<EmployeePo> fuzzyFindEmployeeByUserId(int userId) {
        EmployeeDao employeeDao = EmployeeDao.getInstance();
        return employeeDao.selectPossibleEmployeesByUserId(userId);
    }

    public List<EmployeePo> fuzzyFindEmployeeByUserName(String userName) {
        EmployeeDao employeeDao = EmployeeDao.getInstance();
        return employeeDao.selectPossibleEmployeesByUserName(userName);
    }

    public List<EmployeePo> findEmployeeByDeptId(int deptId) {
        EmployeeDao employeeDao = EmployeeDao.getInstance();
        return employeeDao.selectEmployeesByDeptId(deptId);
    }

    public EmployeePo findEmployee(int userId) {
        EmployeeDao employeeDao = EmployeeDao.getInstance();
        return employeeDao.selectEmployeeByUserId(userId);
    }

    public List<EmployeePo> findEmployees() {
        EmployeeDao employeeDao = EmployeeDao.getInstance();
        return employeeDao.selectEmployees();
    }

    public List<EmployeePo> sort(List<EmployeePo> list, int orderType, boolean isReverse) {
        switch (orderType) {
            case ORDER_BY_USER_ID:
                Collections.sort(list, new UserIdCompare());
                break;
            case ORDER_BY_DEPT_ID:
                Collections.sort(list, new DeptIdCompare());
                break;
            case ORDER_BY_SALARY:
                Collections.sort(list, new SalaryCompare());
                break;
            case ORDER_BY_DATE:
                Collections.sort(list, new DateCompare());
                break;
            default:
                break;
        }
        if (isReverse) {
            Collections.reverse(list);
        }
        return list;
    }


    public EmployeePo updateEmployee(EmployeePo updatedEp) {
        EmployeeDao employeeDao = EmployeeDao.getInstance();
        EmployeePo initialEp = employeeDao.selectEmployeeByUserId(updatedEp.getUserId());
        List<Integer> updatedTypes = new ArrayList<>();

        //姓名
        if (updatedEp.getUserName() != null && !updatedEp.getUserName().equals(initialEp.getUserName())) {
            updatedTypes.add(EmployeeDao.UPDATE_NAME);
        }
        //密码
        if (updatedEp.getPassword() != null && !updatedEp.getPassword().equals(initialEp.getPassword())) {
            updatedTypes.add(EmployeeDao.UPDATE_PASSWORD);
        }
        //邮箱
        if (updatedEp.getEmail() != null && !updatedEp.getEmail().equals(initialEp.getEmail())) {
            updatedTypes.add(EmployeeDao.UPDATE_EMAIL);
        }
        //手机号
        if (updatedEp.getMobile() != null && !updatedEp.getMobile().equals(initialEp.getMobile())) {
            updatedTypes.add(EmployeeDao.UPDATE_MOBILE);
        }
        //部门号
        if (updatedEp.getDeptId() != 0 && updatedEp.getDeptId() != initialEp.getDeptId()) {
            updatedTypes.add(EmployeeDao.UPDATE_DEPARTMENT_ID);
        }
        //薪水
        if (updatedEp.getSalary() != 0.0 && updatedEp.getSalary() != initialEp.getSalary()) {
            updatedTypes.add(EmployeeDao.UPDATE_SALARY);
        }
        //就职日期
        if (updatedEp.getEmployDate() != null && !updatedEp.getEmployDate().equals(initialEp.getEmployDate())) {
            updatedTypes.add(EmployeeDao.UPDATE_EMPLOYEE_DATE);
        }
        boolean isUpdated = employeeDao.updateEmployee(updatedTypes, updatedEp);
        if (isUpdated) {
            return employeeDao.selectEmployeeByUserId(updatedEp.getUserId());
        } else {
            return null;
        }
    }


    class UserIdCompare implements Comparator<EmployeePo> {
        public int compare(EmployeePo one, EmployeePo two) {
            return one.getUserId() - two.getUserId();
        }
    }

    class DateCompare implements Comparator<EmployeePo> {
        public int compare(EmployeePo one, EmployeePo two) {
            return one.getEmployDate().compareTo(two.getEmployDate());
        }
    }

    class SalaryCompare implements Comparator<EmployeePo> {
        public int compare(EmployeePo one, EmployeePo two) {
            Double d1 = one.getSalary();
            Double d2 = two.getSalary();
            return d1.compareTo(d2);
        }
    }

    class DeptIdCompare implements Comparator<EmployeePo> {
        public int compare(EmployeePo one, EmployeePo two) {
            return one.getDeptId() - two.getDeptId();
        }
    }

    public static void main(String[] args) {
        EmployeeService employeeService = EmployeeService.getInstance();
        List<EmployeePo> list = employeeService.findEmployees();
        List<EmployeePo> result = employeeService.sort(list, ORDER_BY_SALARY, true);
        for (EmployeePo e : result) {
            System.out.println(e);
        }
    }
}